package com.Adawasda.skBetterModel;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.plugin.Plugin;
import org.skriptlang.skript.addon.SkriptAddon;
import org.skriptlang.skript.registration.SyntaxRegistry;

import com.Adawasda.skBetterModel.elements.effects.EffDespawnTracker;
import com.Adawasda.skBetterModel.elements.effects.EffDisguise;
import com.Adawasda.skBetterModel.elements.effects.EffSecPlayAnimation;
import com.Adawasda.skBetterModel.elements.effects.EffSpawnTracker;
import com.Adawasda.skBetterModel.elements.effects.EffStopAnimation;
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
import com.Adawasda.skBetterModel.elements.expressions.ExprBoneByName;
import com.Adawasda.skBetterModel.elements.expressions.ExprEntityTrackerOf;
import com.Adawasda.skBetterModel.elements.expressions.ExprLastEntityTracker;
import com.Adawasda.skBetterModel.elements.expressions.ExprModelAnimations;
import com.Adawasda.skBetterModel.elements.expressions.ExprPlayerLimb;
import com.Adawasda.skBetterModel.elements.expressions.ExprTrackerBones;
import com.Adawasda.skBetterModel.elements.expressions.properties.animation.ExprAnimationLength;
import com.Adawasda.skBetterModel.elements.expressions.properties.animation.ExprAnimationName;
import com.Adawasda.skBetterModel.elements.expressions.properties.bone.ExprBoneEnchanted;
import com.Adawasda.skBetterModel.elements.expressions.properties.bone.ExprBoneName;
import com.Adawasda.skBetterModel.elements.expressions.properties.bone.ExprBoneScale;
import com.Adawasda.skBetterModel.elements.expressions.properties.modifier.ExprModifierEnd;
import com.Adawasda.skBetterModel.elements.expressions.properties.modifier.ExprModifierSpeed;
import com.Adawasda.skBetterModel.elements.expressions.properties.modifier.ExprModifierStart;
import com.Adawasda.skBetterModel.elements.expressions.properties.tracker.ExprTrackerGlow;
import com.Adawasda.skBetterModel.elements.expressions.properties.tracker.ExprTrackerScale;
import com.Adawasda.skBetterModel.elements.expressions.properties.tracker.ExprTrackerViewRange;
import com.Adawasda.skBetterModel.elements.sections.SecCreateEntityTracker;
import com.Adawasda.skBetterModel.elements.types.TypeAnimationModifier;
import com.Adawasda.skBetterModel.elements.types.TypeAnimationModifierBuilder;
import com.Adawasda.skBetterModel.elements.types.TypeBlueprintAnimation;
import com.Adawasda.skBetterModel.elements.types.TypeEntityTracker;
import com.Adawasda.skBetterModel.elements.types.TypeModelRenderer;
import com.Adawasda.skBetterModel.elements.types.TypeRenderedBone;
import com.Adawasda.skBetterModel.elements.types.TypeRunningAnimation;

import ch.njol.skript.Skript;
import ch.njol.skript.util.Version;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;


// Not my code, i got inspired by Crebs's BetterModelSk
public class AddonLoader {

    private static final ComponentLogger LOGGER = ComponentLogger.logger("skBetterModel");


    private final SkBetterModel plugin;
    private final Plugin skriptPlugin;

    private SkriptAddon addon;

    public AddonLoader(SkBetterModel plugin) {
        this.plugin = plugin;
        this.skriptPlugin = plugin.getServer().getPluginManager().getPlugin("Skript");
    }

    private static final List<Runnable> TYPES = List.of(
        TypeAnimationModifier::register,
        TypeAnimationModifierBuilder::register,
        TypeEntityTracker::register,
        TypeModelRenderer::register,
        TypeBlueprintAnimation::register,
        TypeRenderedBone::register,
        TypeRunningAnimation::register
    );


    private static final List<Consumer<SyntaxRegistry>> SECTIONS = List.of(
        SecCreateEntityTracker::register
    );

    private static final List<Consumer<SyntaxRegistry>> EFFECTS = List.of(
        EffSecPlayAnimation::register,
        EffStopAnimation::register,
        EffSpawnTracker::register,
        EffDespawnTracker::register,
        EffDisguise::register
    );

    private static final List<Consumer<SyntaxRegistry>> CONDITIONS = List.of();

    private static final List<Consumer<SyntaxRegistry>> EXPRESSIONS = List.of(
        ExprLastEntityTracker::register,
        ExprAllEntityTrackers::register,
        ExprAllModelRenderers::register,
        ExprActiveAnimation::register,
        ExprModelAnimations::register,
        ExprTrackerBones::register,
        ExprBoneByName::register,
        ExprEntityTrackerOf::register,
        ExprPlayerLimb::register
    );

