package com.Adawasda.skBetterModel.elements.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import kr.toxicity.model.api.data.renderer.ModelRenderer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeModelRenderer {

    private TypeModelRenderer() {}

    public static void register() {
        if (Classes.getExactClassInfo(ModelRenderer.class) != null) return;
        Classes.registerClass(new ClassInfo<>(ModelRenderer.class, "modelrenderer")
                .user("model ?renderers?")
                .name("Model Renderer")
                .description("Represents a BetterModel model renderer blueprint.")
                .parser(new Parser<>() {
                    @Override
                    public @Nullable ModelRenderer parse(@NotNull String s, @NotNull ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(@NotNull ParseContext context) {
                        return false;
                    }

                    @Override
                    public @NotNull String toString(ModelRenderer renderer, int flags) {
                        return "model renderer[" + renderer.name() + "]";
                    }

                    @Override
                    public @NotNull String toVariableNameString(ModelRenderer renderer) {
                        return "modelrenderer_" + renderer.name();
                    }
                })
        );
    }
}
