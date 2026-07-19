package com.Adawasda.skBetterModel.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import kr.toxicity.model.api.animation.AnimationModifier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TypeAnimationModifierBuilder {

    public static void register() {
        if (Classes.getExactClassInfo(AnimationModifier.Builder.class) == null) {
            Classes.registerClass(new ClassInfo<>(AnimationModifier.Builder.class, "modifier")
                .user("modifiers?")
                .name("Animation Modifier Builder")
                .description("Represents a mutable animation modifier.")
                .parser(new Parser<AnimationModifier.Builder>() {
                    @Override
                    public @Nullable AnimationModifier.Builder parse(@NotNull String s, @NotNull ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }

                    @Override
                    public @NotNull String toVariableNameString(AnimationModifier.Builder o) {
                        return "modifierbuilder_" + System.identityHashCode(o);
                    }

                    @Override
                    public @NotNull String toString(AnimationModifier.Builder o, int flags) {
                        AnimationModifier built = o.build();
                        return "modifier (start=" + built.start() +
                            ", end=" + built.end() +
                            ", priority=" + built.priority() +
                            ", speed=" + built.speedValue() + ")";
                    }
                })
            );
        }
    }
}