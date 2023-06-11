package realisticstamina.rstamina.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import realisticstamina.rstamina.gui.StaminaHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    // Get the client
    @Shadow
    @Final
    @Mutable
    private MinecraftClient client;


    // Get the player
    @Shadow
    private PlayerEntity getCameraPlayer() {
        return null;
    }


    // Get the ticks
    @Shadow
    private int ticks;


    // Render the thirst bar
    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", ordinal = 1))
    private void staminaMod$renderStaminaHud(MatrixStack matrixStack, CallbackInfo cir) {
        StaminaHud.renderStaminaHud(matrixStack, client, this.getCameraPlayer(), ticks);
    }


    // Modify the heart return value, so the air bar doesn't overlap with the thirst bar
    @Inject(method = "getHeartRows", at = @At(value = "HEAD"), cancellable = true)
    private void staminaMod$fixStaminaBarPosition(int heartCount, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue((int) Math.ceil((double) heartCount / 10.0D) + 1);
    }
}
