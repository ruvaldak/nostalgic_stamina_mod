package com.ruvaldak.nostalgicstamina.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import com.ruvaldak.nostalgicstamina.PlayerState;
import com.ruvaldak.nostalgicstamina.ServerState;
import com.ruvaldak.nostalgicstamina.registry.ConfigRegistry;

public class UpdateStaminaC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        ServerState serverState = ServerState.getServerState(server);
        PlayerState playerstate = ServerState.getPlayerState(player);

        double  cappedSecondsPerTick          = Math.max(0.05, server.getTickTime()/1000),
                staminaRechargedPerTick       = ConfigRegistry.CONFIG.staminaRechargedPerSecond * cappedSecondsPerTick,
                starvingDrainPerTick          = ConfigRegistry.CONFIG.starvingDrainPerSecond * cappedSecondsPerTick,
                sprintingSwimmingDrainPerTick = ConfigRegistry.CONFIG.sprintingSwimmingDrainPerSecond * cappedSecondsPerTick,
                rechargeDelayInMillis         = ConfigRegistry.CONFIG.rechargeDelayInSeconds * 1000,
                cooldownTime                  = Math.round((Math.max(((playerstate.getStoredSystemMillis()+rechargeDelayInMillis)-System.currentTimeMillis())/1000, 0))*10D)/10D;

        playerstate.setStoredCooldownTime(cooldownTime);

        if(player.getHungerManager().getFoodLevel() <= 0 && ConfigRegistry.CONFIG.starvingDrainsStamina) {
            playerstate.setStamina(playerstate.getStamina() - starvingDrainPerTick);
        }

        if (player.isSprinting() || player.isSwimming()) {
            if (!player.isCreative() && !player.isSpectator()) {
                playerstate.setStamina(playerstate.getStamina() - sprintingSwimmingDrainPerTick);

                // if expending stamina, set countdown to configurable recharge delay
                playerstate.updateStoredSystemMillis();
                serverState.markDirty();
            }
        } 
        else if (!player.isSprinting() && !player.isSwimming() && !player.isClimbing() && playerstate.getStamina() < playerstate.getMaxStamina()) {
            if(System.currentTimeMillis() >= (playerstate.getStoredSystemMillis()+rechargeDelayInMillis)) {
                playerstate.setStamina(playerstate.getStamina() + staminaRechargedPerTick);
            }
        } 

        if (playerstate.getStamina() >  playerstate.getMaxStamina()) {
            playerstate.setStamina(playerstate.getMaxStamina());
        }

        if (playerstate.getStamina() <= 0.0) {
            playerstate.setStamina(0.0);
            serverState.markDirty();
        }
    }
}
