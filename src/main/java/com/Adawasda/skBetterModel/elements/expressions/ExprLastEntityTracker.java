package com.Adawasda.skBetterModel.elements.expressions;

import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.skriptlang.skript.registration.DefaultSyntaxInfos;
import org.skriptlang.skript.registration.SyntaxRegistry;

import com.Adawasda.skBetterModel.elements.sections.SecCreateEntityTracker;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import kr.toxicity.model.api.tracker.EntityTracker;
public class ExprLastEntityTracker extends SimpleExpression<EntityTracker> {

	public static void register(@NotNull SyntaxRegistry registry) {
		registry.register(SyntaxRegistry.EXPRESSION, DefaultSyntaxInfos.Expression.builder(ExprLastEntityTracker.class, EntityTracker.class)
				.supplier(ExprLastEntityTracker::new)
				.addPatterns("last created [bm|bettermodel] entity tracker") // [example] is optional in syntax
				.build());
	}
    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends EntityTracker> getReturnType() {
        return EntityTracker.class;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "last created entity tracker";
    }

    @Override
    protected EntityTracker @Nullable [] get(Event event) {
        return new EntityTracker[]{SecCreateEntityTracker.lastCreatedEntityTracker};
    }
    
}
