package chenjunfu2.kexplosion.mixin;

import chenjunfu2.kexplosion.test.KExplosionTest;
import net.minecraft.entity.decoration.ArmorStandEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ArmorStandEntity.class)
public class ArmorStandEntityMixin implements KExplosionTest//仅用于给目标类添加implements
{
}
