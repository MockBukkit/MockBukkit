package org.mockbukkit.metaminer.tests;

import com.google.gson.JsonArray;
import org.bukkit.inventory.ItemType;
import org.mockbukkit.metaminer.DataGenerator;
import org.mockbukkit.metaminer.util.JsonUtil;

import java.io.File;
import java.io.IOException;

public class ItemStackMetaDataGenerator implements DataGenerator
{

	private final File folder;

	public ItemStackMetaDataGenerator(File folder)
	{
		this.folder = folder;
	}

	@Override
	public void generateData() throws IOException
	{
		JsonArray jsonArray = new JsonArray();
		for (ItemType itemType : ItemMetaClassFinder.getInduvidualMetaItemTypes())
		{
			jsonArray.add(itemType.getKey().asString());
		}
		File file = new File(folder, "metaItemTypes.json");
		JsonUtil.dump(jsonArray, file);
	}

}
