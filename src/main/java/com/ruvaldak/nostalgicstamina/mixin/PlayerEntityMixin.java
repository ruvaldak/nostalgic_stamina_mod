package com.ruvaldak.nostalgicstamina.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import com.ruvaldak.nostalgicstamina.NostalgicStaminaClient;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntityMixin {
    @Override
    protected boolean nostalgicstamina$preventSprintingIfStaminaIsTooLow(boolean sprinting) {
        return (NostalgicStaminaClient.getClientStoredStamina() > 0) ? sprinting : false;
    }
}

