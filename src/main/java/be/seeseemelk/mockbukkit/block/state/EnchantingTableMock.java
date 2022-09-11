package be.seeseemelk.mockbukkit.block.state;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.EnchantingTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnchantingTableMock extends TileStateMock implements EnchantingTable
{

	private Component customName;

	public EnchantingTableMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.ENCHANTING_TABLE);
	}

	protected EnchantingTableMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.ENCHANTING_TABLE);
	}

	protected EnchantingTableMock(@NotNull EnchantingTableMock state)
	{
		super(state);
		this.customName = state.customName;
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new EnchantingTableMock(this);
	}

	@Override
	public @Nullable Component customName()
	{
		return this.customName;
	}

	@Override
	public void customName(@Nullable Component customName)
	{
		this.customName = customName == null ? Component.text("") : customName;
	}

	@Override
	public @Nullable String getCustomName()
	{
		return LegacyComponentSerializer.legacySection().serialize(this.customName);
	}

	@Override
	public void setCustomName(@Nullable String name)
	{
		this.customName = name == null ? Component.text("") : LegacyComponentSerializer.legacySection().deserialize(name);
	}

}
