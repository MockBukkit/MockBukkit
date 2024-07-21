package be.seeseemelk.mockbukkit.entity.variant;

import be.seeseemelk.mockbukkit.util.OldEnumMock;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;

public class VillagerProfessionMock extends OldEnumMock<Villager.Profession> implements Villager.Profession
{

	private final NamespacedKey key;
	private final int ordinal;
	private final String name;

	private VillagerProfessionMock(NamespacedKey key, String name, int ordinal)
	{
		this.key = key;
		this.ordinal = ordinal;
		this.name = name;
	}

	public static VillagerProfessionMock from(JsonObject jsonObject)
	{
		String name = jsonObject.get("name").getAsString();
		NamespacedKey key = NamespacedKey.fromString(jsonObject.get("key").getAsString());
		int ordinal = jsonObject.get("ordinal").getAsInt();
		return new VillagerProfessionMock(key, name, ordinal);
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return key;
	}

	@Override
	public @NotNull String name()
	{
		return name;
	}

	@Override
	public int ordinal()
	{
		return ordinal;
	}

}