    private static final List<Runnable> EXPRESSION_PROPERTIES = List.of(
        ExprAnimationName::register,
        ExprAnimationLength::register,
        ExprModifierSpeed::register,
        ExprModifierEnd::register,
        ExprModifierStart::register,
        ExprBoneName::register,
        ExprBoneEnchanted::register,
        ExprBoneScale::register,
        ExprTrackerScale::register,
        ExprTrackerGlow::register,
        ExprTrackerViewRange::register
    );

    private static final List<Consumer<SyntaxRegistry>> EVENTS = List.of(
        EvtBMReloadStart::register,
        EvtBMReloadEnd::register,
        EvtDummyTrackerCreate::register,
        EvtEntityTrackerCreate::register,
        EvtEntityTrackerHide::register,
        EvtEntityTrackerShow::register,
        EvtModelSpawnAtPlayer::register,
        EvtModelDespawnAtPlayer::register
    );



    @SuppressWarnings("null")
    public boolean load() {
        if (skriptPlugin == null || !skriptPlugin.isEnabled()) {
            plugin.getLogger().severe("Skript not found or disabled. Disabling skBetterModel...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return false;
        }

        if (Skript.getVersion().isSmallerThan(new Version("2.14.3"))) {
            plugin.getLogger().severe("Skript 2.14.3 or newer is required. Disabling...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return false;
        }

        Plugin betterModel = plugin.getServer().getPluginManager().getPlugin("BetterModel");
        if (betterModel == null || !betterModel.isEnabled()) {
            plugin.getLogger().severe("BetterModel not found or disabled. Disabling skBetterModel...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return false;
        } else {
            String version = betterModel.getPluginMeta().getVersion();
            if (!version.startsWith("2.")) {
                plugin.getLogger().severe("BetterModel 2.x.x or newer is required. Disabling...");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                return false;
            } 
        }

        this.addon = Skript.instance().registerAddon(SkBetterModel.class, plugin.name());
        addon.localizer().setSourceDirectories("lang", null);
        SyntaxRegistry registry = this.addon.syntaxRegistry();
        
        TYPES.forEach(Runnable::run);
        EXPRESSION_PROPERTIES.forEach(Runnable::run);
        loadElements(SECTIONS, registry);
        loadElements(EFFECTS, registry);
        loadElements(CONDITIONS, registry);
        loadElements(EXPRESSIONS, registry);
        loadElements(EVENTS, registry);

        int typeCount = TYPES.size();
        int sectionCount = SECTIONS.size();
        int effectCount = EFFECTS.size();
        int conditionCount = CONDITIONS.size();
        int expressionCount = EXPRESSIONS.size();
        int propertiesCount = EXPRESSION_PROPERTIES.size();
        int total = typeCount + sectionCount + effectCount + conditionCount + expressionCount + propertiesCount;

        getComponentLogger().info(
            Component.text("Loaded ", NamedTextColor.GRAY)
                .append(Component.text(total, NamedTextColor.AQUA))
                .append(Component.text(" elements", NamedTextColor.GRAY))
        );

        getComponentLogger().info(
            Component.text(typeCount, NamedTextColor.AQUA)
                .append(Component.text(" types", NamedTextColor.GRAY))
        );

        getComponentLogger().info(
            Component.text(sectionCount, NamedTextColor.AQUA)
                .append(Component.text(" sections", NamedTextColor.GRAY))
        );

        getComponentLogger().info(
            Component.text(effectCount, NamedTextColor.AQUA)
                .append(Component.text(" effects", NamedTextColor.GRAY))
        );

        getComponentLogger().info(
            Component.text(conditionCount, NamedTextColor.AQUA)
                .append(Component.text(" conditions", NamedTextColor.GRAY))
        );

        getComponentLogger().info(
            Component.text(expressionCount, NamedTextColor.AQUA)
                .append(Component.text(" expressions", NamedTextColor.GRAY))
        );

        getComponentLogger().info(
            Component.text(propertiesCount, NamedTextColor.AQUA)
                .append(Component.text(" properties", NamedTextColor.GRAY))
        );




        return true;
    }

    public SkriptAddon getAddon() {
        return this.addon;
    }

    public void loadElements(List<Consumer<SyntaxRegistry>> elements, SyntaxRegistry registry) {
        for (Consumer<SyntaxRegistry> element : elements) {
            try {
                element.accept(registry);
            } catch (Exception e) {
                throw new IllegalStateException("Failed to load", e);
            }
        }

    }

    public ComponentLogger getComponentLogger() {
        return LOGGER;
    }

    
}
