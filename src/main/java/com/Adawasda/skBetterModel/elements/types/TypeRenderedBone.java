package com.Adawasda.skBetterModel.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import kr.toxicity.model.api.bone.RenderedBone;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeRenderedBone {

    private TypeRenderedBone() {}

    public static void register() {
        if (Classes.getExactClassInfo(RenderedBone.class) != null) return;
        Classes.registerClass(new ClassInfo<>(RenderedBone.class, "renderedbone")
                .user("rendered ?bones?")
                .name("Rendered Bone")
                .description("Represents a rendered bone within a model tracker.")
                .parser(new Parser<>() {
                    @Override
                    public @Nullable RenderedBone parse(@NotNull String s, @NotNull ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }

                    @Override
                    public @NotNull String toString(RenderedBone bone, int flags) {
                        return "bone[" + (bone.name() != null ? bone.name().name() : "unknown") + "]";
                    }

                    @Override
                    public @NotNull String toVariableNameString(RenderedBone bone) {
                        return "bone_" + (bone.name() != null ? bone.name().name() : "unknown");
                    }
                })
        );
    }
}
