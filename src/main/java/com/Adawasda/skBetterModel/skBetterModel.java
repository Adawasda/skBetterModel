package com.Adawasda.skBetterModel;

import ch.njol.skript.Skript;
import ch.njol.skript.util.Version;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.addon.AddonModule;
import org.skriptlang.skript.addon.SkriptAddon;
import org.skriptlang.skript.registration.SyntaxRegistry;

import com.Adawasda.skBetterModel.utils.PluginConfig;

import com.Adawasda.skBetterModel.elements.effects.*;
import com.Adawasda.skBetterModel.elements.events.*;
import com.Adawasda.skBetterModel.elements.expressions.*;
import com.Adawasda.skBetterModel.elements.expressions.properties.animation.*;
import com.Adawasda.skBetterModel.elements.expressions.properties.bone.*;
import com.Adawasda.skBetterModel.elements.expressions.properties.modifier.*;
import com.Adawasda.skBetterModel.elements.expressions.properties.tracker.*;
import com.Adawasda.skBetterModel.elements.sections.*;
import com.Adawasda.skBetterModel.elements.types.*;

public class SkBetterModel extends JavaPlugin {

    private static @Nullable SkBetterModel instance;

    public static @Nullable SkBetterModel getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        PluginConfig.init(this);
        PluginConfig.get().reload();


        AddonLoader loader = new AddonLoader(instance);
        if (loader.load()) {
            loader.getComponentLogger().info("skBetterModel loaded successfully.");
        }
    }

}
