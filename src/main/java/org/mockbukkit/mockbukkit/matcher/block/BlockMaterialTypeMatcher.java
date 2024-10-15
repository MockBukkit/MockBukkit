package org.mockbukkit.mockbukkit.matcher.block;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.block.BlockMock;

public class BlockMaterialTypeMatcher extends TypeSafeMatcher<BlockMock>
{

	private final Material material;

	private BlockMaterialTypeMatcher(Material material)
	{
		this.material = material;
	}

	@Override
	protected boolean matchesSafely(BlockMock blockMock)
	{
		return blockMock.getType().equals(material);
	}

	@Override
	public void describeTo(Description description)
	{
		description.appendText(String.format("to be block of material '%s'", material));
	}

	@Override
	protected void describeMismatchSafely(BlockMock item, Description mismatchDescription)
	{
		mismatchDescription.appendValue("was of material type").appendValue(item.getType());
	}

	/**
	 * @param material The material that the block should have
	 * @return A matcher which matches blocks with the specified material
	 */
	public static @NotNull BlockMaterialTypeMatcher hasMaterial(@NotNull Material material)
	{
		Preconditions.checkNotNull(material);
		return new BlockMaterialTypeMatcher(material);
	}

}
