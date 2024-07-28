package be.seeseemelk.mockbukkit.world;

public enum InteractionResult
{
	SUCCESS_ACTION,
	CONSUME_ACTION,
	CONSUME_PARTIAL_ACTION,
	PASS_ACTION,
	FAIL_ACTION;

	InteractionResult()
	{
	}

	public boolean consumesAction()
	{
		return this == SUCCESS_ACTION || this == CONSUME_ACTION || this == CONSUME_PARTIAL_ACTION;
	}

	public boolean shouldSwing()
	{
		return this == SUCCESS_ACTION;
	}

	public boolean shouldAwardStats()
	{
		return this == SUCCESS_ACTION || this == CONSUME_ACTION;
	}

	public static InteractionResult sidedSuccess(boolean var0)
	{
		return var0 ? SUCCESS_ACTION : CONSUME_ACTION;
	}
}
