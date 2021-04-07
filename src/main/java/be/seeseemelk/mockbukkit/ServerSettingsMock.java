package be.seeseemelk.mockbukkit;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

/**
 * configuration sections for {@link ServerMock}
 * default values from https://bukkit.fandom.com/wiki/Bukkit.yml
 * @author ReinWD
 *
 * @see ServerMock
 */
public class ServerSettingsMock
{
	//settings
	private boolean allowEnd = true;
	private String updateFolder = "update";
	//default worldContainer is current working directory (according to the wiki)
	private String worldContainer = "";
	private long connectionThrottle = 4000;
	private String shutdownMessage = "Server closed";

	//spawn-limits
	private int monsterSpawnLimit = 70;
	private int animalSpawnLimit = 10;
	private int waterAnimalSpawnLimit = 15;
	private int waterAmbientSpawnLimit = 20;
	private int ambientSpawnLimit = 15	;

	//ticks-per
	private int ticksPerAnimalSpawns = 400;
	private int ticksPerMonsterSpawns = 1;
	private int ticksPerWaterSpawns = 1;
	private int ticksPerWaterAmbientSpawns = 1;
	private int ticksPerAmbientSpawns = 1;

	private YamlConfiguration yamlConfiguration = new YamlConfiguration()
	{
		public String saveToString()
		{
			this.set("allowEnd", isAllowEnd());
			this.set("updateFolder", getUpdateFolder());
			this.set("worldContainer", getWorldContainer().getPath());
			this.set("connectionThrottle", getConnectionThrottle());
			this.set("shutdownMessage", getShutdownMessage());
			this.set("monsterSpawnLimit", getMonsterSpawnLimit());
			this.set("animalSpawnLimit", getAnimalSpawnLimit());
			this.set("waterAnimalSpawnLimit", getWaterAnimalSpawnLimit());
			this.set("waterAmbientSpawnLimit", getWaterAmbientSpawnLimit());
			this.set("ambientSpawnLimit", getAmbientSpawnLimit());
			this.set("ticksPerAnimalSpawns", getTicksPerAnimalSpawns());
			this.set("ticksPerMonsterSpawns", getTicksPerMonsterSpawns());
			this.set("ticksPerWaterSpawns", getTicksPerWaterSpawns());
			this.set("ticksPerWaterAmbientSpawns", getTicksPerWaterAmbientSpawns());
			this.set("ticksPerAmbientSpawns", getTicksPerAmbientSpawns());
			return super.saveToString();
		}

		@Override
		public void loadFromString(@NotNull String contents) throws InvalidConfigurationException
		{
			super.loadFromString(contents);
			ServerSettingsMock.this.setAllowEnd(getBoolean("allowEnd", allowEnd));
			ServerSettingsMock.this.setUpdateFolder(getString("updateFolder", updateFolder));
			ServerSettingsMock.this.setWorldContainer(getString("worldContainer", worldContainer));
			ServerSettingsMock.this.setConnectionThrottle(getLong("connectionThrottle", connectionThrottle));
			ServerSettingsMock.this.setShutdownMessage(getString("shutdownMessage", shutdownMessage));
			ServerSettingsMock.this.setMonsterSpawnLimit(getInt("monsterSpawnLimit", monsterSpawnLimit));
			ServerSettingsMock.this.setAnimalSpawnLimit(getInt("animalSpawnLimit", animalSpawnLimit));
			ServerSettingsMock.this.setWaterAnimalSpawnLimit(getInt("waterAnimalSpawnLimit", waterAnimalSpawnLimit));
			ServerSettingsMock.this.setWaterAmbientSpawnLimit(getInt("waterAmbientSpawnLimit", waterAmbientSpawnLimit));
			ServerSettingsMock.this.setAmbientSpawnLimit(getInt("ambientSpawnLimit", ambientSpawnLimit));
			ServerSettingsMock.this.setTicksPerAnimalSpawns(getInt("ticksPerAnimalSpawns", ticksPerAnimalSpawns));
			ServerSettingsMock.this.setTicksPerMonsterSpawns(getInt("ticksPerMonsterSpawns", ticksPerMonsterSpawns));
			ServerSettingsMock.this.setTicksPerWaterSpawns(getInt("ticksPerWaterSpawns", ticksPerWaterSpawns));
			ServerSettingsMock.this.setTicksPerWaterAmbientSpawns(getInt("ticksPerWaterAmbientSpawns", ticksPerWaterAmbientSpawns));
			ServerSettingsMock.this.setTicksPerAmbientSpawns(getInt("ticksPerAmbientSpawns", ticksPerAmbientSpawns));
		}
	};

	public void load(File file) throws IOException, InvalidConfigurationException
	{
		yamlConfiguration.load(file);
	}

	public void save(File file) throws IOException
	{
		yamlConfiguration.save(file);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}


		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		ServerSettingsMock that = (ServerSettingsMock) o;

