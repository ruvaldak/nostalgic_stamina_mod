package com.ruvaldak.nostalgicstamina.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.ruvaldak.nostalgicstamina.NostalgicStaminaClient;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @ModifyVariable(method = "sendSprintingPacket()V", at = @At("STORE"))
    private boolean nostalgicstamina$preventSprintingIfStaminaIsTooLow(boolean sprinting) {
        return (NostalgicStaminaClient.getClientStoredStamina() <= 0) ? false : sprinting;
    }
}

