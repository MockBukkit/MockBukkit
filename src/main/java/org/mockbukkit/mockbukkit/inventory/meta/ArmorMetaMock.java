package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ArmorMetaMock extends ItemMetaMock implements ArmorMeta
{

	private ArmorTrim trim = null;

	public ArmorMetaMock()
	{
		super();
	}

	public ArmorMetaMock(ItemMeta meta)
	{
		super(meta);

		if(meta instanceof ArmorMetaMock armorMeta)
		{
			this.trim = armorMeta.getTrim();
		}
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

	@Override
	public @NotNull Map<String, Object> serialize()
	{
		Map<String, Object> serialized = super.serialize();
		if (hasTrim())
		{
			Map<String, Object> trimData = new HashMap<>();
			trimData.put("material", this.trim.getMaterial().key().toString());
			trimData.put("pattern", this.trim.getPattern().key().toString());
			serialized.put("trim", trimData);
		}
		return serialized;
	}

	@Override
	protected void deserializeInternal(@NotNull Map<String, Object> args)
	{
		super.deserializeInternal(args);
		if (args.containsKey("trim"))
		{
			Map<String, Object> trimData = (Map<String, Object>) args.get("trim");
			TrimMaterial trimMaterial = Registry.TRIM_MATERIAL.get(NamespacedKey.fromString(trimData.get("material").toString()));
			TrimPattern trimPattern = Registry.TRIM_PATTERN.get(NamespacedKey.fromString(trimData.get("pattern").toString()));
			this.setTrim(new ArmorTrim(trimMaterial, trimPattern));
		}
	}

	public static @NotNull ArmorMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		ArmorMetaMock armorMetaMock = new ArmorMetaMock();
		armorMetaMock.deserializeInternal(args);
		return armorMetaMock;
	}

}
