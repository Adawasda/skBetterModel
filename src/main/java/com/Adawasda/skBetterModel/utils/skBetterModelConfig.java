package com.Adawasda.skBetterModel.utils;

import org.bukkit.configuration.file.FileConfiguration;

import com.Adawasda.skBetterModel.skBetterModel;

public class skBetterModelConfig {
    
    private final skBetterModel plugin;
    private static skBetterModelConfig instance;
    
    // Body Rotator default values 
    private float minBody;
    private float maxBody;
    private float minHead;
    private float maxHead;
    private boolean bodyUneven;
    private boolean headUneven;
    private int rotationDuration;
    private int rotationDelay;


    private skBetterModelConfig(skBetterModel plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        reload();
    }

    public static skBetterModelConfig getInstance(skBetterModel plugin) {
        return new skBetterModelConfig(plugin);
    }


    public static void init(skBetterModel plugin) {
        instance = new skBetterModelConfig(plugin);
    }

    public static skBetterModelConfig get() {
        return instance;
    }

    public void reload() {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        
        minBody = (float) config.getDouble("body-rotator.min-body", -180);
        maxBody = (float) config.getDouble("body-rotator.max-body", 180);
        minHead = (float) config.getDouble("body-rotator.min-head", -180);
        maxHead = (float) config.getDouble("body-rotator.max-head", 180);
        bodyUneven = config.getBoolean("body-rotator.body-uneven", false);
        headUneven = config.getBoolean("body-rotator.head-uneven", false);
        rotationDuration = config.getInt("body-rotator.rotation-duration", 0);
        rotationDelay = config.getInt("body-rotator.rotation-delay", 0);
    }   


    public float getMinBody() { return minBody; }
    public float getMaxBody() { return maxBody; }
    public float getMinHead() { return minHead; }
    public float getMaxHead() { return maxHead; }
    public boolean isBodyUneven() { return bodyUneven; }
    public boolean isHeadUneven() { return headUneven; }
    public int getRotationDuration() { return rotationDuration; }
    public int getRotationDelay() { return rotationDelay; }

}

