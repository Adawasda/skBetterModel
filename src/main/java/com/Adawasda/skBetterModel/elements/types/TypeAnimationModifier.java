package com.Adawasda.skBetterModel.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import kr.toxicity.model.api.animation.AnimationModifier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeAnimationModifier {

    private TypeAnimationModifier() {}

    public static void register() {
        if (Classes.getExactClassInfo(AnimationModifier.class) != null) return;
        Classes.registerClass(new ClassInfo<>(AnimationModifier.class, "animationmodifier")
                .user("animation modifiers?")
                .name("Animation Modifier")
                .description("Represents a built animation modifier with playback settings.")
                .parser(new Parser<>() {
                    @Override
                    public @Nullable AnimationModifier parse(@NotNull String s, @NotNull ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }

                    @Override
                    public @NotNull String toVariableNameString(AnimationModifier modifier) {
                        return "animationmodifier_" + modifier.start() + "_" + modifier.end()
                                + "_" + modifier.priority() + "_" + modifier.speedValue();
                    }

                    @Override
                    public @NotNull String toString(AnimationModifier modifier, int flags) {
                        return "animation modifier(speed=" + modifier.speedValue()
                                + ", start=" + modifier.start()
                                + ", end=" + modifier.end()
                                + ", priority=" + modifier.priority() + ")";
                    }
                })
        );
    }
}
