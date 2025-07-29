package org.mockbukkit.mockbukkit.inventory.meta.components;

import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@ApiStatus.Experimental
public class CustomModelDataComponentMock implements CustomModelDataComponent
{

	private @NotNull List<Float> floats;
	private @NotNull List<Boolean> flags;
	private @NotNull List<String> strings;
	private @NotNull List<Color> colors;

	/**
	 * Creates a new instance of this class, using the provided lists to create
	 * immutable lists for each field
	 *
	 * @param floats  the floats
	 * @param flags   the flags
	 * @param strings the strings
	 * @param colors  the colors
	 */
	public CustomModelDataComponentMock(
			@NotNull List<Float> floats,
			@NotNull List<Boolean> flags,
			@NotNull List<String> strings,
			@NotNull List<Color> colors
	)
	{
		this.floats = List.copyOf(floats);
		this.flags = List.copyOf(flags);
		this.strings = List.copyOf(strings);
		this.colors = List.copyOf(colors);
	}

	/**
	 * A deep copy constructor.<br>
	 * See {@link ItemMeta#getCustomModelDataComponent()} for why this is needed.
	 *
	 * @param component the component to copy
	 */
	public CustomModelDataComponentMock(CustomModelDataComponent component)
	{
		this(
				component.getFloats(),
				component.getFlags(),
				component.getStrings(),
				component.getColors()
		);
	}

	/**
	 * Creates a new instance of this class with empty lists for each field
	 */
	public CustomModelDataComponentMock()
	{
		this(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
	}

	/**
	 * Required by the {@link org.bukkit.configuration.serialization.ConfigurationSerializable} interface.
	 * Creates an instance of this class, deserialized from the given serializable class
	 *
	 * @param serialized the serialized version of this class (see {@link #serialize()})
	 */
	@SuppressWarnings("unchecked")
	public CustomModelDataComponentMock(@NotNull Map<String, Object> serialized)
	{
		this(
				(List<Float>) serialized.get("floats"),
				(List<Boolean>) serialized.get("flags"),
				(List<String>) serialized.get("strings"),
				(List<Color>) serialized.get("colors")
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@ApiStatus.Experimental
	@Override
	public @NotNull List<Float> getFloats()
	{
		return floats;
	}

	/**
	 * {@inheritDoc}
	 */
	@ApiStatus.Experimental
	@Override
	public @NotNull List<Boolean> getFlags()
	{
		return flags;
	}

	/**
	 * {@inheritDoc}
	 */
	@ApiStatus.Experimental
	@Override
	public @NotNull List<String> getStrings()
	{
		return strings;
	}

	/**
	 * {@inheritDoc}
	 */
	@ApiStatus.Experimental
	@Override
	public @NotNull List<Color> getColors()
	{
		return colors;
	}

	/**
	 * {@inheritDoc}
	 */
	@ApiStatus.Experimental
	@Override
	public void setFloats(@NotNull List<Float> floats)
	{
		this.floats = List.copyOf(floats);
	}

	/**
	 * {@inheritDoc}
	 */
	@ApiStatus.Experimental
	@Override
	public void setFlags(@NotNull List<Boolean> flags)
	{
		this.flags = List.copyOf(flags);
	}

	/**
	 * {@inheritDoc}
	 */
	@ApiStatus.Experimental
	@Override
	public void setStrings(@NotNull List<String> strings)
	{
		this.strings = List.copyOf(strings);
	}

	/**
	 * {@inheritDoc}
	 */
	@ApiStatus.Experimental
	@Override
	public void setColors(@NotNull List<Color> colors)
	{
		this.colors = List.copyOf(colors);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		return Map.of(
				"floats", getFloats(),
				"flags", getFlags(),
				"strings", getStrings(),
				"colors", getColors()
		);
	}

	/**
	 * Required by the {@link org.bukkit.configuration.serialization.ConfigurationSerializable} interface
	 *
	 * @param serialized the serialized version of this class (see {@link #serialize()})
	 * @return an instance of this class, deserialized from the given serializable class
	 */
	public static @NotNull CustomModelDataComponent valueOf(@NotNull Map<String, Object> serialized)
	{
		return new CustomModelDataComponentMock(serialized);
	}

	/**
	 * Required by the {@link org.bukkit.configuration.serialization.ConfigurationSerializable} interface
	 *
	 * @param serialized the serialized version of this class (see {@link #serialize()})
	 * @return an instance of this class, deserialized from the given serializable class
	 */
	public static @NotNull CustomModelDataComponent deserialize(@NotNull Map<String, Object> serialized)
	{
		return new CustomModelDataComponentMock(serialized);
	}

}
