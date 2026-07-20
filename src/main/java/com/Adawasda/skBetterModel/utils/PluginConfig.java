package com.Adawasda.skBetterModel.utils;

import org.bukkit.configuration.file.FileConfiguration;

import com.Adawasda.skBetterModel.SkBetterModel;

public final class PluginConfig {

    private static PluginConfig instance;

    private final SkBetterModel plugin;

    private float minBody;
    private float maxBody;
    private float minHead;
    private float maxHead;
    private boolean bodyUneven;
    private boolean headUneven;
    private int rotationDuration;
    private int rotationDelay;

    private float speed;
    private int priority;
    private boolean override;
    private int start;
    private int end;

    private PluginConfig(SkBetterModel plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        reload();
    }

    public static void init(SkBetterModel plugin) {
        instance = new PluginConfig(plugin);
    }

    public static PluginConfig get() {
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

        speed = (float) config.getDouble("animation.speed", 1);
        priority = config.getInt("animation.priority", 0);
        override = config.getBoolean("animation.override", false);
        start = config.getInt("animation.start", 0);
        end = config.getInt("animation.end", 0);
    }

    public float getMinBody() { return minBody; }
    public float getMaxBody() { return maxBody; }
    public float getMinHead() { return minHead; }
    public float getMaxHead() { return maxHead; }
    public boolean isBodyUneven() { return bodyUneven; }
    public boolean isHeadUneven() { return headUneven; }
    public int getRotationDuration() { return rotationDuration; }
    public int getRotationDelay() { return rotationDelay; }

    public float getSpeed() { return speed; }
    public int getPriority() { return priority; }
    public boolean isOverride() { return override; }
    public int getStart() { return start; }
    public int getEnd() { return end; }
}
