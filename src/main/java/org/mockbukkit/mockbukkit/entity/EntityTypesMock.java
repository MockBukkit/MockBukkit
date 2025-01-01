package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.entity.Allay;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Armadillo;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Bogged;
import org.bukkit.entity.Breeze;
import org.bukkit.entity.BreezeWindCharge;
import org.bukkit.entity.Camel;
import org.bukkit.entity.Cat;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.ChestBoat;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cod;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Dolphin;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Egg;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Firework;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.GlowItemFrame;
import org.bukkit.entity.GlowSquid;
import org.bukkit.entity.Goat;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Illusioner;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.Llama;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Marker;
import org.bukkit.entity.Mule;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.PolarBear;
import org.bukkit.entity.PufferFish;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Ravager;
import org.bukkit.entity.Salmon;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.entity.Slime;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Stray;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Tadpole;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Trident;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.Turtle;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Vindicator;
import org.bukkit.entity.Warden;
import org.bukkit.entity.WindCharge;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zoglin;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieHorse;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.entity.boat.AcaciaBoat;
import org.bukkit.entity.boat.AcaciaChestBoat;
import org.bukkit.entity.boat.BambooChestRaft;
import org.bukkit.entity.boat.BambooRaft;
import org.bukkit.entity.boat.BirchBoat;
import org.bukkit.entity.boat.BirchChestBoat;
import org.bukkit.entity.boat.CherryBoat;
import org.bukkit.entity.boat.CherryChestBoat;
import org.bukkit.entity.boat.DarkOakBoat;
import org.bukkit.entity.boat.DarkOakChestBoat;
import org.bukkit.entity.boat.JungleBoat;
import org.bukkit.entity.boat.JungleChestBoat;
import org.bukkit.entity.boat.MangroveBoat;
import org.bukkit.entity.boat.MangroveChestBoat;
import org.bukkit.entity.boat.OakBoat;
import org.bukkit.entity.boat.OakChestBoat;
import org.bukkit.entity.boat.PaleOakBoat;
import org.bukkit.entity.boat.PaleOakChestBoat;
import org.bukkit.entity.boat.SpruceBoat;
import org.bukkit.entity.boat.SpruceChestBoat;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.bukkit.entity.minecart.RideableMinecart;
import org.bukkit.entity.minecart.SpawnerMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.boat.AcaciaBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.AcaciaChestBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.BambooChestRaftMock;
import org.mockbukkit.mockbukkit.entity.boat.BambooRaftMock;
import org.mockbukkit.mockbukkit.entity.boat.BirchBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.BirchChestBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.CherryBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.CherryChestBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.DarkOakBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.DarkOakChestBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.JungleBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.JungleChestBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.MangroveBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.MangroveChestBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.OakBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.OakChestBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.PaleOakBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.PaleOakChestBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.SpruceBoatMock;
import org.mockbukkit.mockbukkit.entity.boat.SpruceChestBoatMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

@ApiStatus.Internal
public final class EntityTypesMock
{

	private static final EntityTypesMock INSTANCE = withDefaults().build();

