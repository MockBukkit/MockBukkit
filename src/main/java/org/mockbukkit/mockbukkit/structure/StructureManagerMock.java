package org.mockbukkit.mockbukkit.structure;

import com.google.common.base.Preconditions;
import org.bukkit.NamespacedKey;
import org.bukkit.structure.Structure;
import org.bukkit.structure.StructureManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StructureManagerMock implements StructureManager
{

	private final Map<NamespacedKey, Structure> structures = new HashMap<>();

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

		return structures.put(structureKey, structure);
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
		Preconditions.checkArgument(structureKey != null, "NamespacedKey structureKey cannot be null");

		throw new UnimplementedOperationException("Load structure was not implemented yet.");
	}

	@Override
	public @Nullable Structure loadStructure(@NotNull NamespacedKey structureKey)
	{
		return this.loadStructure(structureKey, true);
	}

	@Override
	public void saveStructure(@NotNull NamespacedKey structureKey)
	{
		Preconditions.checkArgument(structureKey != null, "NamespacedKey structureKey cannot be null");

		throw new UnimplementedOperationException("Save structure was not implemented yet.");
	}

	@Override
	public void saveStructure(@NotNull NamespacedKey structureKey, @NotNull Structure structure) throws IOException
	{
		Preconditions.checkArgument(structureKey != null, "NamespacedKey structure cannot be null");
		Preconditions.checkArgument(structure != null, "Structure cannot be null");

		File structureFile = this.getStructureFile(structureKey);
		Files.createDirectories(structureFile.toPath().getParent());
		this.saveStructure(structureFile, structure);
	}

	@Override
	public void deleteStructure(@NotNull NamespacedKey structureKey)
	{
		this.deleteStructure(structureKey, true);
	}

	@Override
	public void deleteStructure(@NotNull NamespacedKey structureKey, boolean unregister)
	{
		Preconditions.checkArgument(structureKey != null, "NamespacedKey structureKey cannot be null");

		throw new UnimplementedOperationException("Delete structure was not implemented yet.");
	}

	@Override
	public @NotNull File getStructureFile(@NotNull NamespacedKey structureKey)
	{
		Preconditions.checkArgument(structureKey != null, "NamespacedKey structureKey cannot be null");

		throw new UnimplementedOperationException("Get structure file was not implemented yet.");
	}

	@Override
	public @NotNull Structure loadStructure(@NotNull File file) throws IOException
	{
		Preconditions.checkArgument(file != null, "File cannot be null");

		FileInputStream fileinputstream = new FileInputStream(file);
		return this.loadStructure(fileinputstream);
	}

	@Override
	public @NotNull Structure loadStructure(@NotNull InputStream inputStream)
	{
		Preconditions.checkArgument(inputStream != null, "inputStream cannot be null");

		throw new UnimplementedOperationException("Load structure was not implemented yet.");
	}

	@Override
	public void saveStructure(@NotNull File file, @NotNull Structure structure) throws IOException
	{
		Preconditions.checkArgument(file != null, "file cannot be null");
		Preconditions.checkArgument(structure != null, "structure cannot be null");

		FileOutputStream fileoutputstream = new FileOutputStream(file);
		this.saveStructure(fileoutputstream, structure);
	}

	@Override
	public void saveStructure(@NotNull OutputStream outputStream, @NotNull Structure structure)
	{
		Preconditions.checkArgument(outputStream != null, "outputStream cannot be null");
		Preconditions.checkArgument(structure != null, "structure cannot be null");

		throw new UnimplementedOperationException("Save structure was not implemented yet.");
	}

	@Override
	public @NotNull Structure createStructure()
	{
		return new StructureMock();
	}

	@Override
	public @NotNull Structure copy(@NotNull Structure structure)
	{
		Preconditions.checkArgument(structure != null, "Structure cannot be null");
		return new StructureMock(structure);
	}

}
