package be.seeseemelk.mockbukkit.entity.data;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;

class EntityDataTest
{
	
	String fakeData;
	private EntityData entityData;
	
	@BeforeEach
	void setUp() throws IOException {
		fakeData = "{'default':{'width':2,'height':3},'baby':{'width':1}}";
		entityData = new EntityData(EntityType.BAT, fakeData);
	}
	
	@AfterEach
	void tearDown() {
		MockBukkit.unmock();
	}
	

	@Test
	void defaultValueTest() {
		assertEquals(2,entityData.getWidth(EntityState.DEFAULT));
		assertEquals(3,entityData.getHeight(EntityState.DEFAULT));
	}
	
	@Test
	void nonDefaultValueTest() {
		assertEquals(1,entityData.getWidth(EntityState.BABY));
		assertEquals(3,entityData.getHeight(EntityState.BABY));
	}

	@Test
	void invalidKeyTest() {
		assertThrows(UnimplementedOperationException.class,() -> entityData.getValueFromKey("invalid",EntityState.DEFAULT));
	}
	
	@Test
	void invalidStateTest() {
		assertThrows(UnimplementedOperationException.class,() -> entityData.getWidth(EntityState.SNEAKING));
	}

}
