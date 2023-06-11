package com.ruvaldak.nostalgicstamina;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruvaldak.nostalgicstamina.networking.NetworkingPackets;
import com.ruvaldak.nostalgicstamina.registry.*;

public class NostalgicStaminaMod implements ModInitializer {

	public static final String modid = "nostalgicstamina";
	public static final Logger LOGGER = LoggerFactory.getLogger(modid);

	private static long clientStoredSystemMillis = System.currentTimeMillis();

	public static long getClientStoredSystemMillis() {
        return clientStoredSystemMillis;
    }

	public static void updateClientStoredSystemMillis() {
        clientStoredSystemMillis = System.currentTimeMillis();
    }

	@Override
	public void onInitialize() {
		//networking
		NetworkingPackets.registerC2SPackets();

		//registry register
		ConfigRegistry.register();

		//events
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerState serverState = ServerState.getServerState(handler.player.world.getServer());
			PlayerState playerState = ServerState.getPlayerState(handler.player);
		});

	}
}