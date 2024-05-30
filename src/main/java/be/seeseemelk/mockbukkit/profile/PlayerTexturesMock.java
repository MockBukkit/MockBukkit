package be.seeseemelk.mockbukkit.profile;

import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.gson.JsonObject;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Mock implementation of a {@link PlayerTextures}.
 */
public class PlayerTexturesMock implements PlayerTextures
{

	private static final String PROPERTY_NAME = "textures";
	private final PlayerProfileMock profile;

	private long timestamp;

	// Lazily decoded textures data that can subsequently be overwritten:
	private URL skin;
	private SkinModel skinModel = SkinModel.CLASSIC;
	private URL cape;

	// Dirty: Indicates a change that requires a rebuild of the property.
	// This also indicates an invalidation of any previously present textures data
	// that is specific to official
	// GameProfiles, such as the property signature, timestamp, profileId,
	// playerName, etc.: Any modifications by
	// plugins that affect the textures property immediately invalidate all
	// attributes that are specific to official
	// GameProfiles (even if these modifications are later reverted).
	private boolean dirty = false;

	private void markDirty()
	{
		dirty = true;
		timestamp = 0L;
	}

	public PlayerTexturesMock(@Nonnull PlayerProfileMock profile)
	{
		this.profile = profile;
	}

	@Override
	public boolean isEmpty()
	{
		return skin == null && cape == null;
	}

	@Override
	public void clear()
	{
		profile.removeProperty(PROPERTY_NAME);
		timestamp = 0L;
		skin = null;
		skinModel = SkinModel.CLASSIC;
		cape = null;
		dirty = false;
	}

	@Override
	public @Nullable URL getSkin()
	{
		return this.skin;
	}

	@Override
	public void setSkin(@Nullable URL skinUrl)
	{
		setSkin(skinUrl, SkinModel.CLASSIC);
	}

	@Override
	public void setSkin(@Nullable URL skinUrl, @Nullable PlayerTextures.SkinModel skinModel)
	{
		this.skin = skinUrl;
		this.skinModel = (skinUrl != null) ? skinModel : SkinModel.CLASSIC;
		this.dirty = true;

		markDirty();
		setProperty();
	}

	@NotNull
	@Override
	public SkinModel getSkinModel()
	{
		return this.skinModel;
	}

	@Override
	public @Nullable URL getCape()
	{
		return this.cape;
	}

	@Override
	public void setCape(@Nullable URL capeUrl)
	{
		this.cape = capeUrl;
		this.dirty = true;

		markDirty();
		setProperty();
	}

	@Override
	public long getTimestamp()
	{
		return this.timestamp;
	}

	@Override
	public boolean isSigned()
	{
		if (dirty)
			return false;
		ProfileProperty property = getProperty();
		return property != null;
	}

	@Nullable
	ProfileProperty getProperty()
	{
		return profile.getProperty(PROPERTY_NAME);
	}

	@Override
	public int hashCode()
	{
		ProfileProperty property = getProperty();
		return (property == null) ? 0 : property.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof PlayerTextures))
			return false;

		PlayerTexturesMock other = (PlayerTexturesMock) obj;
		ProfileProperty property = getProperty();
		ProfileProperty otherProperty = other.getProperty();

		if (property == null && otherProperty == null)
		{
			return true;
		}
		else if (property == null || otherProperty == null)
		{
			return false;
		}

		return property.equals(otherProperty);
	}

	private void setProperty()
	{
		this.timestamp = System.currentTimeMillis();

		JsonObject json = new JsonObject();
		json.addProperty("timestamp", this.timestamp);
		json.addProperty("profileId", profile.getUniqueId().toString().replace("-", ""));
		json.addProperty("profileName", profile.getName());

		JsonObject textures = new JsonObject();
		if (skin != null)
		{
			textures.addProperty("SKIN", skin.toString());
			textures.addProperty("SKIN_MODEL", skinModel.name());
		}
		if (cape != null)
		{
			textures.addProperty("CAPE", cape.toString());
		}
		json.add(PROPERTY_NAME, textures);

		String base64Encoded = Base64.getEncoder().encodeToString(json.toString().getBytes(StandardCharsets.UTF_8));

		ProfileProperty property = new ProfileProperty(PROPERTY_NAME, base64Encoded);
		this.profile.setProperty(property);
	}

}
