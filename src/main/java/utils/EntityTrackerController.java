package utils;

import kr.toxicity.model.api.BetterModel;
import kr.toxicity.model.api.animation.AnimationModifier;
import kr.toxicity.model.api.animation.RunningAnimation;
import kr.toxicity.model.api.bukkit.platform.BukkitAdapter;
import kr.toxicity.model.api.data.blueprint.BlueprintAnimation;
import kr.toxicity.model.api.profile.ModelProfile;
import kr.toxicity.model.api.tracker.EntityTracker;
import kr.toxicity.model.api.tracker.EntityTrackerRegistry;
import kr.toxicity.model.api.tracker.ModelScaler;
import kr.toxicity.model.api.tracker.TrackerUpdateAction;

import java.util.Map;
import java.util.Set;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.joml.Vector3f;

import com.Adawasda.skBetterModel.skBetterModel;

public class EntityTrackerController {

    private final EntityTracker tracker;
    private final skBetterModel plugin = skBetterModel.getInstance();

    public EntityTrackerController(Entity entity) {
        EntityTrackerRegistry registry = EntityTrackerRegistry.registry(entity.getUniqueId());  
        if (registry != null) {  
            this.tracker = registry.first();  
        }  else {
            tracker = null;
        }
    }


    public EntityTrackerController(Entity entity, String model) {
        this.tracker = BetterModel.model(model)
                .map(r -> r.getOrCreate(BukkitAdapter.adapt(entity)))
                .orElse(null);
    }

    public EntityTrackerController(Entity entity, String model, OfflinePlayer player) {
        this.tracker = BetterModel.limb(model)
            .map(r -> r.getOrCreate(BukkitAdapter.adapt(entity), ModelProfile.of(player.getUniqueId())))
            .orElse(null);
    }

    public EntityTrackerController(EntityTracker tracker) {
        this.tracker = tracker;
    }


    public EntityTracker getTracker() {
        return tracker;
    }

    public void setScale(float scale) {
        if (tracker == null) return;
        tracker.scaler(ModelScaler.value(scale));
    }

    public void setBrightness(int block, int sky) {
        if (tracker == null) return;
        tracker.update(TrackerUpdateAction.brightness(block, sky));
    }

    public void setGlow(boolean glow) {
        if (tracker == null) return;
        tracker.update(TrackerUpdateAction.glow(glow));
    }

    public void setGlowColor(int color) {
        if (tracker == null) return;
        tracker.update(TrackerUpdateAction.glowColor(color));
    }

    public void setViewRange(int range) {
        if (tracker == null) return;
        tracker.update(TrackerUpdateAction.viewRange(range));
    }

    public void setTint(int color) {
        if (tracker == null) return;
        tracker.update(TrackerUpdateAction.tint(color));
    }

    public RunningAnimation getRunningAnimation() {
        if (tracker == null) return null;
        return tracker.getPipeline().runningAnimation();
    }

    public void animate(String animationName, AnimationModifier modifier, Boolean forced) {
        if (!forced && getRunningAnimation() != null && getRunningAnimation().name().equals(animationName))
            return;

        tracker.animate(animationName, modifier);
    }

    public void setOffset(Vector3f offset) {
        if (tracker == null) return;
        return;
        //!TODO
    }

    public Map<String, BlueprintAnimation> getAnimations() {
        if (tracker == null) return null;
        return tracker.renderer().animations();
    }
 
}