package be.seeseemelk.mockbukkit.profile;

import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.net.URL;

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
	// This also indicates an invalidation of any previously present textures data that is specific to official
	// GameProfiles, such as the property signature, timestamp, profileId, playerName, etc.: Any modifications by
	// plugins that affect the textures property immediately invalidate all attributes that are specific to official
	// GameProfiles (even if these modifications are later reverted).
	private boolean dirty = false;

	PlayerTexturesMock(@Nonnull PlayerProfileMock profile) {
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
	}

	@Override
	public long getTimestamp()
	{
		return this.timestamp;
	}

	@Override
	public boolean isSigned()
	{
		if (dirty) return false;
		ProfileProperty property = getProperty();
		return property != null;
	}

	@Nullable
	ProfileProperty getProperty() {
		return profile.getProperty(PROPERTY_NAME);
	}

}
