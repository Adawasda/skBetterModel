package com.Adawasda.skBetterModel.core;

import kr.toxicity.model.api.bone.BoneName;
import kr.toxicity.model.api.bone.RenderedBone;
import kr.toxicity.model.api.tracker.EntityTracker;
import kr.toxicity.model.api.util.function.BonePredicate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class BoneController {

    private final EntityTracker tracker;
    private final RenderedBone bone;
    private final Quaternionf customRotation = new Quaternionf();

    public BoneController(@NotNull EntityTracker tracker, @NotNull RenderedBone bone) {
        this.tracker = tracker;
        this.bone = bone;
        this.bone.addRotationModifier(BonePredicate.TRUE, rotation -> 
            rotation.mul(customRotation, new Quaternionf()) );
    }

    public @NotNull RenderedBone getBone() {
        return bone;
    }

    public @NotNull EntityTracker getTracker() {
        return tracker;
    }

    public @NotNull String getName() {
        BoneName boneName = bone.name();
        return boneName != null ? boneName.name() : "unknown";
    }

    public @NotNull Quaternionf getRotation() {
        return new Quaternionf(customRotation);
    }

    public void setRotation(float x, float y, float z, float w) {
        customRotation.set(x, y, z, w);
        tracker.forceUpdate(true);
    }

    public void setRotationEuler(float pitch, float yaw, float roll) {
        customRotation.identity()
                .rotateX((float) Math.toRadians(pitch))
                .rotateY((float) Math.toRadians(yaw))
                .rotateZ((float) Math.toRadians(roll));
        tracker.forceUpdate(true);
    }

    public void resetRotation() {
        customRotation.identity();
        tracker.forceUpdate(true);
    }

    public @Nullable Vector3f getWorldPosition() {
        return bone.worldPosition();
    }

    public @Nullable Vector3f getWorldRotation() {
        return bone.worldRotation();
    }

    public void setVisible(boolean visible) {
        if (visible) {
            bone.applyAtDisplay(b -> b.equals(bone), display -> {});
        }
    }

    public void setEnchanted(boolean enchanted) {
        bone.enchant(b -> b.equals(bone), enchanted);
    }

    public void setScale(float scale) {
        bone.scale(() -> scale);
    }
}
