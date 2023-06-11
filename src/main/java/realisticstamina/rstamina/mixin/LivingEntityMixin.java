package realisticstamina.rstamina.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @ModifyVariable(method = "setSprinting(Z)V", at = @At("HEAD"))
    protected boolean rstamina$preventSprintingIfStaminaIsTooLow(boolean sprinting) {
        return sprinting;
    }
}

