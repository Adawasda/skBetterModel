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

public class SkBetterModel extends JavaPlugin implements AddonModule {

    private static @Nullable SkBetterModel instance;

    public static @Nullable SkBetterModel getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        PluginConfig.init(this);
        PluginConfig.get().reload();

        Plugin skript = getServer().getPluginManager().getPlugin("Skript");

        if (skript == null || !skript.isEnabled()) {
            getLogger().severe("Skript not found or disabled. Disabling skBetterModel...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (Skript.getVersion().isSmallerThan(new Version("2.14.3"))) {
            getLogger().severe("Skript 2.14.3 or newer is required. Disabling...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Plugin betterModel = getServer().getPluginManager().getPlugin("BetterModel");
        if (betterModel == null || !betterModel.isEnabled()) {
            getLogger().severe("BetterModel not found or disabled. Disabling skBetterModel...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        SkriptAddon addon = Skript.instance().registerAddon(SkBetterModel.class, "skBetterModel");
        addon.localizer().setSourceDirectories("lang", null);
        addon.loadModules(this);
    }

    @Override
    public @NotNull String name() {
        return "skBetterModel";
    }

    @Override
    public void init(@NotNull SkriptAddon addon) {
        TypeRunningAnimation.register();
        TypeEntityTracker.register();
        TypeModelRenderer.register();
        TypeBlueprintAnimation.register();
        TypeAnimationModifier.register();
        TypeAnimationModifierBuilder.register();
        TypeRenderedBone.register();
    }

    @Override
    public void load(@NotNull SkriptAddon addon) {
        SyntaxRegistry registry = addon.syntaxRegistry();

        ExprLastEntityTracker.register(registry);
        ExprAllEntityTrackers.register(registry);
        ExprAllModelRenderers.register(registry);
        ExprActiveAnimation.register(registry);
        ExprModelAnimations.register(registry);
        ExprTrackerBones.register(registry);
        ExprBoneByName.register(registry);
        ExprEntityTrackerOf.register(registry);

        ExprAnimationName.register();
        ExprAnimationLength.register();
        ExprModifierSpeed.register();
        ExprModifierEnd.register();
        ExprModifierStart.register();
        ExprBoneName.register();
        ExprBoneEnchanted.register();
        ExprBoneScale.register();
        ExprTrackerScale.register();
        ExprTrackerGlow.register();
        ExprTrackerViewRange.register();

        EffSecPlayAnimation.register(registry);
        EffStopAnimation.register(registry);
        EffSpawnTracker.register(registry);
        EffDespawnTracker.register(registry);
        EffDisguise.register(registry);

        EvtBMReloadStart.register(registry);
        EvtBMReloadEnd.register(registry);
        EvtDummyTrackerCreate.register(registry);
        EvtEntityTrackerCreate.register(registry);
        EvtEntityTrackerHide.register(registry);
        EvtEntityTrackerShow.register(registry);
        EvtModelSpawnAtPlayer.register(registry);
        EvtModelDespawnAtPlayer.register(registry);

        SecCreateEntityTracker.register(registry);
    }
}
