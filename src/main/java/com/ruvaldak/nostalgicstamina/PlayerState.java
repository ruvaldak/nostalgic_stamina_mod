package com.ruvaldak.nostalgicstamina;
import com.ruvaldak.nostalgicstamina.registry.ConfigRegistry;

public class PlayerState {
    //public int testplayerdata = 200;
    
    private double  maxStamina = ConfigRegistry.CONFIG.maxStamina,
                    stamina    = 1000000,
                    countdown  = 0;

    public double getMaxStamina() {
        return maxStamina;
    }

    public double getStamina() {
        return stamina;
    }

    public double getCountdown() {
        return countdown;
    }

    public void setMaxStamina(double maxStamina) {
        this.maxStamina = maxStamina;
    }

    public void setStamina(double stamina) {
        this.stamina = stamina;
    }

    public void setCountdown(double countdown) {
        this.countdown = countdown;
    }
}
