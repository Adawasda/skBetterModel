package com.Adawasda.skBetterModel.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import kr.toxicity.model.api.animation.AnimationModifier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TypeAnimationModifier {

    public static void register() {
        if (Classes.getExactClassInfo(AnimationModifier.class) == null) {
            Classes.registerClass(new ClassInfo<>(AnimationModifier.class, "modifier")
                .user("modifiers?")
                .name("Animation Modifier")
                .description("Represents a blueprint animation modifier.")
                .parser(new Parser<AnimationModifier>() {
                    @Override
                    public @Nullable AnimationModifier parse(@NotNull String s, @NotNull ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }

                    @Override
                    public @NotNull String toVariableNameString(AnimationModifier o) {
                        return "animationmodifier_" +
                            o.start() + "_" +
                            o.end() + "_" +
                            o.priority() + "_" +
                            (o.type() != null ? o.type().name() : "null") + "_" +
                            o.speedValue() + "_" +
                            (o.override() != null ? o.override() : "null");
                    }

                    @Override
                    public @NotNull String toString(AnimationModifier o, int flags) {
                        return "animation modifier (start=" + o.start() +
                            ", end=" + o.end() +
                            ", priority=" + o.priority() +
                            ", type=" + o.type() +
                            ", speed=" + o.speedValue() + ")";
                    }

                })
            );
        }
    }
}