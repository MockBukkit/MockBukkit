package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.KnowledgeBookMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
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

	private final List<NamespacedKey> recipes = new ArrayList<>();

	/**
	 * Constructs a new {@link KnowledgeBookMetaMock}.
	 */
	public KnowledgeBookMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link KnowledgeBookMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public KnowledgeBookMetaMock(@NotNull KnowledgeBookMeta meta)
	{
		super(meta);

		recipes.addAll(meta.getRecipes());
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + recipes.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!super.equals(obj))
		{
			return false;
		}
		if (!(obj instanceof KnowledgeBookMetaMock other))
		{
			return false;
		}

		return recipes.equals(other.recipes);
	}

	@Override
	public @NotNull KnowledgeBookMetaMock clone()
	{
		KnowledgeBookMetaMock mock = (KnowledgeBookMetaMock) super.clone();
		mock.recipes.addAll(recipes);
		return mock;
	}

	@Override
	public void addRecipe(@NotNull NamespacedKey @NotNull ... recipes)
	{
		for (NamespacedKey recipe : recipes)
		{
			if (this.recipes.size() >= MAX_RECIPES)
			{
				return;
			}

			if (recipe != null)
			{
				this.recipes.add(recipe);
			}
		}
	}

	@Override
	public @NotNull List<NamespacedKey> getRecipes()
	{
		return Collections.unmodifiableList(recipes);
	}

	@Override
	public boolean hasRecipes()
	{
		return !recipes.isEmpty();
	}

	@Override
	public void setRecipes(@NotNull List<NamespacedKey> recipes)
	{
		this.recipes.clear();
		this.addRecipe(recipes.toArray(new NamespacedKey[0]));
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
		if (args.containsKey("recipes"))
		{
			serialMock.addRecipe(((List<String>) args.get("recipes")).stream().map(NamespacedKey::fromString).toArray(NamespacedKey[]::new));
		}

		return serialMock;
	}

	/**
	 * Serializes the properties of an KnowledgeBookMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the KnowledgeBookMetaMock.
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		final Map<String, Object> serialized = super.serialize();
		serialized.put("recipes", recipes.stream().map(NamespacedKey::toString).toList());
		return serialized;
	}

	@Override
	protected String getTypeName()
	{
		return "KNOWLEDGE_BOOK";
	}

}
