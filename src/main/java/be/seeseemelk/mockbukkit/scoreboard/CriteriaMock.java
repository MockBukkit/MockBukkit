package be.seeseemelk.mockbukkit.scoreboard;

import com.google.common.base.Preconditions;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.RenderType;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link Criteria}.
 */
public class CriteriaMock implements Criteria
{

	private final String name;
	private final boolean readOnly;
	private final RenderType defaultRenderType;

	/**
	 * Constructs a new {@link CriteriaMock} with the provided name.
	 *
	 * @param name The name of the criteria.
	 */
	public CriteriaMock(@NotNull String name)
	{
		Preconditions.checkNotNull(name, "Name cannot be null");
		this.name = name;
		this.readOnly = switch (name)
		{
		case "health", "food", "air", "armor", "xp", "level" -> true;
		default -> false;
		};
		this.defaultRenderType = "health".equals(name) ? RenderType.HEARTS : RenderType.INTEGER;
	}

	@Override
	public @NotNull String getName()
	{
		return this.name;
	}

	@Override
	public boolean isReadOnly()
	{
		return this.readOnly;
	}

	@Override
	public @NotNull RenderType getDefaultRenderType()
	{
		return this.defaultRenderType;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CriteriaMock that = (CriteriaMock) o;
		return this.name.equals(that.name);
	}

	@Override
	public int hashCode()
	{
		return this.name.hashCode();
	}

}
