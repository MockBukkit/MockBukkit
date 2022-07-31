package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Frog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class FrogMock extends AnimalsMock implements Frog
{

	private @Nullable Entity tongueTarget = null;
	private Variant variant = Variant.TEMPERATE;

	public FrogMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @Nullable Entity getTongueTarget()
	{
		return this.tongueTarget;
	}

	@Override
	public void setTongueTarget(@Nullable Entity target)
	{
		this.tongueTarget = target;
	}

	@Override
	public @NotNull Variant getVariant()
	{
		return this.variant;
	}

	@Override
	public void setVariant(@NotNull Variant variant)
	{
		Preconditions.checkNotNull(variant, "Variant cannot be null");
		this.variant = variant;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.FROG;
	}

}
