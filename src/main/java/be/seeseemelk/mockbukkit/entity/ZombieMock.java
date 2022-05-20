package be.seeseemelk.mockbukkit.entity;

import java.util.Set;
import java.util.UUID;

import com.destroystokyo.paper.block.TargetBlockInfo;
import com.destroystokyo.paper.entity.Pathfinder;
import com.destroystokyo.paper.entity.TargetEntityInfo;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ZombieMock extends MonsterMock implements Zombie
{
	private final EntityEquipment equipment = new EntityEquipmentMock(this);

	private boolean baby;
	private boolean villager;
	private Villager.Profession profession;
	private boolean converting;
	private int conversionTime;

	public ZombieMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
		setMaxHealth(20);
		setHealth(20);
	}

	@Override
	public EntityType getType()
	{
		return EntityType.ZOMBIE;
	}


	@Override
	public @Nullable Block getTargetBlock(int maxDistance, TargetBlockInfo.@NotNull FluidMode fluidMode)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable BlockFace getTargetBlockFace(int maxDistance, TargetBlockInfo.@NotNull FluidMode fluidMode)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable TargetBlockInfo getTargetBlockInfo(int maxDistance, TargetBlockInfo.@NotNull FluidMode fluidMode)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Entity getTargetEntity(int maxDistance, boolean ignoreBlocks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable TargetEntityInfo getTargetEntityInfo(int maxDistance, boolean ignoreBlocks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getBeeStingerCooldown()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBeeStingerCooldown(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getBeeStingersInBody()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBeeStingersInBody(int count)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setKiller(@Nullable Player killer)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasLineOfSight(@NotNull Location location)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public EntityEquipment getEquipment()
	{
		return equipment;
	}

	@Override
	public int getArrowsStuck()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setArrowsStuck(int arrows)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getShieldBlockingDelay()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setShieldBlockingDelay(int delay)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable ItemStack getActiveItem()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void clearActiveItem()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getItemUseRemainingTime()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getHandRaisedTime()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isHandRaised()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull EquipmentSlot getHandRaised()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isJumping()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setJumping(boolean jumping)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void playPickupItemAnimation(@NotNull Item item, int quantity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getHurtDirection()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setHurtDirection(float hurtDirection)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Pathfinder getPathfinder()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInDaylight()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void lookAt(@NotNull Location location)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void lookAt(@NotNull Location location, float headRotationSpeed, float maxHeadPitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void lookAt(@NotNull Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void lookAt(@NotNull Entity entity, float headRotationSpeed, float maxHeadPitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void lookAt(double x, double y, double z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void lookAt(double x, double y, double z, float headRotationSpeed, float maxHeadPitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getHeadRotationSpeed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getMaxHeadPitch()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isLeftHanded()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setLeftHanded(boolean leftHanded)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isVillager()
	{
		return villager;
	}

	/**
	 * This are unimplemented because they Bukkit specifies they should always fail? (@Contract Tag)
	 *
	 * @param villager If the zombie is a village
	 */
	@Override
	public void setVillager(boolean villager)
	{
		this.villager = villager;
		throw new UnimplementedOperationException();
	}

	/**
	 * This are unimplemented because they Bukkit specifies they should always fail? (@Contract Tag)
	 *
	 * @param profession Villager profession to use
	 */
	@Override
	public void setVillagerProfession(Villager.Profession profession)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public Villager.Profession getVillagerProfession()
	{
		return profession;
	}

	@Override
	public boolean isConverting()
	{
		return converting;
	}

	@Override
	public int getConversionTime()
	{
		return conversionTime;
	}

	@Override
	public void setConversionTime(int conversionTime)
	{
		this.conversionTime = conversionTime;
	}

	@Override
	public boolean isDrowning()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void startDrowning(int drownedConversionTime)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void stopDrowning()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setArmsRaised(boolean raised)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isArmsRaised()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean shouldBurnInDay()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setShouldBurnInDay(boolean shouldBurnInDay)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean canBreakDoors()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCanBreakDoors(boolean canBreakDoors)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean supportsBreakingDoors()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getAge()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setAge(int age)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setAgeLock(boolean lock)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean getAgeLock()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public boolean isBaby()
	{
		return baby;
	}

	@Override
	@Deprecated
	public void setBaby(boolean baby)
	{
		this.baby = baby;
	}

	@Override
	public void setBaby()
	{
		setBaby(true);
	}

	@Override
	public void setAdult()
	{
		setBaby(false);
	}

	@Override
	public boolean isAdult()
	{
		return !isBaby();
	}

	@Override
	public boolean canBreed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBreed(boolean breed)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Component name()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Component customName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void customName(@Nullable Component customName)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void registerAttribute(@NotNull Attribute attribute)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
}
