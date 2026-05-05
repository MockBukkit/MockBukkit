package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ZombieNautilus;
import org.jspecify.annotations.NullMarked;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

@NullMarked
public class ZombieNautilusMock extends AbstractNautilusMock implements ZombieNautilus
{
	private Variant variant = Variant.TEMPERATE;

	/**
	 * Constructs a new {@link ZombieNautilus} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ZombieNautilusMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public Variant getVariant()
	{
		return this.variant;
	}

	@Override
	public void setVariant(Variant variant)
	{
		Preconditions.checkArgument(variant != null, "variant cannot be null");
		this.variant = variant;
	}

	@Override
	public EntityType getType()
	{
		return EntityType.ZOMBIE_NAUTILUS;
	}

	public static class VariantMock implements Variant
	{

		private final NamespacedKey key;

		public VariantMock(NamespacedKey key)
		{
			this.key = Preconditions.checkNotNull(key, "The 'key' can't be null.");
		}

		@Override
		public NamespacedKey getKey()
		{
			return this.key;
		}

		public static ZombieNautilusMock.VariantMock from(JsonObject json)
		{
			Preconditions.checkNotNull(json, "The 'json' can't be null");

			var keyProperty = json.get("key");
			Preconditions.checkArgument(keyProperty != null, "The property 'key' does not exist.");
			var keyValue = keyProperty.getAsString();
			Preconditions.checkArgument(keyValue != null && !keyValue.isBlank(), "The property 'key' is empty.");
			var key = NamespacedKey.fromString(keyValue);
			Preconditions.checkArgument(key != null, "The property 'key' has an invalid format");

			return new ZombieNautilusMock.VariantMock(key);
		}

	}

}
