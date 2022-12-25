package be.seeseemelk.mockbukkit.entity.data;

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
	
	
	private String subTypeName;

	EntitySubType(String subTypeName){
		this.subTypeName = subTypeName;
	}
	
	public String getName() {
		return this.subTypeName;
	}
}
