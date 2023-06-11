package realisticstamina.rstamina.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import realisticstamina.rstamina.RStaminaClient;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @ModifyVariable(method = "sendSprintingPacket()V", at = @At("STORE"))
    private boolean rstamina$preventSprintingIfStaminaIsTooLow(boolean sprinting) {
        return (RStaminaClient.getClientStoredStamina() <= 0) ? false : sprinting;
    }
}

