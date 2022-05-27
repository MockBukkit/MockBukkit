package be.seeseemelk.mockbukkit.inventory.meta;

import org.bukkit.entity.Axolotl;
import org.bukkit.inventory.meta.AxolotlBucketMeta;
import org.jetbrains.annotations.NotNull;

public class AxolotlBucketMetaMock extends ItemMetaMock implements AxolotlBucketMeta
{

	private Axolotl.Variant variant;

	public AxolotlBucketMetaMock()
	{
		super();
	}

	public AxolotlBucketMetaMock(AxolotlBucketMeta meta)
	{
		this.variant = meta.getVariant();
	}

	@Override
	public @NotNull Axolotl.Variant getVariant()
	{
		return this.variant;
	}

	@Override
	public void setVariant(@NotNull Axolotl.Variant variant)
	{
		if (variant == null)
		{
			variant = Axolotl.Variant.LUCY;
		}
		this.variant = variant;
	}

	@Override
	public boolean hasVariant()
	{
		return this.variant != null;
	}

	@Override
	public @NotNull AxolotlBucketMetaMock clone()
	{
		AxolotlBucketMetaMock clone = (AxolotlBucketMetaMock) super.clone();

		clone.variant = this.variant;

		return clone;
	}

}
