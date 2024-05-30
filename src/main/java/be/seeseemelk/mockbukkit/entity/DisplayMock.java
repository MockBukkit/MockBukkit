package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.Color;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.UUID;

public class DisplayMock extends EntityMock implements Display
{

	private Matrix4f transformationMatrix;
	private int interpolationDuration = 0;
	private int teleportDuration = 0;
	private float viewRange = 1.0f;
	private float shadowRadius = 0.0f;
	private float shadowStrength = 1.0f;
	private float width = 0.0f;
	private float height = 0.0f;
	private int interpolationDelay = 0;
	private int color = -1;
	private Brightness brightness = null;

	/**
	 * Constructs a new EntityMock on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected DisplayMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
		this.transformationMatrix = new Matrix4f();
		this.transformationMatrix.scale(new Vector3f(1.0f, 1.0f, 1.0f));
	}

	@Override
	public @NotNull Transformation getTransformation()
	{
		float f = 1.0F / this.transformationMatrix.m33();
		Vector3f translation = this.transformationMatrix.getTranslation(new Vector3f()).mul(f);
		Quaternionf leftRotation = this.transformationMatrix.getUnnormalizedRotation(new Quaternionf());
		Vector3f scale = transformationMatrix.getScale(new Vector3f());
		Quaternionf rightRotation = leftRotation.conjugate();
		return new Transformation(translation, leftRotation, scale, rightRotation);
	}

	@Override
	public void setTransformation(@NotNull Transformation transformation)
	{
		Preconditions.checkArgument(transformation != null, "Transformation cannot be null");
		Matrix4f matrix4f = new Matrix4f();
		matrix4f.translation(transformation.getTranslation());
		matrix4f.rotate(transformation.getLeftRotation());
		matrix4f.scale(transformation.getScale());
		matrix4f.rotate(transformation.getRightRotation());
		this.transformationMatrix = matrix4f;
	}

	@Override
	public void setTransformationMatrix(@NotNull Matrix4f transformationMatrix)
	{
		Preconditions.checkArgument(transformationMatrix != null, "Transformation matrix cannot be null");
		try
		{
			this.transformationMatrix = (Matrix4f) transformationMatrix.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new IllegalStateException("Could not clone transformation matrix");
		}
	}

	@Override
	public int getInterpolationDuration()
	{
		return this.interpolationDuration;
	}

	@Override
	public void setInterpolationDuration(int duration)
	{
		this.interpolationDuration = duration;
	}

	@Override
	public int getTeleportDuration()
	{
		return this.teleportDuration;
	}

	@Override
	public void setTeleportDuration(int duration)
	{
		Preconditions.checkArgument(duration >= 0 && duration <= 59,
				"duration (%s) cannot be lower than 0 or higher than 59", duration);
		this.teleportDuration = duration;
	}

	@Override
	public float getViewRange()
	{
		return this.viewRange;
	}

	@Override
	public void setViewRange(float range)
	{
		this.viewRange = range;
	}

	@Override
	public float getShadowRadius()
	{
		return this.shadowRadius;
	}

	@Override
	public void setShadowRadius(float radius)
	{
		this.shadowRadius = radius;
	}

	@Override
	public float getShadowStrength()
	{
		return this.shadowStrength;
	}

	@Override
	public void setShadowStrength(float strength)
	{
		this.shadowStrength = strength;
	}

	@Override
	public float getDisplayWidth()
	{
		return this.width;
	}

	@Override
	public void setDisplayWidth(float width)
	{
		this.width = width;
	}

	@Override
	public float getDisplayHeight()
	{
		return this.height;
	}

	@Override
	public void setDisplayHeight(float height)
	{
		this.height = height;
	}

	@Override
	public int getInterpolationDelay()
	{
		return this.interpolationDelay;
	}

	@Override
	public void setInterpolationDelay(int ticks)
	{
		this.interpolationDelay = ticks;
	}

	@Override
	public @NotNull Billboard getBillboard()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBillboard(@NotNull Billboard billboard)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Color getGlowColorOverride()
	{
		return (this.color == -1) ? null : Color.fromARGB(color);
	}

	@Override
	public void setGlowColorOverride(@Nullable Color color)
	{
		if (color == null)
		{
			this.color = -1;
		}
		else
		{
			this.color = color.asARGB();
		}
	}

	@Override
	public @Nullable Brightness getBrightness()
	{
		return cloneBrightness(this.brightness);
	}

	@Override
	public void setBrightness(@Nullable Brightness brightness)
	{
		this.brightness = cloneBrightness(brightness);
	}

	private @Nullable Brightness cloneBrightness(@Nullable Brightness inputBrightness)
	{
		if (inputBrightness == null)
		{
			return null;
		}
		return new Brightness(inputBrightness.getBlockLight(), inputBrightness.getSkyLight());
	}

}
