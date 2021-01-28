package be.seeseemelk.mockbukkit.inventory.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.KnowledgeBookMeta;
import org.jetbrains.annotations.NotNull;

/**
 * This {@link ItemMetaMock} mocks the implementation of {@link KnowledgeBookMeta}.
 *
 * @author TheBusyBiscuit
 *
 */
public class KnowledgeBookMetaMock extends ItemMetaMock implements KnowledgeBookMeta
{

	private static final int MAX_RECIPES = 32767;

	private final List<NamespacedKey> recipes = new ArrayList<>();

	public KnowledgeBookMetaMock()
	{
		super();
	}

	public KnowledgeBookMetaMock(KnowledgeBookMeta meta)
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
		if (!(obj instanceof KnowledgeBookMetaMock))
		{
			return false;
		}

		KnowledgeBookMetaMock other = (KnowledgeBookMetaMock) obj;
		return recipes.equals(other.recipes);
	}

	@Override
	public KnowledgeBookMetaMock clone()
	{
		KnowledgeBookMetaMock mock = (KnowledgeBookMetaMock) super.clone();
		mock.recipes.addAll(recipes);
		return mock;
	}

	@Override
	public void addRecipe(@NotNull NamespacedKey... recipes)
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

}
