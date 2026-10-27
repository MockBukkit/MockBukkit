package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.MusicInstrument;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MusicInstrumentMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.inventory.SerializableMeta;

import java.util.Map;
import java.util.Objects;

/**
 * Mock implementation of an {@link MusicInstrumentMeta}.
 *
 * @see ItemMetaMock
 */
@DelegateDeserialization(SerializableMeta.class)
public class MusicInstrumentMetaMock extends ItemMetaMock implements MusicInstrumentMeta
{

	private @Nullable MusicInstrument musicInstrument;

	/**
	 * Constructs a new {@link MusicInstrumentMetaMock}.
	 */
	public MusicInstrumentMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link MusicInstrumentMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public MusicInstrumentMetaMock(ItemMeta meta)
	{
		super(meta);

		if (meta instanceof MusicInstrumentMetaMock musicInstrumentMeta)
		{
			this.musicInstrument = musicInstrumentMeta.getInstrument();
		}
	}

	@Override
	public void setInstrument(@Nullable MusicInstrument musicInstrument)
	{
		this.musicInstrument = musicInstrument;
	}

	@Override
	public @Nullable MusicInstrument getInstrument()
	{
		return musicInstrument;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof MusicInstrumentMetaMock meta))
		{
			return false;
		}
		return super.equals(obj) && Objects.equals(this.musicInstrument, meta.getInstrument());
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), musicInstrument);
	}

	@Override
	@SuppressWarnings({ "MethodDoesntCallSuperMethod", "java:S2975", "java:S1182" })
	public @NotNull MusicInstrumentMetaMock clone()
	{
		return new MusicInstrumentMetaMock(this);
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized MusicInstrumentMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the MusicInstrumentMetaMock class.
	 */
	public static @NotNull MusicInstrumentMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		MusicInstrumentMetaMock serialMock = new MusicInstrumentMetaMock();
		serialMock.deserializeInternal(args);
		serialMock.musicInstrument = (MusicInstrument) args.get("music-instrument");
		return serialMock;
	}

	/**
	 * Serializes the properties of an MusicInstrumentMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the MusicInstrumentMetaMock.
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		final Map<String, Object> serialized = super.serialize();
		if (this.musicInstrument != null)
		{
			serialized.put("music-instrument", this.musicInstrument);
		}

		return serialized;
	}

	@Override
	protected String getTypeName()
	{
		return "MUSIC_INSTRUMENT";
	}

}