	private static EntityTypesMock getInstance()
	{
		return INSTANCE;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static Builder withDefaults()
	{
		return builder()
			.register(AcaciaBoat.class, AcaciaBoatMock.class, AcaciaBoatMock::new)
			.register(AcaciaChestBoat.class, AcaciaChestBoatMock.class, AcaciaChestBoatMock::new)
			.register(Allay.class, AllayMock.class, AllayMock::new)
			.register(AreaEffectCloud.class, AreaEffectCloudMock.class, AreaEffectCloudMock::new)
			.register(Armadillo.class, ArmadilloMock.class, ArmadilloMock::new)
			.register(ArmorStand.class, ArmorStandMock.class, ArmorStandMock::new)
			.register(Arrow.class, ArrowMock.class, ArrowMock::new)
			.register(Axolotl.class, AxolotlMock.class, AxolotlMock::new)
			.register(BambooChestRaft.class, BambooChestRaftMock.class, BambooChestRaftMock::new)
			.register(BambooRaft.class, BambooRaftMock.class, BambooRaftMock::new)
			.register(Bat.class, BatMock.class, BatMock::new)
			.register(Bee.class, BeeMock.class, BeeMock::new)
			.register(BirchBoat.class, BirchBoatMock.class, BirchBoatMock::new)
			.register(BirchChestBoat.class, BirchChestBoatMock.class, BirchChestBoatMock::new)
			.register(Blaze.class, BlazeMock.class, BlazeMock::new)
			.register(BlockDisplay.class, BlockDisplayMock.class, BlockDisplayMock::new)
			.register(Boat.class, OakBoatMock.class, OakBoatMock::new)
			.register(Bogged.class, BoggedMock.class, BoggedMock::new)
			.register(Breeze.class, BreezeMock.class, BreezeMock::new)
			.register(BreezeWindCharge.class, BreezeWindChargeMock.class, BreezeWindChargeMock::new)
			.register(Camel.class, CamelMock.class, CamelMock::new)
			.register(Cat.class, CatMock.class, CatMock::new)
			.register(CaveSpider.class, CaveSpiderMock.class, CaveSpiderMock::new)
			.register(CherryBoat.class, CherryBoatMock.class, CherryBoatMock::new)
			.register(CherryChestBoat.class, CherryChestBoatMock.class, CherryChestBoatMock::new)
			.register(ChestBoat.class, OakChestBoatMock.class, OakChestBoatMock::new)
			.register(Chicken.class, ChickenMock.class, ChickenMock::new)
			.register(Cod.class, CodMock.class, CodMock::new)
			.register(CommandMinecart.class, CommandMinecartMock.class, CommandMinecartMock::new)
			.register(Cow.class, CowMock.class, CowMock::new)
			.register(Creeper.class, CreeperMock.class, CreeperMock::new)
			.register(DarkOakBoat.class, DarkOakBoatMock.class, DarkOakBoatMock::new)
			.register(DarkOakChestBoat.class, DarkOakChestBoatMock.class, DarkOakChestBoatMock::new)
			.register(Dolphin.class, DolphinMock.class, DolphinMock::new)
			.register(Donkey.class, DonkeyMock.class, DonkeyMock::new)
			.register(DragonFireball.class, DragonFireballMock.class, DragonFireballMock::new)
			.register(Drowned.class, DrownedMock.class, DrownedMock::new)
			.register(Egg.class, EggMock.class, EggMock::new)
			.register(ElderGuardian.class, ElderGuardianMock.class, ElderGuardianMock::new)
			.register(EnderCrystal.class, EnderCrystalMock.class, EnderCrystalMock::new)
			.register(EnderDragon.class, EnderDragonMock.class, EnderDragonMock::new)
			.register(Enderman.class, EndermanMock.class, EndermanMock::new)
			.register(Endermite.class, EndermiteMock.class, EndermiteMock::new)
			.register(EnderPearl.class, EnderPearlMock.class, EnderPearlMock::new)
			.register(EnderSignal.class, EnderSignalMock.class, EnderSignalMock::new)
			.register(Evoker.class, EvokerMock.class, EvokerMock::new)
			.register(EvokerFangs.class, EvokerFangsMock.class, EvokerFangsMock::new)
			.register(ExperienceOrb.class, ExperienceOrbMock.class, ExperienceOrbMock::new)
			.register(ExplosiveMinecart.class, ExplosiveMinecartMock.class, ExplosiveMinecartMock::new)
			.register(FallingBlock.class, FallingBlockMock.class, FallingBlockMock::new)
			.register(Firework.class, FireworkMock.class, FireworkMock::new)
			.register(FishHook.class, FishHookMock.class, FishHookMock::new)
			.register(Fox.class, FoxMock.class, FoxMock::new)
			.register(Frog.class, FrogMock.class, FrogMock::new)
			.register(Ghast.class, GhastMock.class, GhastMock::new)
			.register(Giant.class, GiantMock.class, GiantMock::new)
			.register(GlowItemFrame.class, GlowItemFrameMock.class, GlowItemFrameMock::new)
			.register(GlowSquid.class, GlowSquidMock.class, GlowSquidMock::new)
			.register(Goat.class, GoatMock.class, GoatMock::new)
			.register(Guardian.class, GuardianMock.class, GuardianMock::new)
			.register(Hoglin.class, HoglinMock.class, HoglinMock::new)
			.register(HopperMinecart.class, HopperMinecartMock.class, HopperMinecartMock::new)
			.register(Horse.class, HorseMock.class, HorseMock::new)
			.register(Husk.class, HuskMock.class, HuskMock::new)
			.register(Illusioner.class, IllusionerMock.class, IllusionerMock::new)
			.register(Interaction.class, InteractionMock.class, InteractionMock::new)
			.register(IronGolem.class, IronGolemMock.class, IronGolemMock::new)
			.register(ItemDisplay.class, ItemDisplayMock.class, ItemDisplayMock::new)
			.register(ItemFrame.class, ItemFrameMock.class, ItemFrameMock::new)
			.register(JungleBoat.class, JungleBoatMock.class, JungleBoatMock::new)
			.register(JungleChestBoat.class, JungleChestBoatMock.class, JungleChestBoatMock::new)
			.register(LargeFireball.class, LargeFireballMock.class, LargeFireballMock::new)
			.register(LeashHitch.class, LeashHitchMock.class, LeashHitchMock::new)
			.register(Llama.class, LlamaMock.class, LlamaMock::new)
			.register(LlamaSpit.class, LlamaSpitMock.class, LlamaSpitMock::new)
			.register(MagmaCube.class, MagmaCubeMock.class, MagmaCubeMock::new)
			.register(MangroveBoat.class, MangroveBoatMock.class, MangroveBoatMock::new)
			.register(MangroveChestBoat.class, MangroveChestBoatMock.class, MangroveChestBoatMock::new)
			.register(Marker.class, MarkerMock.class, MarkerMock::new)
			.register(Mule.class, MuleMock.class, MuleMock::new)
			.register(MushroomCow.class, MushroomCowMock.class, MushroomCowMock::new)
			.register(OakBoat.class, OakBoatMock.class, OakBoatMock::new)
			.register(OakChestBoat.class, OakChestBoatMock.class, OakChestBoatMock::new)
			.register(Ocelot.class, OcelotMock.class, OcelotMock::new)
			.register(Painting.class, PaintingMock.class, PaintingMock::new)
			.register(PaleOakBoat.class, PaleOakBoatMock.class, PaleOakBoatMock::new)
			.register(PaleOakChestBoat.class, PaleOakChestBoatMock.class, PaleOakChestBoatMock::new)
			.register(Panda.class, PandaMock.class, PandaMock::new)
			.register(Parrot.class, ParrotMock.class, ParrotMock::new)
			.register(Pig.class, PigMock.class, PigMock::new)
			.register(PigZombie.class, PigZombieMock.class, PigZombieMock::new)
			.register(Pillager.class, PillagerMock.class, PillagerMock::new)
			.register(PolarBear.class, PolarBearMock.class, PolarBearMock::new)
			.register(PoweredMinecart.class, PoweredMinecartMock.class, PoweredMinecartMock::new)
			.register(PufferFish.class, PufferFishMock.class, PufferFishMock::new)
			.register(Rabbit.class, RabbitMock.class, RabbitMock::new)
			.register(Ravager.class, RavagerMock.class, RavagerMock::new)
			.register(RideableMinecart.class, RideableMinecartMock.class, RideableMinecartMock::new)
			.register(Salmon.class, SalmonMock.class, SalmonMock::new)
			.register(Sheep.class, SheepMock.class, SheepMock::new)
			.register(Shulker.class, ShulkerMock.class, ShulkerMock::new)
			.register(Silverfish.class, SilverfishMock.class, SilverfishMock::new)
			.register(Skeleton.class, SkeletonMock.class, SkeletonMock::new)
			.register(SkeletonHorse.class, SkeletonHorseMock.class, SkeletonHorseMock::new)
			.register(Slime.class, SlimeMock.class, SlimeMock::new)
			.register(SmallFireball.class, SmallFireballMock.class, SmallFireballMock::new)
			.register(Snowball.class, SnowballMock.class, SnowballMock::new)
			.register(Snowman.class, SnowmanMock.class, SnowmanMock::new)
			.register(SpawnerMinecart.class, SpawnerMinecartMock.class, SpawnerMinecartMock::new)
			.register(SpectralArrow.class, SpectralArrowMock.class, SpectralArrowMock::new)
			.register(Spider.class, SpiderMock.class, SpiderMock::new)
			.register(SpruceBoat.class, SpruceBoatMock.class, SpruceBoatMock::new)
			.register(SpruceChestBoat.class, SpruceChestBoatMock.class, SpruceChestBoatMock::new)
			.register(Squid.class, SquidMock.class, SquidMock::new)
			.register(StorageMinecart.class, StorageMinecartMock.class, StorageMinecartMock::new)
			.register(Stray.class, StrayMock.class, StrayMock::new)
			.register(Tadpole.class, TadpoleMock.class, TadpoleMock::new)
			.register(ThrownExpBottle.class, ThrownExpBottleMock.class, ThrownExpBottleMock::new)
			.register(ThrownPotion.class, ThrownPotionMock.class, ThrownPotionMock::new)
			.register(TNTPrimed.class, TNTPrimedMock.class, TNTPrimedMock::new)
			.register(Trident.class, TridentMock.class, TridentMock::new)
			.register(TropicalFish.class, TropicalFishMock.class, TropicalFishMock::new)
			.register(Turtle.class, TurtleMock.class, TurtleMock::new)
			.register(Villager.class, VillagerMock.class, VillagerMock::new)
			.register(Vindicator.class, VindicatorMock.class, VindicatorMock::new)
			.register(Warden.class, WardenMock.class, WardenMock::new)
			.register(WindCharge.class, WindChargeMock.class, WindChargeMock::new)
			.register(Witch.class, WitchMock.class, WitchMock::new)
			.register(Wither.class, WitherMock.class, WitherMock::new)
			.register(WitherSkeleton.class, WitherSkeletonMock.class, WitherSkeletonMock::new)
			.register(WitherSkull.class, WitherSkullMock.class, WitherSkullMock::new)
			.register(Wolf.class, WolfMock.class, WolfMock::new)
			.register(Zoglin.class, ZoglinMock.class, ZoglinMock::new)
			.register(Zombie.class, ZombieMock.class, ZombieMock::new)
			.register(ZombieHorse.class, ZombieHorseMock.class, ZombieHorseMock::new)
			.register(ZombieVillager.class, ZombieVillagerMock.class, ZombieVillagerMock::new);
	}

	public static <T extends Entity> @NotNull EntityMock createEntity(@NotNull Class<T> bukkitClazz, @NotNull ServerMock server, @NotNull UUID entityUUID)
	{
		return getInstance().create(bukkitClazz, server, entityUUID);
	}

	public static <T extends Entity> @NotNull EntityMock createEntity(@NotNull Class<T> bukkitClazz, @NotNull ServerMock server)
	{
		return EntityTypesMock.createEntity(bukkitClazz, server, UUID.randomUUID());
	}

	private final Map<Class<? extends Entity>, EntityData<? extends Entity, ? extends EntityMock>> bukkitToMockData;

	private EntityTypesMock(@NotNull Map<Class<? extends Entity>, EntityData<? extends Entity, ? extends EntityMock>> bukkitToMockData)
	{
		this.bukkitToMockData = Preconditions.checkNotNull(bukkitToMockData);
	}

	public <T extends Entity> @NotNull EntityMock create(@NotNull Class<T> bukkitClazz, @NotNull ServerMock server, @NotNull UUID entityUUID)
	{
		Preconditions.checkArgument(bukkitClazz != null, "bukkitClazz cannot be null");
		Preconditions.checkArgument(server != null, "server cannot be null");
		Preconditions.checkArgument(entityUUID != null, "entityUUID cannot be null");

		if (bukkitClazz == Item.class)
		{
			throw new IllegalArgumentException("Items must be spawned using World#dropItem(...)");
		} else if (bukkitClazz == Player.class)
		{
			throw new IllegalArgumentException("Player Entities cannot be spawned, use ServerMock#addPlayer(...)");
		}

		EntityData<? extends Entity, ? extends EntityMock> data = bukkitToMockData.get(bukkitClazz);
		if (data == null) {
			throw new UnimplementedOperationException(String.format("Mock for entity %s was not implemented yet.", bukkitClazz.getName()));
		}

		@Nullable EntityMock mockedEntity = data.mockFactory().apply(server, entityUUID);
		Preconditions.checkState(mockedEntity != null, "After creating the mock the entity was null.");

		Class<?> mockedEntityClass = mockedEntity.getClass();
		Preconditions.checkState(bukkitClazz.isAssignableFrom(mockedEntityClass), "The class %s is not a subclass of %s", mockedEntityClass, bukkitClazz);

		return mockedEntity;
	}

	private record EntityData<E extends Entity, M extends EntityMock>(@NotNull Class<E> entityClass,
												@NotNull Class<M> mockClass,
												@NotNull BiFunction<ServerMock, UUID, EntityMock> mockFactory)
	{}

	public static class Builder
	{

		private final Map<Class<? extends Entity>, EntityData<? extends Entity, ? extends EntityMock>> mapping = new HashMap<>();

		@ApiStatus.Internal
		public <E extends Entity, M extends EntityMock> Builder register(@NotNull Class<E> bukkitClazz,
																		 @NotNull Class<M> mockClazz,
																		 @NotNull BiFunction<ServerMock, UUID, EntityMock> mockFactory)
		{
			Preconditions.checkArgument(bukkitClazz != null, "Cannot register a null bukkit class");
			Preconditions.checkArgument(mockClazz != null, "Cannot register a null mock class");
			Preconditions.checkArgument(bukkitClazz.isAssignableFrom(mockClazz), "The class %s is not a subclass of %s", mockClazz, bukkitClazz);
			Preconditions.checkArgument(mockFactory != null, "Cannot register a null mock factory");
			Preconditions.checkArgument(!mapping.containsKey(bukkitClazz), "Cannot register type %s because it's already registered.", bukkitClazz);
			mapping.put(bukkitClazz, new EntityData<>(bukkitClazz, mockClazz, mockFactory));
			return this;
		}

		public EntityTypesMock build()
		{
			return new EntityTypesMock(Collections.unmodifiableMap(this.mapping));
		}

	}

}
