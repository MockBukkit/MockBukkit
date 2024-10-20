package org.mockbukkit.mockbukkit.entity.data;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.IOException;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EntityDataTest
{

	String fakeData;
	private EntityData entityData;

	@BeforeEach
	void setUp() throws IOException
	{
		fakeData = "{'default':{'width':2,'height':3,'eyeHeight':4,'states':{'sleeping':{'height':0.2, 'eyeHeight':0.3}}},'baby':{'width':1, 'height':0.5, 'eyeHeight':0.1}}";
		entityData = new EntityData(EntityType.BAT, fakeData);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void defaultValueTest()
	{
		try
		{
			assertEquals(2, entityData.getWidth(EntitySubType.DEFAULT, EntityState.DEFAULT));
			assertEquals(3, entityData.getHeight(EntitySubType.DEFAULT, EntityState.DEFAULT));
			assertEquals(4, entityData.getEyeHeight(EntitySubType.DEFAULT, EntityState.DEFAULT));
		}
		catch (UnimplementedOperationException e)
		{
			throw new AssertionFailedError("");
		}
	}

	@Test
	void subTypeValueTest()
	{
		try
		{
			assertEquals(1, entityData.getWidth(EntitySubType.BABY, EntityState.DEFAULT));
			assertEquals(0.5, entityData.getHeight(EntitySubType.BABY, EntityState.DEFAULT));
			assertEquals(0.1, entityData.getEyeHeight(EntitySubType.BABY, EntityState.DEFAULT));
		}
		catch (UnimplementedOperationException e)
		{
			throw new AssertionFailedError("");
		}
	}

	@Test
	void stateValueTest()
	{
		try
		{
			assertEquals(2, entityData.getWidth(EntitySubType.DEFAULT, EntityState.SLEEPING));
			assertEquals(0.2, entityData.getHeight(EntitySubType.DEFAULT, EntityState.SLEEPING));
			assertEquals(0.3, entityData.getEyeHeight(EntitySubType.DEFAULT, EntityState.SLEEPING));
		}
		catch (UnimplementedOperationException e)
		{
			throw new AssertionFailedError("");
		}
	}

	@Test
	void invalidKeyTest()
	{
		assertThrows(UnimplementedOperationException.class,
				() -> entityData.getValueFromKey("invalid", EntitySubType.DEFAULT, EntityState.DEFAULT));
	}

	@Test
	void invalidStateTest()
	{
		assertThrows(UnimplementedOperationException.class, () -> entityData.getWidth(EntitySubType.DEFAULT, EntityState.SNEAKING));
	}

}
