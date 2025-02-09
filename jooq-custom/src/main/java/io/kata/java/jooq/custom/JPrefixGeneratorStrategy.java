package io.kata.java.jooq.custom;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.codegen.GeneratorStrategy;
import org.jooq.meta.Definition;

public class JPrefixGeneratorStrategy extends DefaultGeneratorStrategy {

	@Override
	public String getJavaClassName(final Definition definition, final GeneratorStrategy.Mode mode) {
		if (mode == Mode.DEFAULT) {
			return "J" + super.getJavaClassName(definition, mode);
		}
		return super.getJavaClassName(definition, mode);
	}
}