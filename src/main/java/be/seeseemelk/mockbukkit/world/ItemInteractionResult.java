package be.seeseemelk.mockbukkit.world;

public enum ItemInteractionResult
{
	SUCCESS,
	CONSUME,
	CONSUME_PARTIAL,
	PASS_TO_DEFAULT_BLOCK_INTERACTION,
	SKIP_DEFAULT_BLOCK_INTERACTION,
	FAIL;

	public boolean consumesAction() {
		return this.result().consumesAction();
	}

	public InteractionResult result() {
		return switch (this) {
			case SUCCESS -> InteractionResult.SUCCESS;
			case CONSUME -> InteractionResult.CONSUME;
			case CONSUME_PARTIAL -> InteractionResult.CONSUME_PARTIAL;
			case PASS_TO_DEFAULT_BLOCK_INTERACTION, SKIP_DEFAULT_BLOCK_INTERACTION -> InteractionResult.PASS;
			case FAIL -> InteractionResult.FAIL;
		};
	}
}
