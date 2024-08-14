package org.mockbukkit.metaminer.internal.tags;

import org.mockbukkit.metaminer.DataGenerator;
import org.mockbukkit.metaminer.internal.tags.blocks.InternalBlockTagDataGenerator;

import java.io.File;
import java.util.function.Function;
import java.util.function.Supplier;

public enum InternalTagType
{
	BLOCKS(InternalBlockTagDataGenerator::new);

	private final Function<File, ? extends DataGenerator> constructor;

	InternalTagType(Function<File, ? extends DataGenerator> constructor)
	{
		this.constructor = constructor;
	}

	public Function<File, ? extends DataGenerator> getConstructor()
	{
		return this.constructor;
	}
}
