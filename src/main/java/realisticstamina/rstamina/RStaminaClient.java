package realisticstamina.rstamina;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
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

    }

}
