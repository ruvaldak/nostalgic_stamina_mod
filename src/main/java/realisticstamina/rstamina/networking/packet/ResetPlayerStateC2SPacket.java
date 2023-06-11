package realisticstamina.rstamina.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import realisticstamina.rstamina.PlayerState;
import realisticstamina.rstamina.ServerState;
import realisticstamina.rstamina.registry.ConfigRegistry;

public class ResetPlayerStateC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {

        ServerState serverState = ServerState.getServerState(server);
        PlayerState playerstate = ServerState.getPlayerState(player);

        playerstate.setMaxStamina(ConfigRegistry.CONFIG.maxStamina);
        playerstate.setStamina(playerstate.getMaxStamina());
        playerstate.setCountdown(0);
        serverState.markDirty();

    }

}
