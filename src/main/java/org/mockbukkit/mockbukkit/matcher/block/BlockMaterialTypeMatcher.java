package org.mockbukkit.mockbukkit.matcher.block;

import org.bukkit.Material;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.command.CommandResult;

public class BlockMaterialTypeMatcher extends TypeSafeMatcher<BlockMock>
{

	private final Material material;

	public BlockMaterialTypeMatcher(Material material)
	{
		this.material = material;
	}

	@Override
	protected boolean matchesSafely(BlockMock item)
	{
		return item.getType().equals(material);
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

	public static BlockMaterialTypeMatcher hasMaterial(Material material)
	{
		return new BlockMaterialTypeMatcher(material);
	}

}
