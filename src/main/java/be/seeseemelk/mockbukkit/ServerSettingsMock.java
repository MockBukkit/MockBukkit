package be.seeseemelk.mockbukkit;

import java.io.File;

/**
 * configuration sections for {@link ServerMock}
 * default values from https://bukkit.fandom.com/wiki/Bukkit.yml
 * @author ReinWD
 *
 * @see ServerMock
 */
public class ServerSettingsMock {
	//maybe configurable in the future.
	//todo: make this section configurable

	//settings
	private boolean allowEnd = true;
	private String updateFolder = "update";
	//default worldContainer is current working directory (according to the wiki)
	private File worldContainer = new File("");
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

	public boolean isAllowEnd() {
		return allowEnd;
	}

	public void setAllowEnd(boolean allowEnd) {
		this.allowEnd = allowEnd;
	}

	public String getUpdateFolder() {
		return updateFolder;
	}

	public void setUpdateFolder(String updateFolder) {
		this.updateFolder = updateFolder;
	}

	public File getWorldContainer() {
		return worldContainer;
	}

	public void setWorldContainer(File worldContainer) {
		this.worldContainer = worldContainer;
	}

	public long getConnectionThrottle() {
		return connectionThrottle;
	}

	public void setConnectionThrottle(long connectionThrottle) {
		this.connectionThrottle = connectionThrottle;
	}

	public String getShutdownMessage() {
		return shutdownMessage;
	}

	public void setShutdownMessage(String shutdownMessage) {
		this.shutdownMessage = shutdownMessage;
	}

	public int getMonsterSpawnLimit() {
		return monsterSpawnLimit;
	}

	public void setMonsterSpawnLimit(int monsterSpawnLimit) {
		this.monsterSpawnLimit = monsterSpawnLimit;
	}

	public int getAnimalSpawnLimit() {
		return animalSpawnLimit;
	}

	public void setAnimalSpawnLimit(int animalSpawnLimit) {
		this.animalSpawnLimit = animalSpawnLimit;
	}

	public int getWaterAnimalSpawnLimit() {
		return waterAnimalSpawnLimit;
	}

	public void setWaterAnimalSpawnLimit(int waterAnimalSpawnLimit) {
		this.waterAnimalSpawnLimit = waterAnimalSpawnLimit;
	}

	public int getWaterAmbientSpawnLimit() {
		return waterAmbientSpawnLimit;
	}

	public void setWaterAmbientSpawnLimit(int waterAmbientSpawnLimit) {
		this.waterAmbientSpawnLimit = waterAmbientSpawnLimit;
	}

	public int getAmbientSpawnLimit() {
		return ambientSpawnLimit;
	}

	public void setAmbientSpawnLimit(int ambientSpawnLimit) {
		this.ambientSpawnLimit = ambientSpawnLimit;
	}

	public int getTicksPerAnimalSpawns() {
		return ticksPerAnimalSpawns;
	}

	public void setTicksPerAnimalSpawns(int ticksPerAnimalSpawns) {
		this.ticksPerAnimalSpawns = ticksPerAnimalSpawns;
	}

	public int getTicksPerMonsterSpawns() {
		return ticksPerMonsterSpawns;
	}

	public void setTicksPerMonsterSpawns(int ticksPerMonsterSpawns) {
		this.ticksPerMonsterSpawns = ticksPerMonsterSpawns;
	}

	public int getTicksPerWaterSpawns() {
		return ticksPerWaterSpawns;
	}

	public void setTicksPerWaterSpawns(int ticksPerWaterSpawns) {
		this.ticksPerWaterSpawns = ticksPerWaterSpawns;
	}

	public int getTicksPerWaterAmbientSpawns() {
		return ticksPerWaterAmbientSpawns;
	}

	public void setTicksPerWaterAmbientSpawns(int ticksPerWaterAmbientSpawns) {
		this.ticksPerWaterAmbientSpawns = ticksPerWaterAmbientSpawns;
	}

	public int getTicksPerAmbientSpawns() {
		return ticksPerAmbientSpawns;
	}

	public void setTicksPerAmbientSpawns(int ticksPerAmbientSpawns) {
		this.ticksPerAmbientSpawns = ticksPerAmbientSpawns;
	}
}
