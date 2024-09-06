package org.mockbukkit.mockbukkit.entity.data;

/**
 * A type of a entity, it still has the same
 * {@link org.bukkit.entity.EntityType}.
 */
public enum EntitySubType
{
	/**
	 * The default subtype (adult or ageless)
	 */
	DEFAULT("default"),

	/**
	 * Baby
	 */
	BABY("baby"),

	/**
	 * Big slime/magma cube
	 */
	BIG("big"),

	/**
	 * Medium slime/magma cube
	 */
	MEDIUM("medium"),

	/**
	 * Small slime/magma cube
	 */
	SMALL("small");

	private final String subTypeName;

	EntitySubType(String subTypeName)
	{
		this.subTypeName = subTypeName;
	}

	/**
	 * @return The key this property is assigned to
	 */
	public String getName()
	{
		return this.subTypeName;
	}
}
