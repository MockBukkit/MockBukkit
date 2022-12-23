package be.seeseemelk.mockbukkit.entity.data;

public enum EntitySubType
{
	DEFAULT("default"),  BABY("baby");
	
	
	private String subTypeName;

	EntitySubType(String subTypeName){
		this.subTypeName = subTypeName;
	}
	
	public String getName() {
		return this.subTypeName;
	}
}
