package io.papermc.paper.datacomponent.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import net.kyori.adventure.text.object.PlayerHeadObjectContents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.profile.PlayerProfileMock;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record ResolvableProfileMock(@Nullable UUID uuid, @Nullable String name,
									@Unmodifiable Collection<ProfileProperty> properties) implements ResolvableProfile
{

	@Override
	public boolean dynamic()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public CompletableFuture<PlayerProfile> resolve()
	{
		PlayerProfileMock playerProfileMock = new PlayerProfileMock(name, uuid);
		playerProfileMock.setProperties(properties);
		return CompletableFuture.completedFuture(playerProfileMock);
	}

	@Override
	public SkinPatch skinPatch()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void applySkinToPlayerHeadContents(PlayerHeadObjectContents.@NotNull Builder builder)
	{
		throw new UnimplementedOperationException();
	}

	static class BuilderMock implements Builder
	{
		private final Set<ProfileProperty> properties = new HashSet<>();

		private @Nullable String name;
		private @Nullable UUID uuid;

		@Override
		public Builder name(@Nullable String name)
		{
			if (name != null)
			{
				Preconditions.checkArgument(name.length() <= 16, "name cannot be more than 16 characters, was %s", name.length());
				Preconditions.checkArgument(isValidPlayerName(name), "name cannot include invalid characters, was %s", name);
			}
			this.name = name;
			return this;
		}

		@Override
		public Builder uuid(@Nullable UUID uuid)
		{
			this.uuid = uuid;
			return this;
		}

		@Override
		public Builder addProperty(ProfileProperty property)
		{
			Preconditions.checkNotNull(property);
			if (!properties.contains(property))
			{
				int newSize = this.properties.size() + 1;
				Preconditions.checkArgument(newSize <= 16, "Cannot have more than 16 properties, was %s", newSize);
			}
			properties.add(property);
			return this;
		}

		@Override
		public Builder addProperties(Collection<ProfileProperty> properties)
		{
			Preconditions.checkNotNull(properties);
			properties.forEach(this::addProperty);
			return this;
		}

		@Override
		public Builder skinPatch(SkinPatch patch)
		{
			throw new UnimplementedOperationException();
		}

		@Override
		public Builder skinPatch(Consumer<SkinPatchBuilder> configure)
		{
			throw new UnimplementedOperationException();
		}

		@Override
		public ResolvableProfile build()
		{
			return new ResolvableProfileMock(uuid, name, new ImmutableSet.Builder<ProfileProperty>().addAll(properties).build());
		}


		private static boolean isValidPlayerName(String playerName)
		{
			return playerName.chars().filter(i -> i <= 32 || i >= 127).findAny().isEmpty();
		}

	}

}
