package realisticstamina.rstamina.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import realisticstamina.rstamina.RStaminaClient;
import realisticstamina.rstamina.RStaminaMod;
import realisticstamina.rstamina.networking.packet.*;

public class NetworkingPackets {
    //C2S
    public static final Identifier UPDATE_STAMINA_C2S_PACKET_ID = new Identifier(RStaminaMod.modid, "update_stamina_c2s_packet");
    public static final Identifier REQUEST_PLAYERSTATE_C2S_PACKET_ID = new Identifier(RStaminaMod.modid, "request_playerstate_c2s_packet");
    public static final Identifier PLAYER_SLEEP_C2S_PACKET_ID = new Identifier(RStaminaMod.modid, "player_sleep_c2s_packet");
    public static final Identifier RESET_PLAYERSTATE_C2S_PACKET_ID = new Identifier(RStaminaMod.modid, "reset_playerstate_c2s_packet");

    //S2C
    public static final Identifier SEND_PLAYERSTATE_S2C_PACKET_ID = new Identifier(RStaminaMod.modid, "send_playerstate_s2c_packet");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(UPDATE_STAMINA_C2S_PACKET_ID, UpdateStaminaC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(REQUEST_PLAYERSTATE_C2S_PACKET_ID, RequestPlayerStateC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(RESET_PLAYERSTATE_C2S_PACKET_ID, ResetPlayerStateC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SEND_PLAYERSTATE_S2C_PACKET_ID, (client, handler, buf, responseSender) -> {
            double stamina = buf.readDouble();
            double maxStamina = buf.readDouble();
            int countdown = buf.readInt();
            client.execute(() -> {
                RStaminaClient.setClientStoredStamina(stamina);
                RStaminaClient.setClientStoredMaxStamina(maxStamina);
                RStaminaClient.setClientStoredCountdown(countdown);
            });
        });
    }
}
