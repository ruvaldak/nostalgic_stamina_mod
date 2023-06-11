package realisticstamina.rstamina.registry;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import realisticstamina.rstamina.config.StaminaConfig;

public class ConfigRegistry {
    public static StaminaConfig CONFIG = new StaminaConfig();

    public static void register() {
        AutoConfig.register(StaminaConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(StaminaConfig.class).getConfig();
    }
}
