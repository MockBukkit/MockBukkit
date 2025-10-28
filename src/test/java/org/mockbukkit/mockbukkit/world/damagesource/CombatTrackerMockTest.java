package org.mockbukkit.mockbukkit.world.damagesource;

import com.destroystokyo.paper.MaterialTags;
import io.papermc.paper.world.damagesource.FallLocationType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Player;
import org.bukkit.entity.Warden;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.BeeMock;
import org.mockbukkit.mockbukkit.entity.FireworkMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.entity.WardenMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockBukkitExtension.class)
class CombatTrackerMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private PlayerMock player;
	private CombatTrackerMock combatTracker;

	@BeforeEach
	void beforeEach()
	{
		player = server.addPlayer("notch");
		combatTracker = new CombatTrackerMock(player);
	}

	/**
	 * For more details check <a href="https://minecraft.wiki/w/Death_messages#Current">minecraft wiki</a>
	 */
	@Nested
	class GetDeathMessage
	{

		@Test
		void givenNoCombatEntriesShouldReturnGenericMessage()
		{
			Component actual = combatTracker.getDeathMessage();

			assertPlainMessage("notch died", actual);
		}

		@Test
		void givenIntentionalGameDesignShouldReturnIntentionalGameDesign()
		{
			CombatEntryMock combatEntry = CombatEntryMock.builder()
					.damageSource(DamageSource.builder(DamageType.BAD_RESPAWN_POINT).build())
					.build();
			combatTracker.addCombatEntry(combatEntry);

			Component actual = combatTracker.getDeathMessage();

			assertPlainMessage("notch was killed by [Intentional Game Design]", actual);
		}

		@Nested
		class Cactus
		{

			@Test
			void killedByCactus()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.CACTUS).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was pricked to death", actual);
			}

			@Test
			void killedByCactusWhileRunningAwayFromEntity()
			{
				PlayerMock attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock fatalEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.CACTUS).build())
						.build();
				combatTracker.addCombatEntry(fatalEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch walked into a cactus while trying to escape jeb", actual);
			}

		}

		@Nested
		class Drowning
		{

			@Test
			void killedByDrowning()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.DROWN).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch drowned", actual);
			}

			@Test
			void killedByDrowningWhileRunningAwayFromEntity()
			{
				PlayerMock attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock fatalEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.DROWN).build())
						.build();
				combatTracker.addCombatEntry(fatalEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch drowned while trying to escape jeb", actual);
			}

		}

		@Nested
		class DryingOut
		{

			@Test
			void killedByDryingOut()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.DRY_OUT).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch died from dehydration", actual);
			}

			@Test
			void killedByDryingOutWhileRunningAwayFromEntity()
			{
				PlayerMock attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock fatalEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.DRY_OUT).build())
						.build();
				combatTracker.addCombatEntry(fatalEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch died from dehydration while trying to escape jeb", actual);
			}

		}

		@Nested
		class Elytra
		{

			@Test
			void killedByElytra()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FLY_INTO_WALL).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch experienced kinetic energy", actual);
			}

			@Test
			void killedByElytraWhileRunningAwayFromEntity()
			{
				PlayerMock attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock fatalEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FLY_INTO_WALL).build())
						.build();
				combatTracker.addCombatEntry(fatalEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch experienced kinetic energy while trying to escape jeb", actual);
			}

		}

		@Nested
		class Explosion
		{

			@Test
			void killedByExplosion()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.EXPLOSION).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch blew up", actual);
			}

			@Test
			void killedByExplosionWhileRunningAwayFromEntity()
			{
				PlayerMock attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock fatalEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.EXPLOSION).build())
						.build();
				combatTracker.addCombatEntry(fatalEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was blown up by jeb", actual);
			}

		}

		@Nested
		class Falling
		{

			@ParameterizedTest
			@ValueSource(ints = {1, 2, 3, 4, 5})
			void withLessThen5BlockFall(int fallAmount)
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FALL).build())
						.fallLocationType(FallLocationType.GENERIC)
						.fallDistance(fallAmount)
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch hit the ground too hard", actual);
			}

			@Test
			void with3BlockFallAndRunningAwayFromEntity()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FALL).build())
						.fallLocationType(FallLocationType.GENERIC)
						.fallDistance(3)
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch hit the ground too hard while trying to escape jeb", actual);
			}

			@ParameterizedTest
			@ValueSource(ints = {6, 7, 8, 9, 10})
			void with6BlockFall(int fallAmount)
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FALL).build())
						.fallLocationType(FallLocationType.GENERIC)
						.fallDistance(fallAmount)
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch fell from a high place", actual);
			}

			@Test
			void withLadderFall()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FALL).build())
						.fallLocationType(FallLocationType.LADDER)
						.fallDistance(6)
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch fell off a ladder", actual);
			}

			@Test
			void withVines()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FALL).build())
						.fallLocationType(FallLocationType.VINES)
						.fallDistance(6)
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch fell off some vines", actual);
			}

			@Test
			void withWeepingVines()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FALL).build())
						.fallLocationType(FallLocationType.WEEPING_VINES)
						.fallDistance(6)
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch fell off some weeping vines", actual);
			}

			@Test
			void withTwistingVines()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FALL).build())
						.fallLocationType(FallLocationType.TWISTING_VINES)
						.fallDistance(6)
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch fell off some twisting vines", actual);
			}

			@Test
			void withScaffolding()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FALL).build())
						.fallLocationType(FallLocationType.SCAFFOLDING)
						.fallDistance(6)
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch fell off scaffolding", actual);
			}

			@Test
			void whileClimbing()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FALL).build())
						.fallLocationType(FallLocationType.OTHER_CLIMBABLE)
						.fallDistance(6)
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch fell while climbing", actual);
			}

			@Test
			void whileTakingEnvironmentDamage()
			{
				CombatEntryMock a = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.CACTUS).build())
						.build();
				combatTracker.addCombatEntry(a);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FALL).build())
						.fallDistance(6)
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was doomed to fall", actual);
			}

			@Test
			void whileTakingEnvironmentDamageAndRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");

				CombatEntryMock a = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.PLAYER_ATTACK).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(a);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FALL).build())
						.fallDistance(6)
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was doomed to fall by jeb", actual);
			}

			@Test
			void whileTakingEnvironmentDamageAndRunningAwayFromPlayerWithCustomItem()
			{
				Player attacker = server.addPlayer("jeb");

				ItemStack wand = ItemStack.of(Material.STICK);
				wand.editMeta(m -> m.customName(Component.text("Leviosa Spell")));
				attacker.getEquipment().setItemInMainHand(wand);

				CombatEntryMock a = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.PLAYER_ATTACK).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(a);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FALL).build())
						.fallDistance(6)
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was doomed to fall by jeb using [Leviosa Spell]", actual);
			}

			@Test
			void withStalagmite()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.STALAGMITE).build())
						.fallDistance(6)
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was impaled on a stalagmite", actual);
			}

			@Test
			void withStalagmiteAndRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.STALAGMITE).build())
						.fallDistance(6)
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was impaled on a stalagmite while fighting jeb", actual);
			}

		}

		@Nested
		class FallingBlocks
		{

			@Test
			void withFallingAnvil()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FALLING_ANVIL).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was squashed by a falling anvil", actual);
			}

			@Test
			void withFallingBlock()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FALLING_BLOCK).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was squashed by a falling block", actual);
			}

			@Test
			void withFallingStalactite()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FALLING_STALACTITE).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was skewered by a falling stalactite", actual);
			}

		}

		@Nested
		class Fire
		{

			@Test
			void whenInFire()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.IN_FIRE).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch went up in flames", actual);
			}

			@Test
			void whenInFireAndRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.IN_FIRE).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch walked into fire while fighting jeb", actual);
			}

			@Test
			void whenOnFire()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.ON_FIRE).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch burned to death", actual);
			}

			@Test
			void whenOnFireAndRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.ON_FIRE).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was burned to a crisp while fighting jeb", actual);
			}

		}

		@Nested
		class FireworkRockets
		{

			@Test
			void byFireworkRocket()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FIREWORKS).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch went off with a bang", actual);
			}

			@Test
			void byFireworkRocketAndRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FIREWORKS).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch went off with a bang while fighting jeb", actual);
			}

			@Test
			void byFireworkRocketAndRunningAwayFromPlayerWithCustomName()
			{
				Player attacker = server.addPlayer("jeb");

				ItemStack wand = ItemStack.of(Material.FIREWORK_ROCKET);
				wand.editMeta(m -> m.customName(Component.text("Incendious Spell")));
				attacker.getEquipment().setItemInMainHand(wand);

				FireworkMock firework = new FireworkMock(server, UUID.randomUUID());
				firework.setShooter(attacker);
				firework.setItem(wand);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FIREWORKS).withCausingEntity(firework).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch went off with a bang due to a firework fired from [Incendious Spell] by jeb", actual);
			}

		}

		@Nested
		class Lava
		{

			@Test
			void bySwimInLava()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.LAVA).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch tried to swim in lava", actual);
			}

			@Test
			void bySwimInLavaWhileRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.LAVA).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch tried to swim in lava to escape jeb", actual);
			}

		}

		@Nested
		class Lightning
		{

			@Test
			void byLightning()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.LIGHTNING_BOLT).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was struck by lightning", actual);
			}

			@Test
			void byLightningWhileRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.LIGHTNING_BOLT).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was struck by lightning while fighting jeb", actual);
			}

		}

		@Nested
		class MagmaBlock
		{

			@Test
			void byMagmaBlock()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.HOT_FLOOR).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch discovered the floor was lava", actual);
			}

			@Test
			void byMagmaBlockWhileRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.HOT_FLOOR).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch walked into the danger zone due to jeb", actual);
			}

		}

		@Nested
		class Magic
		{

			@Test
			void byMagic()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.MAGIC).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was killed by magic", actual);
			}

			@Test
			void byMagicWhileRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.MAGIC).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was killed by magic while trying to escape jeb", actual);
			}

			@Test
			void byIndirectMagic()
			{
				Player attacker = server.addPlayer("jeb");

				CombatEntryMock dispenserEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.INDIRECT_MAGIC).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(dispenserEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was killed by jeb using magic", actual);
			}

			@Test
			void byIndirectMagicWithName()
			{
				Player attacker = server.addPlayer("jeb");

				ItemStack wand = ItemStack.of(Material.SPLASH_POTION);
				wand.editMeta(m -> m.customName(Component.text("Noxious Poison")));
				attacker.getEquipment().setItemInMainHand(wand);

				CombatEntryMock dispenserEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.INDIRECT_MAGIC).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(dispenserEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was killed by jeb using [Noxious Poison]", actual);
			}

		}

		@Nested
		class PowderSnow
		{

			@Test
			void byPowderSnow()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FREEZE).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch froze to death", actual);
			}

			@Test
			void byPowderSnowWhileRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FREEZE).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was frozen to death by jeb", actual);
			}

		}

		@Nested
		class PlayersAndMobs
		{

			@Test
			void byPlayer()
			{
				Player attacker = server.addPlayer("jeb");

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.PLAYER_ATTACK).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was slain by jeb", actual);
			}

			@Test
			void byPlayerWithItem()
			{
				Player attacker = server.addPlayer("jeb");

				ItemStack wand = ItemStack.of(Material.STICK);
				wand.editMeta(m -> m.customName(Component.text("Avada Kedavra Spell")));
				attacker.getEquipment().setItemInMainHand(wand);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.PLAYER_ATTACK).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was slain by jeb using [Avada Kedavra Spell]", actual);
			}

			@Test
			void byBee()
			{
				Bee bee = new BeeMock(server, UUID.randomUUID());

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.STING).withCausingEntity(bee).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was stung to death", actual);
			}

			@Test
			void byBeeWithCustomItem()
			{
				Bee attacker = new BeeMock(server, UUID.randomUUID());

				ItemStack wand = ItemStack.of(Material.HONEYCOMB);
				wand.editMeta(m -> m.customName(Component.text("Lovely honey")));
				attacker.getEquipment().setItemInMainHand(wand);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.STING).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was stung to death by entity using [Lovely honey]", actual);
			}

			@Test
			void byWarden()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.SONIC_BOOM).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was obliterated by a sonically-charged shriek", actual);
			}

			@Test
			void byWardenWithCustomItem()
			{
				Warden attacker = new WardenMock(server, UUID.randomUUID());

				ItemStack wand = ItemStack.of(Material.STICK);
				wand.editMeta(m -> m.customName(Component.text("Hey!")));
				attacker.getEquipment().setItemInMainHand(wand);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.SONIC_BOOM).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was obliterated by a sonically-charged shriek while trying to escape entity wielding [Hey!]", actual);
			}

			@Test
			void byMace()
			{
				Player attacker = server.addPlayer("jeb");

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.MACE_SMASH).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was smashed by jeb", actual);
			}

			@Test
			void byMaceWithCustomItem()
			{
				Player attacker = server.addPlayer("jeb");

				ItemStack mace = ItemStack.of(Material.MACE);
				mace.editMeta(m -> m.customName(Component.text("Hey!")));
				attacker.getEquipment().setItemInMainHand(mace);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.MACE_SMASH).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was smashed by jeb with [Hey!]", actual);
			}
		}

		@Nested
		class Projectiles
		{

			@Test
			void byArrow()
			{
				Player attacker = server.addPlayer("jeb");

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.ARROW).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was shot by jeb", actual);
			}

			@Test
			void byArrowWithCustomItem()
			{
				Player attacker = server.addPlayer("jeb");

				ItemStack wand = ItemStack.of(Material.CROSSBOW);
				wand.editMeta(m -> m.customName(Component.text("Beast!")));
				attacker.getEquipment().setItemInMainHand(wand);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.ARROW).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was shot by jeb using [Beast!]", actual);
			}

			@Test
			void bySnowball()
			{
				Player attacker = server.addPlayer("jeb");

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.THROWN).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was pummeled by jeb", actual);
			}

			@Test
			void bySnowballWithCustomItem()
			{
				Player attacker = server.addPlayer("jeb");

				ItemStack wand = ItemStack.of(Material.SNOWBALL);
				wand.editMeta(m -> m.customName(Component.text("Beast!")));
				attacker.getEquipment().setItemInMainHand(wand);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.THROWN).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was pummeled by jeb using [Beast!]", actual);
			}

			@Test
			void byFireball()
			{
				Player attacker = server.addPlayer("jeb");

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FIREBALL).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was fireballed by jeb", actual);
			}

			@Test
			void byFireballWithCustomItem()
			{
				Player attacker = server.addPlayer("jeb");

				ItemStack wand = ItemStack.of(Material.FIRE_CHARGE);
				wand.editMeta(m -> m.customName(Component.text("Incendious fireball!")));
				attacker.getEquipment().setItemInMainHand(wand);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.FIREBALL).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was fireballed by jeb using [Incendious fireball!]", actual);
			}

			@Test
			void bySkull()
			{
				Player attacker = server.addPlayer("jeb");

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.WITHER_SKULL).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was shot by a skull from jeb", actual);
			}

			@Test
			void bySkullWithCustomItem()
			{
				Player attacker = server.addPlayer("jeb");

				ItemStack wand = ItemStack.of(Material.WITHER_SKELETON_SKULL);
				wand.editMeta(m -> m.customName(Component.text("Poison skull")));
				attacker.getEquipment().setItemInMainHand(wand);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.WITHER_SKULL).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was shot by a skull from jeb using [Poison skull]", actual);
			}

		}

		@Nested
		class Starving
		{

			@Test
			void byStarving()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.STARVE).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch starved to death", actual);
			}

			@Test
			void byStarvingWhileRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.STARVE).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch starved to death while fighting jeb", actual);
			}

		}

		@Nested
		class Suffocation
		{

			@Test
			void bySuffocationInWall()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.IN_WALL).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch suffocated in a wall", actual);
			}

			@Test
			void bySuffocationInWallWhileRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.IN_WALL).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch suffocated in a wall while fighting jeb", actual);
			}

			@Test
			void byCramming()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.CRAMMING).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was squished too much", actual);
			}

			@Test
			void byCrammingWhileRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.CRAMMING).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was squashed by jeb", actual);
			}

			@Test
			void byOutsideBorder()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.OUTSIDE_BORDER).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch left the confines of this world", actual);
			}

			@Test
			void byOutsideBorderWhileRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.OUTSIDE_BORDER).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch left the confines of this world while fighting jeb", actual);
			}

		}

		@Nested
		class SweetBerryBushes
		{

			@Test
			void bySweetBerryBushes()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.SWEET_BERRY_BUSH).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was poked to death by a sweet berry bush", actual);
			}

			@Test
			void bySweetBerryBushesWhileRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.SWEET_BERRY_BUSH).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was poked to death by a sweet berry bush while trying to escape jeb", actual);
			}

		}

		@Nested
		class Thorns
		{

			@Test
			void byThorns()
			{
				Player attacker = server.addPlayer("jeb");

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.THORNS).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was killed while trying to hurt jeb", actual);
			}

			@Test
			void byThornsWithCustomName()
			{
				Player attacker = server.addPlayer("jeb");

				ItemStack wand = ItemStack.of(Material.STICK);
				wand.editMeta(m -> m.customName(Component.text("Killing you softly")));
				attacker.getEquipment().setItemInMainHand(wand);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.THORNS).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was killed by [Killing you softly] while trying to hurt jeb", actual);
			}

		}

		@Nested
		class Trident
		{

			@Test
			void byTrident()
			{
				Player attacker = server.addPlayer("jeb");

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.TRIDENT).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was impaled by jeb", actual);
			}

			@Test
			void byTridentWithCustomName()
			{
				Player attacker = server.addPlayer("jeb");

				ItemStack wand = ItemStack.of(Material.TRIDENT);
				wand.editMeta(m -> m.customName(Component.text("Killing you softly")));
				attacker.getEquipment().setItemInMainHand(wand);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.TRIDENT).withCausingEntity(attacker).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was impaled by jeb with [Killing you softly]", actual);
			}

		}

		@Nested
		class Void
		{

			@Test
			void byVoid()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.OUT_OF_WORLD).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch fell out of the world", actual);
			}

			@Test
			void byVoidWhileRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.OUT_OF_WORLD).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch didn't want to live in the same world as jeb", actual);
			}

		}

		@Nested
		class Wither
		{

			@Test
			void byVoid()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.WITHER).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch withered away", actual);
			}

			@Test
			void byVoidWhileRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.WITHER).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch withered away while fighting jeb", actual);
			}

		}

		@Nested
		class GenericDeath
		{

			@Test
			void byGeneric()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.GENERIC).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch died", actual);
			}

			@Test
			void byGenericWhileRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.GENERIC).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch died because of jeb", actual);
			}

			@Test
			void byGenericKill()
			{
				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.GENERIC_KILL).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was killed", actual);
			}

			@Test
			void byGenericKillWhileRunningAwayFromPlayer()
			{
				Player attacker = server.addPlayer("jeb");
				player.setKiller(attacker);

				CombatEntryMock combatEntry = CombatEntryMock.builder()
						.damageSource(DamageSource.builder(DamageType.GENERIC_KILL).build())
						.build();
				combatTracker.addCombatEntry(combatEntry);

				Component actual = combatTracker.getDeathMessage();

				assertPlainMessage("notch was killed while fighting jeb", actual);
			}
		}
	}

	@Nested
	class CalculateFallLocationType
	{
		private final WorldMock world = new WorldMock();
		private final Location blockLocation = new Location(world, 0, 64, 0);
		private final Location playerLocation = new Location(world, 0, 15, 0);

		@Test
		void byUnknownFall()
		{
			player.setLastClimbableLocation(null);
			player.setInWater(false);

			player.setLocation(playerLocation);

			@Nullable FallLocationType actual = combatTracker.calculateFallLocationType();

			assertNull(actual);
		}

		@Test
		void byWaterFall()
		{
			player.setLastClimbableLocation(null);
			player.setInWater(true);

			player.setLocation(playerLocation);

			@Nullable FallLocationType actual = combatTracker.calculateFallLocationType();

			assertEquals(FallLocationType.WATER, actual);
		}

		@Test
		void byLadder()
		{
			world.getBlockAt(blockLocation).setType(Material.LADDER);

			player.setLastClimbableLocation(blockLocation);
			player.setLocation(playerLocation);

			@Nullable FallLocationType actual = combatTracker.calculateFallLocationType();

			assertEquals(FallLocationType.LADDER, actual);
		}

		@ParameterizedTest
		@MethodSource("getTrapdoors()")
		void byTrapdoor(Material trapdoor)
		{
			world.getBlockAt(blockLocation).setType(trapdoor);

			player.setLastClimbableLocation(blockLocation);
			player.setLocation(playerLocation);

			@Nullable FallLocationType actual = combatTracker.calculateFallLocationType();

			assertEquals(FallLocationType.LADDER, actual);
		}

		@Test
		void byVine()
		{
			world.getBlockAt(blockLocation).setType(Material.VINE);

			player.setLastClimbableLocation(blockLocation);
			player.setLocation(playerLocation);

			@Nullable FallLocationType actual = combatTracker.calculateFallLocationType();

			assertEquals(FallLocationType.VINES, actual);
		}

		@ParameterizedTest
		@ValueSource(strings = {"WEEPING_VINES", "WEEPING_VINES_PLANT"})
		void byWeepingVine(Material material)
		{
			world.getBlockAt(blockLocation).setType(material);

			player.setLastClimbableLocation(blockLocation);
			player.setLocation(playerLocation);

			@Nullable FallLocationType actual = combatTracker.calculateFallLocationType();

			assertEquals(FallLocationType.WEEPING_VINES, actual);
		}

		@ParameterizedTest
		@ValueSource(strings = {"TWISTING_VINES", "TWISTING_VINES_PLANT"})
		void byTwistingVine(Material material)
		{
			world.getBlockAt(blockLocation).setType(material);

			player.setLastClimbableLocation(blockLocation);
			player.setLocation(playerLocation);

			@Nullable FallLocationType actual = combatTracker.calculateFallLocationType();

			assertEquals(FallLocationType.TWISTING_VINES, actual);
		}

		@Test
		void byScaffholding()
		{
			world.getBlockAt(blockLocation).setType(Material.SCAFFOLDING);

			player.setLastClimbableLocation(blockLocation);
			player.setLocation(playerLocation);

			@Nullable FallLocationType actual = combatTracker.calculateFallLocationType();

			assertEquals(FallLocationType.SCAFFOLDING, actual);
		}

		@Test
		void byOtherClimbable()
		{
			world.getBlockAt(blockLocation).setType(Material.STONE);

			player.setLastClimbableLocation(blockLocation);
			player.setLocation(playerLocation);

			@Nullable FallLocationType actual = combatTracker.calculateFallLocationType();

			assertEquals(FallLocationType.OTHER_CLIMBABLE, actual);
		}

		/**
		 * Get all the trapdoors.
		 *
		 * @return The possible list of materials for trapdoors.
		 */
		public static Stream<Arguments> getTrapdoors()
		{
			return MaterialTags.TRAPDOORS.getValues().stream().map(Arguments::of);
		}
	}

	/**
	 * Assert that the message sent to the user matches the expected format.
	 * <p>
	 * Note that this does not validate color, hover events or click events.
	 *
	 * @param expected	The expected output.
	 * @param actual	The received output.
	 */
	public static void assertPlainMessage(String expected, Component actual)
	{
		String actualString = PlainTextComponentSerializer.plainText().serialize(actual);
		assertEquals(expected, actualString);
	}

}
