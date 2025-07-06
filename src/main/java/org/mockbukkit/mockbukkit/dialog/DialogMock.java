package org.mockbukkit.mockbukkit.dialog;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import io.papermc.paper.dialog.Dialog;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("NonExtendableApiUsage")
public class DialogMock implements Dialog
{

	@NotNull
	@ApiStatus.Internal
	public static DialogMock from(@NotNull JsonObject data)
	{
		Preconditions.checkNotNull(data);
		Preconditions.checkArgument(data.has("key"), "Missing json key");

		NamespacedKey key = NamespacedKey.fromString(data.get("key").getAsString());
		Preconditions.checkNotNull(key, "Missing key");

		return new DialogMock(key);
	}

	private final NamespacedKey key;

	private DialogMock(@NotNull NamespacedKey key)
	{
		this.key = Objects.requireNonNull(key, "The key cannot be null");
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return this.key;
	}

}
