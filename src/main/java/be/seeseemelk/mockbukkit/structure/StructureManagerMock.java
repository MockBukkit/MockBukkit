package be.seeseemelk.mockbukkit.structure;

import com.google.common.base.Preconditions;
import org.bukkit.NamespacedKey;
import org.bukkit.structure.Structure;
import org.bukkit.structure.StructureManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StructureManagerMock implements StructureManager
{

	private Map<NamespacedKey, Structure> structures = new HashMap<>();

	@Override
	public @NotNull Map<NamespacedKey, Structure> getStructures()
	{
		return Collections.unmodifiableMap(structures);
	}

	@Override
	public @Nullable Structure getStructure(@NotNull NamespacedKey structureKey)
	{
		Preconditions.checkArgument(structureKey != null, "NamespacedKey structureKey cannot be null");

		return structures.get(structureKey);
	}

	@Override
	public @Nullable Structure registerStructure(@NotNull NamespacedKey structureKey, @NotNull Structure structure)
	{
		Preconditions.checkArgument(structureKey != null, "NamespacedKey structureKey cannot be null");
		Preconditions.checkArgument(structure != null, "Structure structure cannot be null");

		Optional<Structure> oldStructure = Optional.ofNullable(structures.put(structureKey, structure));
	}

	@Override
	public @Nullable Structure unregisterStructure(@NotNull NamespacedKey structureKey)
	{
		Preconditions.checkArgument(structureKey != null, "NamespacedKey structureKey cannot be null");

		return structures.remove(structureKey);
	}

	@Override
	public @Nullable Structure loadStructure(@NotNull NamespacedKey structureKey, boolean register)
	{
		return null;
	}

	@Override
	public @Nullable Structure loadStructure(@NotNull NamespacedKey structureKey)
	{
		return null;
	}

	@Override
	public void saveStructure(@NotNull NamespacedKey structureKey)
	{

	}

	@Override
	public void saveStructure(@NotNull NamespacedKey structureKey, @NotNull Structure structure) throws IOException
	{

	}

	@Override
	public void deleteStructure(@NotNull NamespacedKey structureKey) throws IOException
	{

	}

	@Override
	public void deleteStructure(@NotNull NamespacedKey structureKey, boolean unregister) throws IOException
	{

	}

	@Override
	public @NotNull File getStructureFile(@NotNull NamespacedKey structureKey)
	{
		return null;
	}

	@Override
	public @NotNull Structure loadStructure(@NotNull File file) throws IOException
	{
		return null;
	}

	@Override
	public @NotNull Structure loadStructure(@NotNull InputStream inputStream) throws IOException
	{
		return null;
	}

	@Override
	public void saveStructure(@NotNull File file, @NotNull Structure structure) throws IOException
	{

	}

	@Override
	public void saveStructure(@NotNull OutputStream outputStream, @NotNull Structure structure) throws IOException
	{

	}

	@Override
	public @NotNull Structure createStructure()
	{
		return null;
	}

	@Override
	public @NotNull Structure copy(@NotNull Structure structure)
	{
		return null;
	}

}
