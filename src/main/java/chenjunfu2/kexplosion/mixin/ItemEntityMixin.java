package chenjunfu2.kexplosion.mixin;

import chenjunfu2.kexplosion.test.KExplosionTest;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemEntity.class)
public class ItemEntityMixin implements KExplosionTest//仅用于给目标类添加implements
{
}
