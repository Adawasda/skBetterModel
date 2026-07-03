package com.Adawasda.skBetterModel;

import ch.njol.skript.Skript;
import ch.njol.skript.util.Version;
import com.Adawasda.skBetterModel.utils.skBetterModelConfig;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.addon.AddonModule;
import org.skriptlang.skript.addon.SkriptAddon;
import org.skriptlang.skript.registration.SyntaxRegistry;

import com.Adawasda.skBetterModel.elements.effects.EffSecPlayAnimation;
import com.Adawasda.skBetterModel.elements.effects.EffSecPlayAnimation;
import com.Adawasda.skBetterModel.elements.events.EvtBMReloadEnd;
import com.Adawasda.skBetterModel.elements.events.EvtBMReloadStart;
import com.Adawasda.skBetterModel.elements.events.EvtDummyTrackerCreate;
import com.Adawasda.skBetterModel.elements.events.EvtEntityTrackerCreate;
import com.Adawasda.skBetterModel.elements.events.EvtEntityTrackerHide;
import com.Adawasda.skBetterModel.elements.events.EvtEntityTrackerShow;
import com.Adawasda.skBetterModel.elements.events.EvtModelDespawnAtPlayer;
import com.Adawasda.skBetterModel.elements.events.EvtModelSpawnAtPlayer;
import com.Adawasda.skBetterModel.elements.expressions.ExprActiveAnimation;
import com.Adawasda.skBetterModel.elements.expressions.ExprAllEntityTrackers;
import com.Adawasda.skBetterModel.elements.expressions.ExprAllModelRenderers;
import com.Adawasda.skBetterModel.elements.expressions.ExprModelAnimations;
import com.Adawasda.skBetterModel.elements.expressions.ExprLastEntityTracker;
import com.Adawasda.skBetterModel.elements.expressions.properties.animation.ExprAnimationName;
import com.Adawasda.skBetterModel.elements.sections.SecCreateEntityTracker;
import com.Adawasda.skBetterModel.elements.types.TypeAnimationModifier;
import com.Adawasda.skBetterModel.elements.types.TypeBlueprintAnimation;
import com.Adawasda.skBetterModel.elements.types.TypeEntityTracker;
import com.Adawasda.skBetterModel.elements.types.TypeModelRenderer;
import com.Adawasda.skBetterModel.elements.types.TypeRunningAnimation;

@SuppressWarnings("unused")
public class skBetterModel extends JavaPlugin implements AddonModule {

	private static @Nullable skBetterModel instance;

	public static @Nullable skBetterModel getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
		skBetterModelConfig.init(this);
		skBetterModelConfig.get().reload();

		Plugin skript = getServer().getPluginManager().getPlugin("Skript");

		if (skript == null || !skript.isEnabled()) {
			getLogger().severe("Using Skript addon with no skript? Disabling...");
			getServer().getPluginManager().disablePlugin(this);
			return;
		} else if (Skript.getVersion().isSmallerThan(new Version("2.14.3"))) {
			getLogger().severe("Your Skript version is too old, update to 2.14.3 or newer. Disabling...");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		if (!isBetterModelEnabled()) {
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		SkriptAddon addon = Skript.instance().registerAddon(skBetterModel.class, "skBetterModel");
		addon.localizer().setSourceDirectories("lang", null);
		addon.loadModules(this);
	}

	@Override
	public @NotNull String name() {
		return "skBetterModel";
	}

	private boolean isBetterModelEnabled() {
		Plugin plugin = getServer().getPluginManager().getPlugin("BetterModel");
		
		if (plugin == null) {
			instance.getLogger().warning("BetterModel not found. Disabling...");
			return false;
		}

		return true;
	
	}

	@Override
	public void init(@NotNull SkriptAddon addon) {
		TypeRunningAnimation.register();
		TypeEntityTracker.register();
		TypeModelRenderer.register();
		TypeBlueprintAnimation.register();
		TypeAnimationModifier.register();
	}

	@Override
	public void load(@NotNull SkriptAddon addon) {
		SyntaxRegistry registry = addon.syntaxRegistry();

		// Register expressions
		ExprLastEntityTracker.register(registry);
		ExprAllEntityTrackers.register(registry);
		ExprModelAnimations.register(registry);
		ExprActiveAnimation.register(registry);
		ExprAllModelRenderers.register(registry);

		// Properties
		ExprAnimationName.register(registry);

		// Register conditions

		// Register effects
		EffSecPlayAnimation.register(registry);

		// Register events
		EvtBMReloadStart.register(registry);
		EvtBMReloadEnd.register(registry);
		EvtDummyTrackerCreate.register(registry);
		EvtEntityTrackerCreate.register(registry);
		EvtEntityTrackerHide.register(registry);
		EvtEntityTrackerShow.register(registry);
		EvtModelSpawnAtPlayer.register(registry);
		EvtModelDespawnAtPlayer.register(registry);


		// Register sections
		SecCreateEntityTracker.register(registry);

		// Register functions
	}

}
