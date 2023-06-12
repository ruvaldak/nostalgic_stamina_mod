package com.ruvaldak.nostalgicstamina.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import com.ruvaldak.nostalgicstamina.identifier.HudTexturesIdentifiers;
import com.ruvaldak.nostalgicstamina.registry.ConfigRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import com.ruvaldak.nostalgicstamina.NostalgicStaminaClient;

@Environment(EnvType.CLIENT)
public class StaminaHud {
    public static void renderStaminaHud(MatrixStack matrixStack, MinecraftClient client, PlayerEntity playerEntity, int ticks) {

        // Get the client width and height
        if (playerEntity != null && !playerEntity.isCreative() && !playerEntity.isSpectator()) {
            int width = client.getWindow().getScaledWidth() / 2;
            int height = client.getWindow().getScaledHeight();

            // Defining the texture
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShaderTexture(0, HudTexturesIdentifiers.ICONS);

            //try to translate floating point stamina into a 20 segment status bar
            //int staminaValue = (RStaminaClient.getClientStoredStamina() <= Math.nextUp(0)) ? 0 : (int)Math.min(20.0d, Math.ceil(RStaminaClient.getClientStoredStamina()));
            //int staminaValue = (int)(RStaminaClient.getClientStoredStamina()<10D ?Math.ceil(RStaminaClient.getClientStoredStamina()):Math.floor(Math.nextUp(RStaminaClient.getClientStoredStamina())));
            
            int staminaValue = (int)Math.nextUp(Math.ceil((NostalgicStaminaClient.getClientStoredStamina()/21D)*20D)); 
            /* this is very cursed but it works 90% of the time
            current downsides are that some values don't round very well, like 1.1 become 2, 13.66 becomes 14, and 19.96 becomes 20. 
            visually, though, it looks consistent with expectations and increases/decreases linearly. */


            // Create the Stamina Bar
            // empty stamina segment
            for (int i = 0; i < 10; i++) {
                DrawableHelper.drawTexture(matrixStack,
                        (ConfigRegistry.CONFIG.hud_right) ? (width + 82 - (i * 9) + i) : (width - 91 + (i * 9) - i),
                        (height - 39) - (ConfigRegistry.CONFIG.hud_row * 10),
                        0,
                        0,
                        9,
                        9,
                        256,
                        256);
            }

            // half stamina segment
            for (int i = 0; i < 20; i++) {
                if (staminaValue != 0) {
                    if (((staminaValue + 1) / 2) > i) {
                        DrawableHelper.drawTexture(matrixStack,
                                (ConfigRegistry.CONFIG.hud_right) ? (width + 82 - (i * 9) + i) : (width - 91 + (i * 9) - i),
                                (height - 39) - (ConfigRegistry.CONFIG.hud_row * 10),
                                9,
                                9,
                                9,
                                9,
                                256,
                                256);
                    } else {
                        break;
                    }
                }
            }

            // full stamina segment
            for (int i = 0; i < 20; i++) {
                if (staminaValue != 0) {
                    if ((staminaValue / 2) > i) {
                        DrawableHelper.drawTexture(matrixStack,
                                (ConfigRegistry.CONFIG.hud_right) ? (width + 82 - (i * 9) + i) : (width - 91 + (i * 9) - i),
                                (height - 39) - (ConfigRegistry.CONFIG.hud_row * 10),
                                0,
                                9,
                                9,
                                9,
                                256,
                                256);
                    } else {
                        break;
                    }
                }
            }
            RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);
        }
    }
}
