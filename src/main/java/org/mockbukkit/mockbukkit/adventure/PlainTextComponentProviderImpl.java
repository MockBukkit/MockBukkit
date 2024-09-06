package org.mockbukkit.mockbukkit.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.TranslationArgument;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.translation.Translator;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlainTextComponentProviderImpl implements PlainTextComponentSerializer.Provider
{

	private static final Pattern LOCALIZATION_PATTERN = Pattern.compile("%(?:(\\d+)\\$)?s");

	private static final ComponentFlattener BASIC_COMPONENT_FLATTENER = ComponentFlattener.basic().toBuilder()
			.complexMapper(TranslatableComponent.class, PlainTextComponentProviderImpl::processTranslatable).build();

	@Override
	public @NotNull PlainTextComponentSerializer plainTextSimple()
	{
		return PlainTextComponentSerializer.builder()
				.flattener(BASIC_COMPONENT_FLATTENER)
				.build();
	}

	@Override
	public @NotNull Consumer<PlainTextComponentSerializer.Builder> plainText()
	{
		return builder -> builder.flattener(BASIC_COMPONENT_FLATTENER);
	}

	private static void processTranslatable(TranslatableComponent translatable, Consumer<Component> consumer)
	{
		try
		{
			// Taken directly from io.papermc.paper.adventure.PaperAdventure

			Language language = Languages.getInstance();
			if (!language.has(translatable.key()))
			{
				for (Translator source : GlobalTranslator.translator().sources())
				{
					if (source instanceof TranslationRegistry registry && registry.contains(translatable.key()))
					{
						consumer.accept(GlobalTranslator.render(translatable, Locale.US));
						return;
					}
				}
			}
			String fallback = translatable.fallback();
			String translated = Languages.getInstance().getOrDefault(translatable.key(), fallback);

			List<TranslationArgument> argumentList = translatable.arguments();
			Matcher matcher = LOCALIZATION_PATTERN.matcher(translated);
			int argPosition = 0;
			int lastIndex = 0;
			while (matcher.find())
			{
				if (lastIndex < matcher.start())
				{
					consumer.accept(Component.text(translated.substring(lastIndex, matcher.start())));
				}
				lastIndex = matcher.end();
				argPosition = dealWithTranslatableArguments(matcher, consumer, argumentList, argPosition);
			}

			if (lastIndex < translated.length())
			{
				consumer.accept(Component.text(translated.substring(lastIndex)));
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}

	private static int dealWithTranslatableArguments(Matcher matcher, Consumer<Component> consumer, List<TranslationArgument> argumentList, int argPosition)
	{
		String argIndex = matcher.group(1);
		if (argIndex != null)
		{
			try
			{
				final int idx = Integer.parseInt(argIndex) - 1;
				if (idx < argumentList.size())
				{
					consumer.accept(argumentList.get(idx).asComponent());
				}
			}
			catch (final NumberFormatException ex)
			{
				// ignore, drop the format placeholder
			}
		}
		else
		{
			final int idx = argPosition++;
			if (idx < argumentList.size())
			{
				consumer.accept(argumentList.get(idx).asComponent());
			}
		}
		return argPosition;
	}

}