		return new EqualsBuilder()
				.append(allowEnd, that.allowEnd)
				.append(connectionThrottle, that.connectionThrottle)
				.append(monsterSpawnLimit, that.monsterSpawnLimit)
				.append(animalSpawnLimit, that.animalSpawnLimit)
				.append(waterAnimalSpawnLimit, that.waterAnimalSpawnLimit)
				.append(waterAmbientSpawnLimit, that.waterAmbientSpawnLimit)
				.append(ambientSpawnLimit, that.ambientSpawnLimit)
				.append(ticksPerAnimalSpawns, that.ticksPerAnimalSpawns)
				.append(ticksPerMonsterSpawns, that.ticksPerMonsterSpawns)
				.append(ticksPerWaterSpawns, that.ticksPerWaterSpawns)
				.append(ticksPerWaterAmbientSpawns, that.ticksPerWaterAmbientSpawns)
				.append(ticksPerAmbientSpawns, that.ticksPerAmbientSpawns)
				.append(updateFolder, that.updateFolder)
				.append(worldContainer, that.worldContainer)
				.append(shutdownMessage, that.shutdownMessage).isEquals();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(17, 37)
				.append(allowEnd)
				.append(updateFolder)
				.append(worldContainer)
				.append(connectionThrottle)
				.append(shutdownMessage)
				.append(monsterSpawnLimit)
				.append(animalSpawnLimit)
				.append(waterAnimalSpawnLimit)
				.append(waterAmbientSpawnLimit)
				.append(ambientSpawnLimit)
				.append(ticksPerAnimalSpawns)
				.append(ticksPerMonsterSpawns)
				.append(ticksPerWaterSpawns)
				.append(ticksPerWaterAmbientSpawns)
				.append(ticksPerAmbientSpawns).toHashCode();
	}

	public boolean isAllowEnd()
    {
		return allowEnd;
	}

	public void setAllowEnd(boolean allowEnd)
    {
		this.allowEnd = allowEnd;
	}

	@NotNull
	public String getUpdateFolder()
    {
		Validate.notNull(updateFolder, "'updateFolder' must not be null");
    	return updateFolder;
	}

	public void setUpdateFolder(String updateFolder)
    {
		this.updateFolder = updateFolder;
	}

	@NotNull
	public File getWorldContainer()
    {
		Validate.notNull(worldContainer, "'worldContainer' must not be null");
		return new File(worldContainer);
	}

	public void setWorldContainer(File worldContainer)
    {
		this.worldContainer = worldContainer.getPath();
	}

	public void setWorldContainer(String worldContainer)
    {
		this.worldContainer = worldContainer;
	}

	public long getConnectionThrottle()
    {
		return connectionThrottle;
	}

	public void setConnectionThrottle(long connectionThrottle)
    {
		this.connectionThrottle = connectionThrottle;
	}

	@NotNull
	public String getShutdownMessage()
    {
		Validate.notNull(shutdownMessage, "'shutdownMessage' must not be null");
		return shutdownMessage;
	}

	public void setShutdownMessage(String shutdownMessage)
    {
		this.shutdownMessage = shutdownMessage;
	}

	public int getMonsterSpawnLimit()
    {
		return monsterSpawnLimit;
	}

	public void setMonsterSpawnLimit(int monsterSpawnLimit)
    {
		this.monsterSpawnLimit = monsterSpawnLimit;
	}

	public int getAnimalSpawnLimit()
    {
		return animalSpawnLimit;
	}

	public void setAnimalSpawnLimit(int animalSpawnLimit)
    {
		this.animalSpawnLimit = animalSpawnLimit;
	}

	public int getWaterAnimalSpawnLimit()
    {
		return waterAnimalSpawnLimit;
	}

	public void setWaterAnimalSpawnLimit(int waterAnimalSpawnLimit)
    {
		this.waterAnimalSpawnLimit = waterAnimalSpawnLimit;
	}

	public int getWaterAmbientSpawnLimit()
    {
		return waterAmbientSpawnLimit;
	}

	public void setWaterAmbientSpawnLimit(int waterAmbientSpawnLimit)
    {
		this.waterAmbientSpawnLimit = waterAmbientSpawnLimit;
	}

	public int getAmbientSpawnLimit()
	{
		return ambientSpawnLimit;
	}

	public void setAmbientSpawnLimit(int ambientSpawnLimit)
	{
		this.ambientSpawnLimit = ambientSpawnLimit;
	}

	public int getTicksPerAnimalSpawns()
	{
		return ticksPerAnimalSpawns;
	}

	public void setTicksPerAnimalSpawns(int ticksPerAnimalSpawns)
	{
		this.ticksPerAnimalSpawns = ticksPerAnimalSpawns;
	}

	public int getTicksPerMonsterSpawns()
	{
		return ticksPerMonsterSpawns;
	}

	public void setTicksPerMonsterSpawns(int ticksPerMonsterSpawns)
	{
		this.ticksPerMonsterSpawns = ticksPerMonsterSpawns;
	}

	public int getTicksPerWaterSpawns()
	{
		return ticksPerWaterSpawns;
	}

	public void setTicksPerWaterSpawns(int ticksPerWaterSpawns)
	{
		this.ticksPerWaterSpawns = ticksPerWaterSpawns;
	}

	public int getTicksPerWaterAmbientSpawns()
	{
		return ticksPerWaterAmbientSpawns;
	}

	public void setTicksPerWaterAmbientSpawns(int ticksPerWaterAmbientSpawns)
	{
		this.ticksPerWaterAmbientSpawns = ticksPerWaterAmbientSpawns;
	}

	public int getTicksPerAmbientSpawns()
	{
		return ticksPerAmbientSpawns;
	}

	public void setTicksPerAmbientSpawns(int ticksPerAmbientSpawns)
	{
		this.ticksPerAmbientSpawns = ticksPerAmbientSpawns;
	}
}
