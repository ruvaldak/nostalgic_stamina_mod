package realisticstamina.rstamina.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "nostalgic-stamina")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class StaminaConfig implements ConfigData {
    // Hud X
    @ConfigEntry.Category("hud_position_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public int hud_x = 0;

    // Hud X
    @ConfigEntry.Category("hud_position_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public int hud_y = 0;

    @ConfigEntry.Category("stamina_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public float rechargeDelayInSeconds = 3.0F;

    @ConfigEntry.Category("stamina_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public double maxStamina = 20.0;

    @ConfigEntry.Category("stamina_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public double staminaRechargedPerSecond = 1.25;

    @ConfigEntry.Category("stamina_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public double sprintingSwimmingDrainPerSecond = 2.5;

    @ConfigEntry.Category("stamina_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public boolean starvingDrainsStamina = false;

    @ConfigEntry.Category("stamina_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public double starvingDrainPerSecond = 0.625;

    @ConfigEntry.Category("debug_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public boolean extraLogging = false;

    @ConfigEntry.Category("debug_settings")
    @ConfigEntry.Gui.Tooltip(count = 3)
    public boolean debugHUD = false;
}
