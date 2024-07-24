package be.seeseemelk.mockbukkit.world;

public enum EnumInteractionResult {
	SUCCESS,
	CONSUME,
	CONSUME_PARTIAL,
	PASS,
	FAIL;

	private EnumInteractionResult() {
	}

	public boolean consumesAction() {
		return this == SUCCESS || this == CONSUME || this == CONSUME_PARTIAL;
	}

	public boolean shouldSwing() {
		return this == SUCCESS;
	}

	public boolean shouldAwardStats() {
		return this == SUCCESS || this == CONSUME;
	}

	public static EnumInteractionResult sidedSuccess(boolean var0) {
		return var0 ? SUCCESS : CONSUME;
	}
}
