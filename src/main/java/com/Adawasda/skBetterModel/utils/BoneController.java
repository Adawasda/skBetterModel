package com.Adawasda.skBetterModel.utils;

import org.joml.Quaternionf;

import com.Adawasda.skBetterModel.skBetterModel;

import kr.toxicity.model.api.bone.RenderedBone;
import kr.toxicity.model.api.tracker.EntityTracker;
import kr.toxicity.model.api.util.TransformedItemStack;
import kr.toxicity.model.api.util.function.BonePredicate;

@SuppressWarnings("unused")

public class BoneController {

    private final EntityTracker tracker;
    private final RenderedBone bone;
    private final Quaternionf customRotation = new Quaternionf();
    private TransformedItemStack tItemStack;
    
    public BoneController(EntityTracker tracker, RenderedBone bone) {
        this.tracker = tracker;
        this.bone = bone;
        this.tItemStack = bone.getGroup().getItemStack().copy();

        this.bone.addGlobalRotModifier(BonePredicate.TRUE, animationRotation ->
                animationRotation.mul(customRotation, new Quaternionf()));
    }

    //TODO add more methods for rendered bone

}
