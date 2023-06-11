package realisticstamina.rstamina.gui;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import realisticstamina.rstamina.RStaminaClient;
import realisticstamina.rstamina.networking.NetworkingPackets;
import realisticstamina.rstamina.registry.ConfigRegistry;

public class StaminaHudDebug implements HudRenderCallback {

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.floor(value * scale) / scale;
    }

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        int x = 10;
        int y = 25;

        MinecraftClient client = MinecraftClient.getInstance();

        if (client != null && ConfigRegistry.CONFIG.debugHUD) {

            ClientPlayNetworking.send(NetworkingPackets.REQUEST_PLAYERSTATE_C2S_PACKET_ID, PacketByteBufs.create());

            TextRenderer textRenderer = client.textRenderer;

            if ((round(RStaminaClient.getClientStoredStamina(), 1)) > 24.0 && (round(RStaminaClient.getClientStoredStamina(), 1)) == (Math.ceil(RStaminaClient.getClientStoredMaxStamina()))) {
                textRenderer.drawWithShadow(matrixStack, Text.literal("§aStamina: §a" + (round(RStaminaClient.getClientStoredStamina(), 1)) + "§7/" + (Math.ceil(RStaminaClient.getClientStoredMaxStamina()))), x, y, 16777215);
            } else if ((round(RStaminaClient.getClientStoredStamina(), 1)) >= 24.0 && (round(RStaminaClient.getClientStoredStamina(), 1)) < (Math.ceil(RStaminaClient.getClientStoredMaxStamina()))) {
                textRenderer.drawWithShadow(matrixStack, Text.literal("§2Stamina: §a" + (round(RStaminaClient.getClientStoredStamina(), 1)) + "§7/" + (Math.ceil(RStaminaClient.getClientStoredMaxStamina()))), x, y, 16777215);
            } else if ((round(RStaminaClient.getClientStoredStamina(), 1)) < 24.0 && (round(RStaminaClient.getClientStoredStamina(), 1)) > 12) {
                textRenderer.drawWithShadow(matrixStack, Text.literal("§2Stamina: §e" + (round(RStaminaClient.getClientStoredStamina(), 1)) + "§7/" + (Math.ceil(RStaminaClient.getClientStoredMaxStamina()))), x, y, 16777215);
            } else if ((round(RStaminaClient.getClientStoredStamina(), 1)) <= 12 && (round(RStaminaClient.getClientStoredStamina(), 1)) > 0) {
                textRenderer.drawWithShadow(matrixStack, Text.literal("§2Stamina: §6" + (round(RStaminaClient.getClientStoredStamina(), 1)) + "§7/" + (Math.ceil(RStaminaClient.getClientStoredMaxStamina()))), x, y, 16777215);
            } else if ((round(RStaminaClient.getClientStoredStamina(), 1)) <= 0) {
                textRenderer.drawWithShadow(matrixStack, Text.literal("§2Stamina: §c" + (round(RStaminaClient.getClientStoredStamina(), 1)) + "§7/" + (Math.ceil(RStaminaClient.getClientStoredMaxStamina()))), x, y, 16777215);
            }

            textRenderer.drawWithShadow(matrixStack, Text.literal("§eRecharge Cooldown: §f" + round(RStaminaClient.getClientStoredCountdown(),2)), x, y + 10, 16777215);
        }
    }
}