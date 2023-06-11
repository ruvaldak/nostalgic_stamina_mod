package com.ruvaldak.nostalgicstamina.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import com.ruvaldak.nostalgicstamina.ServerState;
import com.ruvaldak.nostalgicstamina.networking.NetworkingPackets;

public class RequestPlayerStateC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {

        PacketByteBuf sendingdata = PacketByteBufs.create();
        sendingdata.writeDouble(ServerState.getPlayerState(player).getStamina()); //stamina
        sendingdata.writeDouble(ServerState.getPlayerState(player).getMaxStamina()); //max stamina
        sendingdata.writeDouble(ServerState.getPlayerState(player).getCountdown());

        ServerPlayNetworking.send(player, NetworkingPackets.SEND_PLAYERSTATE_S2C_PACKET_ID, sendingdata);

    }

}
