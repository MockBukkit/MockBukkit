package io.papermc.paper.entity;

import com.google.common.base.Preconditions;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.SequencedCollection;
import java.util.Set;

@NullMarked
public class PlayerPostEffectsMock implements PlayerPostEffects
{
	private final List<Key> postEffects = new ArrayList<>();

	@Override
	public @Unmodifiable List<Key> values()
	{
		return Collections.unmodifiableList(postEffects);
	}

	@Override
	public boolean set(SequencedCollection<Key> postEffects)
	{
        Preconditions.checkArgument(postEffects != null, "postEffects cannot be null");

		Set<Key> ids = new HashSet<>(postEffects);

		for(Key effect : postEffects) {
			Preconditions.checkArgument(effect != null, "effects cannot be null");
			Preconditions.checkArgument(ids.add(effect), "effects cannot be duplicate [%s]", effect);
		}

		this.postEffects.clear();
		return this.postEffects.addAll(ids);
	}

	@Override
	public boolean add(Key key)
	{
		Preconditions.checkArgument(key != null, "key cannot be null");
		return this.postEffects.add(key);
	}

	@Override
	public boolean remove(Key key)
	{
		Preconditions.checkArgument(key != null, "key cannot be null");
		return this.postEffects.remove(key);
	}

	@Override
	public boolean clear()
	{
		if (this.postEffects.isEmpty())
		{
			return false;
		}
		else
		{
			this.postEffects.clear();
			return true;
		}
	}

	@Override
	public void update()
	{
		// Nothing to do here
	}

}
