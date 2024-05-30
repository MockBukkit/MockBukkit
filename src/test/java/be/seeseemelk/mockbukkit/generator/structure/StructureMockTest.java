package be.seeseemelk.mockbukkit.generator.structure;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class StructureMockTest
{

	private StructureMock structure;
	private StructureTypeMock structureType;
	private static final String NAME_SPACE = "mock_bukkit";
	private NamespacedKey key;

	@BeforeEach
	void setUp()
	{
		this.structureType = new StructureTypeMock(new NamespacedKey(NAME_SPACE, "custom_structure_type"));
		this.key = new NamespacedKey(NAME_SPACE, "custom_structure");
		this.structure = new StructureMock(key, structureType);
	}

	@Test
	void from_invalid()
	{
		// technically already tests valid keys for this
		JsonObject invalid = new JsonObject();
		invalid.add("invalidKey", new JsonPrimitive("key"));
		assertThrows(IllegalArgumentException.class, () -> StructureMock.from(invalid));
	}

	@Test
	void getStructureType()
	{
		assertEquals(structureType, structure.getStructureType());
	}

	@Test
	void getKey()
	{
		assertEquals(key, structure.getKey());
	}

}
