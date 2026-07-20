package com.Adawasda.skBetterModel.core;

import kr.toxicity.model.api.BetterModel;
import kr.toxicity.model.api.animation.AnimationModifier;
import kr.toxicity.model.api.animation.RunningAnimation;
import kr.toxicity.model.api.bone.RenderedBone;
import kr.toxicity.model.api.bukkit.platform.BukkitAdapter;
import kr.toxicity.model.api.data.blueprint.BlueprintAnimation;
import kr.toxicity.model.api.platform.PlatformBillboard;
import kr.toxicity.model.api.profile.ModelProfile;
import kr.toxicity.model.api.tracker.EntityTracker;
import kr.toxicity.model.api.tracker.EntityTrackerRegistry;
import kr.toxicity.model.api.tracker.ModelScaler;
import kr.toxicity.model.api.tracker.TrackerModifier;
import kr.toxicity.model.api.tracker.TrackerUpdateAction;
import kr.toxicity.model.api.tracker.EntityBodyRotator.RotatorData;
import kr.toxicity.model.api.util.function.BonePredicate;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class TrackerController {

    private final EntityTracker tracker;

    private TrackerController(@Nullable EntityTracker tracker) {
        this.tracker = tracker;
    }

    public static @NotNull TrackerController fromExisting(@NotNull Entity entity) {
        EntityTrackerRegistry registry = EntityTrackerRegistry.registry(entity.getUniqueId());
        EntityTracker found = (registry != null) ? registry.first() : null;
        return new TrackerController(found);
    }

    public static @NotNull TrackerController fromModel(@NotNull Entity entity, @NotNull String model) {
        EntityTracker created = BetterModel.model(model)
                .map(r -> r.getOrCreate(BukkitAdapter.adapt(entity), TrackerModifier.DEFAULT, t -> {
                    t.bodyRotator().lockRotation(false);
                    t.bodyRotator().setValue(data -> {
                        data.setMinBody(-180f);
                        data.setMaxBody(180f);
                        data.setMinHead(-180f);
                        data.setMaxHead(180f);
                        data.setBodyUneven(false);
                        data.setHeadUneven(false);
                        data.setRotationDuration(0);
                        data.setRotationDelay(0);
                    });
                }))
                .orElse(null);
        return new TrackerController(created);
    }

    public static @NotNull TrackerController fromLimb(@NotNull Entity entity, @NotNull String model,
                                                       @NotNull OfflinePlayer player) {
        EntityTracker created = BetterModel.limb(model)
                .map(r -> r.getOrCreate(BukkitAdapter.adapt(entity), ModelProfile.of(player.getUniqueId()),
                        TrackerModifier.DEFAULT, t -> {
                            t.bodyRotator().lockRotation(false);
                            t.bodyRotator().setValue(data -> {
                                data.setMinBody(-180f);
                                data.setMaxBody(180f);
                                data.setMinHead(-180f);
                                data.setMaxHead(180f);
                                data.setBodyUneven(false);
                                data.setHeadUneven(false);
                                data.setRotationDuration(0);
                                data.setRotationDelay(0);
                            });
                        }))
                .orElse(null);
        return new TrackerController(created);
    }

    public static @NotNull TrackerController wrap(@NotNull EntityTracker tracker) {
        return new TrackerController(tracker);
    }

    public @Nullable EntityTracker getTracker() {
        return tracker;
    }

    public boolean isValid() {
        return tracker != null && !tracker.isClosed();
    }

    public void setScale(float scale) {
        if (!isValid()) return;
        tracker.scaler(ModelScaler.value(scale));
    }

    public void setBrightness(int block, int sky) {
        if (!isValid()) return;
        tracker.update(TrackerUpdateAction.brightness(block, sky));
    }

    public void setGlow(boolean glow) {
        if (!isValid()) return;
        tracker.update(TrackerUpdateAction.glow(glow));
    }

    public void setGlowColor(int color) {
        if (!isValid()) return;
        tracker.update(TrackerUpdateAction.glowColor(color));
    }

    public void setViewRange(int range) {
        if (!isValid()) return;
        tracker.update(TrackerUpdateAction.viewRange(range));
    }

    public void setTint(int color) {
        if (!isValid()) return;
        tracker.update(TrackerUpdateAction.tint(color));
    }

    public void setOffset(@NotNull Vector3f offset) {
        if (!isValid()) return;
        var pipeline = tracker.getPipeline();
        pipeline.addPositionModifier(BonePredicate.TRUE, vec -> vec.add(offset.x, offset.y, offset.z));
        tracker.forceUpdate(true);
    }

    public void setBillboard(@NotNull Billboard billboard) {
        if (!isValid()) return;
        PlatformBillboard pb = switch (billboard) {
            case CENTER -> PlatformBillboard.CENTER;
            case VERTICAL -> PlatformBillboard.VERTICAL;
            case HORIZONTAL -> PlatformBillboard.HORIZONTAL;
            case FIXED -> PlatformBillboard.FIXED;
        };
        tracker.update(TrackerUpdateAction.billboard(pb));
    }

    public void setRotatorData(@NotNull Consumer<RotatorData> consumer) {
        if (!isValid()) return;
        tracker.bodyRotator().setValue(consumer);
    }

    public @Nullable RunningAnimation getRunningAnimation() {
        if (!isValid()) return null;
        return tracker.getPipeline().runningAnimation();
    }

    public void animate(@NotNull String name, @NotNull AnimationModifier modifier, boolean forced) {
        if (!isValid()) return;
        if (!forced) {
            RunningAnimation current = getRunningAnimation();
            if (current != null && current.name().equals(name)) return;
        }
        tracker.animate(name, modifier);
    }

    public @Nullable Map<String, BlueprintAnimation> getAnimations() {
        if (!isValid()) return null;
        return tracker.renderer().animations();
    }

    public @Nullable Collection<RenderedBone> getBones() {
        if (!isValid()) return null;
        return tracker.bones();
    }

    public @Nullable RenderedBone getBone(@NotNull String name) {
        if (!isValid()) return null;
        return tracker.bone(name);
    }

    public void close() {
        if (!isValid()) return;
        tracker.close();
    }

    public void despawn() {
        if (!isValid()) return;
        tracker.despawn();
    }
}
