package be.seeseemelk.mockbukkit.entity;

import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.jetbrains.annotations.NotNull;

/**
 * This is the mock of an {@link ArmorStand}.
 *
 * @author TheBusyBiscuit
 *
 */
public class ArmorStandMock extends LivingEntityMock implements ArmorStand
{

	private final EntityEquipment equipment = new EntityEquipmentMock(this);

	private boolean hasArms = false;
	private boolean isSmall = false;
	private boolean isMarker = false;
	private boolean hasBasePlate = true;
	private boolean isVisible = true;

	private EulerAngle headPose = EulerAngle.ZERO;
	private EulerAngle bodyPose = EulerAngle.ZERO;
	private EulerAngle leftArmPose = new EulerAngle(Math.toRadians(-10.0f), 0.0f, Math.toRadians(-10.0f));
	private EulerAngle rightArmPose = new EulerAngle(Math.toRadians(-15.0f), 0.0f, Math.toRadians(10.0f));
	private EulerAngle leftLegPose = new EulerAngle(Math.toRadians(-1.0f), 0.0f, Math.toRadians(-1.0f));
	private EulerAngle rightLegPose = new EulerAngle(Math.toRadians(1.0f), 0.0f, Math.toRadians(1.0f));

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
	public EntityEquipment getEquipment()
	{
		return equipment;
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
	public @NotNull EulerAngle getBodyPose()
	{
		return bodyPose;
	}

	@Override
	public void setBodyPose(@NotNull EulerAngle pose)
	{
		this.bodyPose = pose;
	}

	@Override
	public @NotNull EulerAngle getLeftArmPose()
	{
		return leftArmPose;
	}

	@Override
	public void setLeftArmPose(@NotNull EulerAngle pose)
	{
		this.leftArmPose = pose;
	}

	@Override
	public @NotNull EulerAngle getRightArmPose()
	{
		return rightArmPose;
	}

	@Override
	public void setRightArmPose(@NotNull EulerAngle pose)
	{
		this.rightArmPose = pose;
	}

	@Override
	public @NotNull EulerAngle getLeftLegPose()
	{
		return leftLegPose;
	}

	@Override
	public void setLeftLegPose(@NotNull EulerAngle pose)
	{
		this.leftLegPose = pose;
	}

	@Override
	public @NotNull EulerAngle getRightLegPose()
	{
		return rightLegPose;
	}

	@Override
	public void setRightLegPose(@NotNull EulerAngle pose)
	{
		this.rightLegPose = pose;
	}

	@Override
	public @NotNull EulerAngle getHeadPose()
	{
		return headPose;
	}

	@Override
	public void setHeadPose(@NotNull EulerAngle pose)
	{
		this.headPose = pose;
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
    public @NotNull SpawnCategory getSpawnCategory()
    {
        return SpawnCategory.MISC;
    }

}
