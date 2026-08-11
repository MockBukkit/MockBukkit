package io.papermc.paper.datacomponent.item;

import io.papermc.paper.text.Filtered;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class WrittenBookContentMockTest
{

	@Test
	void constructor_longTitle_throws()
	{
		String longTitle = "t".repeat(40);
		assertThrows(IllegalArgumentException.class, () -> new WrittenBookContentMock.BuilderMock(Filtered.of(longTitle, null), "author"));
	}

	@Test
	void generation_outOfRange_throws()
	{
		var builder = new WrittenBookContentMock.BuilderMock(Filtered.of("title", null), "author");
		assertThrows(IllegalArgumentException.class, () -> builder.generation(5));
	}

	@Test
	void addPage_serializedTooLong_throws()
	{
		var builder = new WrittenBookContentMock.BuilderMock(Filtered.of("title", null), "author");
		String longText = "z".repeat(33000);
		Component large = Component.text(longText);
		assertThrows(IllegalArgumentException.class, () -> builder.addPage(large));
	}

}
