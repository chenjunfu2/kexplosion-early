package chenjunfu2.kexplosion.mixin;

import chenjunfu2.kexplosion.test.KExplosionTest;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractDecorationEntity.class)
public class AbstractDecorationEntityMixin implements KExplosionTest//仅用于给目标类添加implements
{
}
