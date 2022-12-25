package be.seeseemelk.mockbukkit.entity.data;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

public enum EntityState
{
	DEFAULT("default"),

	SLEEPING("sleeping"),

	SNEAKING("sneaking"),

	SEMI_PUFFED("semi_puffed"),

	PUFFED("puffed"),

	GLIDING("gliding"),

	SWIMMING("swimming"),

	ANGRY("angry"),
	
	PEEKING("peeking"),
	
	OPEN("open");

	private String state;

	private EntityState(@NotNull String state)
	{
		Preconditions.checkNotNull(state);
		this.state = state;
	}

	public @NotNull String getState()
	{
		return this.state;
	}
}
