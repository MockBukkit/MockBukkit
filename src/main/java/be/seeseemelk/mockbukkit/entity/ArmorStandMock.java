package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import io.papermc.paper.math.Rotations;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Mock implementation of an {@link ArmorStand}.
 *
 * @see LivingEntityMock
 */
public class ArmorStandMock extends LivingEntityMock implements ArmorStand
{

	private boolean hasArms = false;
	private boolean isSmall = false;
	private boolean isMarker = false;
	private boolean hasBasePlate = true;
	private boolean isVisible = true;

	private @NotNull EulerAngle headPose = EulerAngle.ZERO;
	private @NotNull EulerAngle bodyPose = EulerAngle.ZERO;
	private @NotNull EulerAngle leftArmPose = new EulerAngle(Math.toRadians(-10.0f), 0.0f, Math.toRadians(-10.0f));
	private @NotNull EulerAngle rightArmPose = new EulerAngle(Math.toRadians(-15.0f), 0.0f, Math.toRadians(10.0f));
	private @NotNull EulerAngle leftLegPose = new EulerAngle(Math.toRadians(-1.0f), 0.0f, Math.toRadians(-1.0f));
	private @NotNull EulerAngle rightLegPose = new EulerAngle(Math.toRadians(1.0f), 0.0f, Math.toRadians(1.0f));

	private final Set<EquipmentSlot> disabledSlots = EnumSet.noneOf(EquipmentSlot.class);

	/**
	 * Constructs a new {@link ArmorStandMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ArmorStandMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.ARMOR_STAND;
	}

	@Override
	@Deprecated
	public @NotNull ItemStack getBoots()
	{
		return getEquipment().getBoots();
	}

	@Override
	@Deprecated
	public void setBoots(@Nullable ItemStack item)
	{
		getEquipment().setBoots(item);
	}

	@Override
	@Deprecated
	public @NotNull ItemStack getLeggings()
	{
		return getEquipment().getLeggings();
	}

	@Override
	@Deprecated
	public void setLeggings(@Nullable ItemStack item)
	{
		getEquipment().setLeggings(item);
	}

	@Override
	@Deprecated
	public @NotNull ItemStack getChestplate()
	{
		return getEquipment().getChestplate();
	}

	@Override
	@Deprecated
	public void setChestplate(@Nullable ItemStack item)
	{
		getEquipment().setChestplate(item);
	}

	@Override
	@Deprecated
	public @NotNull ItemStack getHelmet()
	{
		return getEquipment().getHelmet();
	}

	@Override
	@Deprecated
	public void setHelmet(@Nullable ItemStack item)
	{
		getEquipment().setHelmet(item);
	}

	@Override
	@Deprecated
	public @NotNull ItemStack getItemInHand()
	{
		return getEquipment().getItemInMainHand();
	}

	@Override
	@Deprecated
	public void setItemInHand(@Nullable ItemStack item)
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
		Preconditions.checkNotNull(pose, "Pose cannot be null");
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
		Preconditions.checkNotNull(pose, "Pose cannot be null");
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
		Preconditions.checkNotNull(pose, "Pose cannot be null");
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
		Preconditions.checkNotNull(pose, "Pose cannot be null");
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
		Preconditions.checkNotNull(pose, "Pose cannot be null");
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
		Preconditions.checkNotNull(pose, "Pose cannot be null");
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
	public void addEquipmentLock(@NotNull EquipmentSlot slot, @NotNull LockType lockType)
	{
		// TODO Equipment Locks use byte operations internally, they might be hard to
		// implement
		throw new UnimplementedOperationException();
	}

	@Override
	public void removeEquipmentLock(@NotNull EquipmentSlot slot, @NotNull LockType lockType)
	{
		// TODO Equipment Locks use byte operations internally, they might be hard to
		// implement
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasEquipmentLock(@NotNull EquipmentSlot slot, @NotNull LockType lockType)
	{
		// TODO Equipment Locks use byte operations internally, they might be hard to
		// implement
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
		Preconditions.checkNotNull(slot, "Slot cannot be null");
		return switch (slot)
		{
		case HAND -> getEquipment().getItemInMainHand();
		case OFF_HAND -> getEquipment().getItemInOffHand();
		case FEET -> getBoots();
		case LEGS -> getLeggings();
		case CHEST -> getChestplate();
		case HEAD -> getHelmet();
		};
	}

	@Override
	public void setItem(@NotNull EquipmentSlot slot, @Nullable ItemStack item)
	{
		Preconditions.checkNotNull(slot, "Slot cannot be null");
		if (item == null)
			item = new ItemStack(Material.AIR);
		switch (slot)
		{
		case HAND -> getEquipment().setItemInMainHand(item);
		case OFF_HAND -> getEquipment().setItemInOffHand(item);
		case FEET -> setBoots(item);
		case LEGS -> setLeggings(item);
		case CHEST -> setChestplate(item);
		case HEAD -> setHelmet(item);
		}
	}

	@Override
	public @NotNull Set<EquipmentSlot> getDisabledSlots()
	{
		return EnumSet.copyOf(this.disabledSlots);
	}

	@Override
	public void setDisabledSlots(@NotNull EquipmentSlot... slots)
	{
		Preconditions.checkNotNull(slots, "Slots cannot be null");
		this.disabledSlots.clear();
		Collections.addAll(this.disabledSlots, slots);
	}

	@Override
	public void addDisabledSlots(@NotNull EquipmentSlot... slots)
	{
		Preconditions.checkNotNull(slots, "Slots cannot be null");
		Collections.addAll(this.disabledSlots, slots);
	}

	@Override
	public void removeDisabledSlots(@NotNull EquipmentSlot... slots)
	{
		Preconditions.checkNotNull(slots, "Slots cannot be null");
		List.of(slots).forEach(this.disabledSlots::remove);
	}

	@Override
	public boolean isSlotDisabled(@NotNull EquipmentSlot slot)
	{
		Preconditions.checkNotNull(slot, "Slot cannot be null");
		return this.disabledSlots.contains(slot);
	}

	@Override
	public @NotNull Rotations getBodyRotations()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBodyRotations(@NotNull Rotations rotations)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Rotations getLeftArmRotations()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setLeftArmRotations(@NotNull Rotations rotations)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Rotations getRightArmRotations()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setRightArmRotations(@NotNull Rotations rotations)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Rotations getLeftLegRotations()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setLeftLegRotations(@NotNull Rotations rotations)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Rotations getRightLegRotations()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setRightLegRotations(@NotNull Rotations rotations)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Rotations getHeadRotations()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setHeadRotations(@NotNull Rotations rotations)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
