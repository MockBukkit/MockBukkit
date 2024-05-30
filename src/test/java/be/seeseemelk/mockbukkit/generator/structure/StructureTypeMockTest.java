package be.seeseemelk.mockbukkit.generator.structure;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockBukkitExtension.class)
class StructureTypeMockTest
{

	private NamespacedKey key;
	private StructureTypeMock structureType;

	@BeforeEach
	void setUp()
	{
		this.key = new NamespacedKey("mock_bukkit", "custom_structure_type");
		this.structureType = new StructureTypeMock(key);
	}

	@Test
	void getKey()
	{
		assertEquals(key, structureType.getKey());
	}

	@Test
	void from()
	{
		JsonObject invalid = new JsonObject();
		assertThrows(IllegalArgumentException.class, () -> StructureTypeMock.from(invalid));
	}

}
