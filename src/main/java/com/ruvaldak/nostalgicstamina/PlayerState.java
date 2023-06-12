package com.ruvaldak.nostalgicstamina;
import com.ruvaldak.nostalgicstamina.registry.ConfigRegistry;

public class PlayerState {
    private long storedSystemMillis = System.currentTimeMillis();
    private double  maxStamina = ConfigRegistry.CONFIG.maxStamina,
                    stamina    = 1000000,
                    storedCooldownTime = 0;

    public double getMaxStamina() {
        return maxStamina;
    }

    public double getStamina() {
        return stamina;
    }

    public double getStoredCooldownTime() {
        return storedCooldownTime;
    }

    public long getStoredSystemMillis() {
        return storedSystemMillis;
    }

    public void setMaxStamina(double maxStamina) {
        this.maxStamina = maxStamina;
    }

    public void setStamina(double stamina) {
        this.stamina = stamina;
    }

    public void setStoredCooldownTime(double time) {
        storedCooldownTime = time;
    }

    public void updateStoredSystemMillis() {
        storedSystemMillis = System.currentTimeMillis();
    }
}
