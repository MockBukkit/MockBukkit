package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.KnowledgeBookMeta;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Mock implementation of an {@link KnowledgeBookMeta}.
 *
 * @see ItemMetaMock
 */
public class KnowledgeBookMetaMock extends ItemMetaMock implements KnowledgeBookMeta
{

	private static final int MAX_RECIPES = 32767;

	/**
	 * Constructs a new {@link KnowledgeBookMetaMock}.
	 */
	public KnowledgeBookMetaMock()
	{
		super();
	}

	@ApiStatus.Internal
	public KnowledgeBookMetaMock(Map<DataComponentType, Object> data)
	{
		super(data);
	}

	/**
	 * Constructs a new {@link KnowledgeBookMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public KnowledgeBookMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);
	}

	@Override
	public @NotNull KnowledgeBookMetaMock clone()
	{
		return (KnowledgeBookMetaMock) super.clone();
	}

	@Override
	public void addRecipe(@NotNull NamespacedKey @NotNull ... recipes)
	{
		List<Key> existingRecipes = new ArrayList<>(getOrDefault(DataComponentTypes.RECIPES, List.of()));
		List<Key> recipeList = Arrays.asList(recipes);
		int remainingEmptySlots = MAX_RECIPES - existingRecipes.size();
		existingRecipes.addAll(recipeList.subList(0, Math.min(remainingEmptySlots, recipeList.size())));
		set(DataComponentTypes.RECIPES, List.copyOf(existingRecipes));
	}

	@Override
	public @NotNull List<NamespacedKey> getRecipes()
	{
		return getOrDefault(DataComponentTypes.RECIPES, List.of())
				.stream()
				.map(NamespacedKey.class::cast)
				.toList();
	}

	@Override
	public boolean hasRecipes()
	{
		return !getOrDefault(DataComponentTypes.RECIPES, List.of()).isEmpty();
	}

	@Override
	public void setRecipes(@NotNull List<NamespacedKey> recipes)
	{
		Preconditions.checkNotNull(recipes);
		set(DataComponentTypes.RECIPES, List.copyOf(recipes));
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized KnowledgeBookMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the KnowledgeBookMetaMock class.
	 */
	@SuppressWarnings("unchecked")
	public static @NotNull KnowledgeBookMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		KnowledgeBookMetaMock serialMock = new KnowledgeBookMetaMock();
		serialMock.deserializeInternal(args);
		return serialMock;
	}

	@Override
	protected String getTypeName()
	{
		return "KNOWLEDGE_BOOK";
	}

}
