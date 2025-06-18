package chenjunfu2.kexplosion.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.world.explosion.Explosion;

@Mixin(Explosion.class)
public class ExplosionMixin
{
	@Shadow @Final private World world;
	@Shadow @Final private @Nullable Entity entity;
	@Shadow @Final private Explosion.DestructionType destructionType;
	
	@Redirect(
		method = "collectBlocksAndDamageEntities()V",
		at = @At
		(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/Entity;isImmuneToExplosion()Z"
		)
	)
	public boolean redirectIsImmuneToExplosion(Entity targetEntity)
	{
		// 返回 true → 跳过伤害（相当于原版 isImmuneToExplosion() 返回 true）
		// 返回 false → 允许伤害（相当于原版 isImmuneToExplosion() 返回 false）
		
		//return explosion.preservesDecorativeEntities() ? super.isImmuneToExplosion(explosion) : true;
		//因为低版本redirectIsImmuneToExplosion不会传入this，所以无法通过mixin需要的实体来调用preservesDecorativeEntities
		//在这里直接判断targetEntity的类型
		
		if//如果实体符合要求则判断爆炸实体
		(
			targetEntity instanceof ItemEntity ||
			targetEntity instanceof ArmorStandEntity ||
			targetEntity instanceof AbstractDecorationEntity
		)
		{//是的话则走K爆判断
			return preservesDecorativeEntities() ? targetEntity.isImmuneToExplosion() : true;
		}
		else
		{//否则按原始判断
			return targetEntity.isImmuneToExplosion();
		}
	}
	
	@Unique
	private boolean preservesDecorativeEntities()//这里判断是否保留装饰实体，通过判断导致爆炸的实体在不在水中来决定
	{
		//游戏规则同样影响是否能炸掉实体，如果是苦力怕之类的生物并且DO_MOB_GRIEFING为false，那么哪怕不在水中也不会炸掉实体
		boolean bl = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
		boolean bl2 = this.entity == null || !this.entity.isTouchingWater();
		return bl ? bl2 : this.destructionType != Explosion.DestructionType.KEEP && bl2;
	}
}