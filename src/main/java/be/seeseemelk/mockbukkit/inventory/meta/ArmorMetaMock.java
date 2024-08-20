package be.seeseemelk.mockbukkit.inventory.meta;

import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArmorMetaMock extends ItemMetaMock implements ArmorMeta
{

	private ArmorTrim trim = null;

	public ArmorMetaMock()
	{
		super();
	}

	@Override
	public boolean hasTrim()
	{
		return trim != null;
	}

	@Override
	public void setTrim(@Nullable ArmorTrim trim)
	{
		this.trim = trim;
	}

	@Override
	public @Nullable ArmorTrim getTrim()
	{
		return this.trim;
	}

	@Override
	public @NotNull ArmorMetaMock clone()
	{
		ArmorMetaMock armorMetaMock = (ArmorMetaMock) super.clone();
		armorMetaMock.setTrim(this.trim);
		return armorMetaMock;
	}

}
