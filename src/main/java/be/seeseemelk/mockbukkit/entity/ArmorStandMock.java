package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.destroystokyo.paper.block.TargetBlockInfo;
import com.destroystokyo.paper.entity.TargetEntityInfo;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

/**
 * This is the mock of an {@link ArmorStand}.
 *
 * @author TheBusyBiscuit
 */
public class ArmorStandMock extends LivingEntityMock implements ArmorStand
{

	private final EntityEquipment equipment = new EntityEquipmentMock(this);

	private boolean hasArms = false;
	private boolean isSmall = false;
	private boolean isMarker = false;
	private boolean hasBasePlate = true;
	private boolean isVisible = true;

	public ArmorStandMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public EntityType getType()
	{
		return EntityType.ARMOR_STAND;
	}

	@Override
	public @NotNull Component teamDisplayName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Location getOrigin()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean fromMobSpawner()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public CreatureSpawnEvent.@NotNull SpawnReason getEntitySpawnReason()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInRain()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInBubbleColumn()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInWaterOrRain()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInWaterOrBubbleColumn()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInWaterOrRainOrBubbleColumn()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInLava()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isTicking()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public boolean canTick()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCanTick(boolean tick)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack getItem(@NotNull EquipmentSlot slot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setItem(@NotNull EquipmentSlot slot, @Nullable ItemStack item)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Set<EquipmentSlot> getDisabledSlots()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setDisabledSlots(@NotNull EquipmentSlot... slots)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void addDisabledSlots(@NotNull EquipmentSlot... slots)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void removeDisabledSlots(@NotNull EquipmentSlot... slots)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isSlotDisabled(@NotNull EquipmentSlot slot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public ItemStack getBoots()
	{
		return getEquipment().getBoots();
	}

	@Override
	@Deprecated
	public void setBoots(ItemStack item)
	{
		getEquipment().setBoots(item);
	}

	@Override
	@Deprecated
	public ItemStack getLeggings()
	{
		return getEquipment().getLeggings();
	}

	@Override
	@Deprecated
	public void setLeggings(ItemStack item)
	{
		getEquipment().setLeggings(item);
	}

	@Override
	@Deprecated
	public ItemStack getChestplate()
	{
		return getEquipment().getChestplate();
	}

	@Override
	@Deprecated
	public void setChestplate(ItemStack item)
	{
		getEquipment().setChestplate(item);
	}

	@Override
	@Deprecated
	public ItemStack getHelmet()
	{
		return getEquipment().getHelmet();
	}

	@Override
	@Deprecated
	public void setHelmet(ItemStack item)
	{
		getEquipment().setHelmet(item);
	}

	@Override
	@Deprecated
	public ItemStack getItemInHand()
	{
		return getEquipment().getItemInMainHand();
	}

	@Override
	@Deprecated
	public void setItemInHand(ItemStack item)
	{
		getEquipment().setItemInMainHand(item);
	}

	@Override
	public EulerAngle getBodyPose()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBodyPose(EulerAngle pose)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public EulerAngle getLeftArmPose()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setLeftArmPose(EulerAngle pose)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public EulerAngle getRightArmPose()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setRightArmPose(EulerAngle pose)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public EulerAngle getLeftLegPose()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setLeftLegPose(EulerAngle pose)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public EulerAngle getRightLegPose()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setRightLegPose(EulerAngle pose)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public EulerAngle getHeadPose()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setHeadPose(EulerAngle pose)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasBasePlate()
	{
		return hasBasePlate;
	}

	@Override
	public void setBasePlate(boolean basePlate)
	{
		this.hasBasePlate = basePlate;
	}

	@Override
	public boolean isVisible()
	{
		return isVisible;
	}

	@Override
	public void setVisible(boolean visible)
	{
		this.isVisible = visible;
	}

	@Override
	public boolean hasArms()
	{
		return hasArms;
	}

	@Override
	public void setArms(boolean arms)
	{
		this.hasArms = arms;
	}

	@Override
	public boolean isSmall()
	{
		return isSmall;
	}

	@Override
	public void setSmall(boolean small)
	{
		this.isSmall = small;
	}

	@Override
	public boolean isMarker()
	{
		return isMarker;
	}

	@Override
	public void setMarker(boolean marker)
	{
		this.isMarker = marker;
	}

	@Override
	public void addEquipmentLock(EquipmentSlot slot, LockType lockType)
	{
		// TODO Equipment Locks use byte operations internally, they might be hard to implement
		throw new UnimplementedOperationException();
	}

	@Override
	public void removeEquipmentLock(EquipmentSlot slot, LockType lockType)
	{
		// TODO Equipment Locks use byte operations internally, they might be hard to implement
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasEquipmentLock(EquipmentSlot slot, LockType lockType)
	{
		// TODO Equipment Locks use byte operations internally, they might be hard to implement
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean canMove()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCanMove(boolean move)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isSleeping()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void attack(Entity target)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void swingMainHand()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void swingOffHand()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public Set<UUID> getCollidableExemptions()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> T getMemory(MemoryKey<T> memoryKey)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public <T> void setMemory(MemoryKey<T> memoryKey, T memoryValue)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public double getAbsorptionAmount()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setAbsorptionAmount(double amount)
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
	public @NotNull Component name()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
}
