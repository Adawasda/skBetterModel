package com.Adawasda.skBetterModel.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import kr.toxicity.model.api.animation.AnimationModifier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeAnimationModifierBuilder {

    private TypeAnimationModifierBuilder() {}

    public static void register() {
        if (Classes.getExactClassInfo(AnimationModifier.Builder.class) != null) return;
        Classes.registerClass(new ClassInfo<>(AnimationModifier.Builder.class, "modifier")
                .user("modifiers?")
                .name("Animation Modifier Builder")
                .description("Represents a mutable animation modifier builder for configuring playback.")
                .parser(new Parser<>() {
                    @Override
                    public @Nullable AnimationModifier.Builder parse(@NotNull String s, @NotNull ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }

                    @Override
                    public @NotNull String toVariableNameString(AnimationModifier.Builder builder) {
                        AnimationModifier built = builder.build();
                        return "modifier_" + built.start() + "_" + built.end()
                                + "_" + built.speedValue();
                    }

                    @Override
                    public @NotNull String toString(AnimationModifier.Builder builder, int flags) {
                        AnimationModifier built = builder.build();
                        return "modifier builder(speed=" + built.speedValue()
                                + ", start=" + built.start()
                                + ", end=" + built.end()
                                + ", priority=" + built.priority() + ")";
                    }
                })
        );
    }
}
