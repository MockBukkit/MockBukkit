package io.papermc.paper.entity.poi;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class PoiTypeMockTest
{

    private static final String BLAST_FURNACE_DATA = "minecraft:blast_furnace[facing=north,lit=true]";

	@Test
	void fromDeserializesMatchingStates()
	{
		var json = new JsonObject();
		json.addProperty("key", "minecraft:armorer");
		json.addProperty("occupants", true);
		json.add("matchingStates", JsonParser.parseString("[\"" + BLAST_FURNACE_DATA + "\"]").getAsJsonArray());

		var poiType = PoiTypeMock.from(json);

		assertEquals(NamespacedKey.minecraft("armorer"), poiType.getKey());
		assertTrue(poiType.hasOccupants());
		assertTrue(poiType.is(Bukkit.createBlockData(BLAST_FURNACE_DATA)));
	}

	@Test
	void isRejectsDifferentBlockState()
	{
		var json = new JsonObject();
		json.addProperty("key", "minecraft:armorer");
		json.addProperty("occupants", true);
		json.add("matchingStates", JsonParser.parseString("[\"" + BLAST_FURNACE_DATA + "\"]").getAsJsonArray());

		var poiType = PoiTypeMock.from(json);

		assertFalse(poiType.is(Bukkit.createBlockData("minecraft:blast_furnace[facing=north,lit=false]")));
		assertFalse(poiType.is(Bukkit.createBlockData("minecraft:barrel[facing=north,open=false]")));
	}

	@Test
	void fromRejectsMissingJsonProperties()
	{
		var emptyObject = new JsonObject();
		assertThrows(NullPointerException.class, () -> PoiTypeMock.from(emptyObject));
	}
}
