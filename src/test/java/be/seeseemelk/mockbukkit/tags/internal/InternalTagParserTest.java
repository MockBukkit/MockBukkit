package be.seeseemelk.mockbukkit.tags.internal;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.tags.TagsMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class InternalTagParserTest
{
	@Test
	void insertInternalTagValues_registries()
	{
		TagsMock.loadDefaultTags(new ServerMock(),true);
		InternalTag.loadRegistries();
		assertFalse(InternalTag.SOLID_BLOCKS.getValues().isEmpty());
		assertFalse(InternalTag.NON_SOLID_BLOCKS.getValues().isEmpty());
	}

}
