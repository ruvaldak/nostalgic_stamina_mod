package realisticstamina.rstamina.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import realisticstamina.rstamina.PlayerState;
import realisticstamina.rstamina.RStaminaMod;
import realisticstamina.rstamina.ServerState;
import realisticstamina.rstamina.registry.ConfigRegistry;

public class UpdateStaminaC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        ServerState serverState = ServerState.getServerState(server);
        PlayerState playerstate = ServerState.getPlayerState(player);

        //double millisPerTick = System.currentTimeMillis() - RStaminaClient.getClientStoredSystemMillis(); //milliseconds since last tick
        // double ticksPerSecond = Math.min(20, 1000/millisPerTick);
        //double ticksPerSecond = Math.min(20, 1000/server.getTickTime()); //convert MSPT to TPS
        //double millisPerTick = server.getTickTime();
        //double secondsPerTick = millisPerTick/1000;
        double  cappedSecondsPerTick          = Math.max(0.05, server.getTickTime()/1000),
                staminaRechargedPerTick       = ConfigRegistry.CONFIG.staminaRechargedPerSecond * cappedSecondsPerTick,
                starvingDrainPerTick          = ConfigRegistry.CONFIG.starvingDrainPerSecond * cappedSecondsPerTick,
                sprintingSwimmingDrainPerTick = ConfigRegistry.CONFIG.sprintingSwimmingDrainPerSecond * cappedSecondsPerTick,
                rechargeDelayInMillis         = ConfigRegistry.CONFIG.rechargeDelayInSeconds * 1000;
        //int     rechargeDelayInTicks          = (int)Math.round(ConfigRegistry.CONFIG.rechargeDelayInSeconds / cappedSecondsPerTick);
        //RStaminaClient.updateClientStoredSystemMillis();

        if(player.getHungerManager().getFoodLevel() <= 0 && ConfigRegistry.CONFIG.starvingDrainsStamina) {
            playerstate.setStamina(playerstate.getStamina() - starvingDrainPerTick);
        }

        if (player.isSprinting() || player.isSwimming()) {
            if (!player.isCreative() && !player.isSpectator()) {
                playerstate.setStamina(playerstate.getStamina() - sprintingSwimmingDrainPerTick);

                
                if(System.currentTimeMillis() - RStaminaMod.getClientStoredSystemMillis()+rechargeDelayInMillis >= 0) {
                    if(ConfigRegistry.CONFIG.extraLogging) 
                        //extra Logging
                        RStaminaMod.LOGGER.info("Setting recharge countdown to: " + Math.floor(ConfigRegistry.CONFIG.rechargeDelayInSeconds * 100) / 100d + " seconds.");
                    
                    // if expending stamina, set countdown to configurable recharge delay
                    RStaminaMod.updateClientStoredSystemMillis();
                }
                serverState.markDirty();
            }
        } 
        else if (!player.isSprinting() && !player.isSwimming() && !player.isClimbing() && playerstate.getStamina() < playerstate.getMaxStamina()) {
            if(System.currentTimeMillis() >= (RStaminaMod.getClientStoredSystemMillis()+rechargeDelayInMillis)) {
                playerstate.setStamina(playerstate.getStamina() + staminaRechargedPerTick);
            }
            /*if(playerstate.getCountdown() > 0) {
                //extra Logging
                if(ConfigRegistry.CONFIG.extraLogging) {
                    RStaminaMod.LOGGER.info("Recharge delay countdown: " + Math.floor(playerstate.getCountdown() * 100) / 100d);
                }
                
                //reduce countdown every tick
                playerstate.setCountdown(Math.max(0,playerstate.getCountdown()-cappedSecondsPerTick));
                if(ConfigRegistry.CONFIG.extraLogging && playerstate.getCountdown() <= 0) 
                        RStaminaMod.LOGGER.info("Beginning recharge");
            } 
            else if(playerstate.getCountdown() <= 0) {
                //extra logging
                if(playerstate.getCountdown() < 0)
                    playerstate.setCountdown(0);
                // if countdown hits zero, begin recharging stamina.
                playerstate.setStamina(playerstate.getStamina() + staminaRechargedPerTick);
            }*/
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
