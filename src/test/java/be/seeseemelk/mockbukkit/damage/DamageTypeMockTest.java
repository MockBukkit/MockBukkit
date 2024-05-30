package be.seeseemelk.mockbukkit.damage;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class DamageTypeMockTest
{
	@ParameterizedTest
	@MethodSource("getMinecraftDamageTypes")
	void from_GivenMinecraftExamples(ExpectedDamageType expectedDamageType)
	{

		JsonObject json = new JsonObject();
		json.addProperty(DamageTypeMock.EXHAUSTION, expectedDamageType.exhaustion());
		json.addProperty(DamageTypeMock.DEATH_MESSAGE_TYPE, expectedDamageType.deathMessageType());
		json.addProperty(DamageTypeMock.DAMAGE_SCALING, expectedDamageType.damageScaling());
		json.addProperty(DamageTypeMock.KEY, expectedDamageType.key());
		json.addProperty(DamageTypeMock.SOUND, expectedDamageType.sound());

		DamageTypeMock actual = DamageTypeMock.from(json);

		assertEquals(expectedDamageType.exhaustion(), actual.getExhaustion());
		assertEquals(expectedDamageType.deathMessageType(), actual.getDeathMessageType().toString());
		assertEquals(expectedDamageType.damageScaling(), actual.getDamageScaling().toString());
		assertEquals(expectedDamageType.key(), actual.getKey().toString());
		assertEquals(expectedDamageType.sound(), actual.getDamageEffect().getSound().getKey().toString());
	}

	record ExpectedDamageType(String key, String damageScaling, String sound, String deathMessageType, float exhaustion)
	{
	}

	/**
	 * The expected list of {@link org.bukkit.damage.DamageType} provided by minecraft namespace.
	 *
	 * @return The list of arguments provided by minecraft.
	 */
	public static Stream<Arguments> getMinecraftDamageTypes()
	{
		return Stream.of(
				Arguments.of(new ExpectedDamageType("minecraft:in_fire", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt_on_fire", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:lightning_bolt", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:on_fire", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt_on_fire", "DEFAULT", 0.0F)),
				Arguments.of(new ExpectedDamageType("minecraft:lava", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt_on_fire", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:hot_floor", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt_on_fire", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:in_wall", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.0F)),
				Arguments.of(new ExpectedDamageType("minecraft:cramming", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.0F)),
				Arguments.of(new ExpectedDamageType("minecraft:drown", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt_drown", "DEFAULT", 0.0F)),
				Arguments.of(new ExpectedDamageType("minecraft:starve", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.0F)),
				Arguments.of(new ExpectedDamageType("minecraft:cactus", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:fall", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "FALL_VARIANTS", 0.0F)),
				Arguments.of(new ExpectedDamageType("minecraft:fly_into_wall", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.0F)),
				Arguments.of(new ExpectedDamageType("minecraft:out_of_world", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.0F)),
				Arguments.of(new ExpectedDamageType("minecraft:generic", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.0F)),
				Arguments.of(new ExpectedDamageType("minecraft:magic", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.0F)),
				Arguments.of(new ExpectedDamageType("minecraft:wither", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.0F)),
				Arguments.of(new ExpectedDamageType("minecraft:dragon_breath", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.0F)),
				Arguments.of(new ExpectedDamageType("minecraft:dry_out", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:sweet_berry_bush", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt_sweet_berry_bush", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:freeze", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt_freeze", "DEFAULT", 0.0F)),
				Arguments.of(new ExpectedDamageType("minecraft:stalagmite", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.0F)),
				Arguments.of(new ExpectedDamageType("minecraft:falling_block", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:falling_anvil", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:falling_stalactite", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:sting", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:mob_attack", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:mob_attack_no_aggro", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:player_attack", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:arrow", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:trident", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:mob_projectile", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:fireworks", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:fireball", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt_on_fire", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:unattributed_fireball",
						"WHEN_CAUSED_BY_LIVING_NON_PLAYER", "minecraft:entity.player.hurt_on_fire", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:wither_skull", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:thrown", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:indirect_magic", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.0F)),
				Arguments.of(new ExpectedDamageType("minecraft:thorns", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:enchant.thorns.hit", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:explosion", "ALWAYS", "minecraft:entity.player.hurt",
						"DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:player_explosion", "ALWAYS",
						"minecraft:entity.player.hurt", "DEFAULT", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:sonic_boom", "ALWAYS", "minecraft:entity.player.hurt",
						"DEFAULT", 0.0F)),
				Arguments.of(new ExpectedDamageType("minecraft:bad_respawn_point", "ALWAYS",
						"minecraft:entity.player.hurt", "INTENTIONAL_GAME_DESIGN", 0.1F)),
				Arguments.of(new ExpectedDamageType("minecraft:outside_border", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.0F)),
				Arguments.of(new ExpectedDamageType("minecraft:generic_kill", "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
						"minecraft:entity.player.hurt", "DEFAULT", 0.0F)));
	}

}
