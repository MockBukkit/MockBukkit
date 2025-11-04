package io.papermc.paper.datacomponent.item;

import org.bukkit.block.BlockType;
import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record BlockItemDataPropertiesMock() implements BlockItemDataProperties
{

	@Override
	public BlockData createBlockData(BlockType blockType)
	{
		return blockType.createBlockData();
	}

	@Override
	public BlockData applyTo(BlockData blockData)
	{
		return blockData.clone();
	}

	static class BuilderMock implements BlockItemDataProperties.Builder
	{

		@Override
		public BlockItemDataProperties build()
		{
			return new BlockItemDataPropertiesMock();
		}

	}

}
