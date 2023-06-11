package realisticstamina.rstamina;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import realisticstamina.rstamina.gui.StaminaHudDebug;
import realisticstamina.rstamina.networking.NetworkingPackets;

public class RStaminaClient implements ClientModInitializer {

    //these numbers are only used to display stats on the client. they do nothing else
    private static double clientStoredStamina = 0;
    private static double clientStoredMaxStamina = 0;
    private static double clientStoredCountdown = 0;

    public static double getClientStoredStamina() {
        return clientStoredStamina;
    }
    public static double getClientStoredMaxStamina() {
        return clientStoredMaxStamina;
    }
    public static double getClientStoredCountdown() {
        return clientStoredCountdown;
    }

    public static void setClientStoredStamina(double stamina) {
        clientStoredStamina = stamina;
    }

    public static void setClientStoredMaxStamina(double maxStamina) {
        clientStoredMaxStamina = maxStamina;
    }

    public static void setClientStoredCountdown(double countdown) {
        clientStoredCountdown = countdown;
    }

    @Override
    public void onInitializeClient() {
        //networking
        NetworkingPackets.registerS2CPackets();

        //hud
        HudRenderCallback.EVENT.register(new StaminaHudDebug());

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
