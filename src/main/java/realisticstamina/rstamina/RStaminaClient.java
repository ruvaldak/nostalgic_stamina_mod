package realisticstamina.rstamina;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import realisticstamina.rstamina.client.StaminaHudOverlay;
import realisticstamina.rstamina.networking.NetworkingPackets;

public class RStaminaClient implements ClientModInitializer {

    public static int clientStoredTestPlayerData = 34;

    public static double clientStoredStamina = 400.0;
    public static double clientStoredMaxStamina = 551.0;

    @Override
    public void onInitializeClient() {



        //networking
        NetworkingPackets.registerS2CPackets();

        //hud
        HudRenderCallback.EVENT.register(new StaminaHudOverlay());

        //tick
        ClientTickEvents.START_CLIENT_TICK.register((client) -> {
            if (client.world != null) {



                if (client.world.isClient()) {
                    ClientPlayNetworking.send(NetworkingPackets.UPDATE_STAMINA_C2S_PACKET_ID, PacketByteBufs.create());
                }
            }
        });

    }

}
