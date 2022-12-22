package be.seeseemelk.mockbukkit.entity.data;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

public enum EntityState
{
	DEFAULT("default"),
	
	BABY("baby"),
	
	SLEEPING("sleeping"),
	
	SNEAKING("sneaking");
	
	private String state;

	private EntityState(@NotNull String state) {
		Preconditions.checkNotNull(state);
		this.state = state;
	}
	
	public @NotNull String getState() {
		return this.state;
	}
}
