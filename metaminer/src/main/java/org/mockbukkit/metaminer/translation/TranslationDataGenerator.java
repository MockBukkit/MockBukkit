package org.mockbukkit.metaminer.translation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import net.minecraft.locale.Language;
import org.mockbukkit.metaminer.DataGenerator;
import org.mockbukkit.metaminer.util.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class TranslationDataGenerator implements DataGenerator
{

	private final File dataFolder;

	public TranslationDataGenerator(File dataFolder)
	{
		this.dataFolder = new File(dataFolder, "src/main/resources/translations");
	}

	@Override
	public void generateData() throws IOException
	{
		try (InputStream inputStream = Language.class.getResourceAsStream("/assets/minecraft/lang/en_us.json"))
		{
			JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
			JsonObject jsonObject = new JsonObject();
			jsonObject.add("rightToLeft", new JsonPrimitive(false));
			jsonObject.add("translations", jsonElement);
			JsonUtil.dump(jsonObject, new File(dataFolder, "en-us.json"));
		}
	}

}
