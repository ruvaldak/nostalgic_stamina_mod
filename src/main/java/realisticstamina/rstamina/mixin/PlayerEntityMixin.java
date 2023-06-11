package realisticstamina.rstamina.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import realisticstamina.rstamina.RStaminaClient;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntityMixin {
    @Override
    protected boolean rstamina$preventSprintingIfStaminaIsTooLow(boolean sprinting) {
        return (RStaminaClient.getClientStoredStamina() > 0) ? sprinting : false;
    }
}

