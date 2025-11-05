package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.data.type.NoteBlock;
import org.jetbrains.annotations.NotNull;

public class NoteBlockDataMock extends BlockDataMock implements NoteBlock
{

	public NoteBlockDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected NoteBlockDataMock(@NotNull NoteBlockDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull Instrument getInstrument()
	{
		return this.get(BlockDataKey.INSTRUMENT);
	}

	@Override
	public void setInstrument(@NotNull Instrument instrument)
	{
		Preconditions.checkArgument(instrument != null, "instrument cannot be null!");
		this.set(BlockDataKey.INSTRUMENT, instrument);
	}

	@Override
	public @NotNull Note getNote()
	{
		return new Note(this.get(BlockDataKey.NOTE));
	}

	@Override
	public void setNote(@NotNull Note note)
	{
		Preconditions.checkArgument(note != null, "note cannot be null!");
		this.set(BlockDataKey.NOTE, (int) note.getId());
	}

	@Override
	public boolean isPowered()
	{
		return this.get(BlockDataKey.POWERED);
	}

	@Override
	public void setPowered(boolean powered)
	{
		this.set(BlockDataKey.POWERED, powered);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull NoteBlockDataMock clone()
	{
		return new NoteBlockDataMock(this);
	}

}
