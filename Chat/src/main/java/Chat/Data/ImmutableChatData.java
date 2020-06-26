package Chat.Data;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableChatData extends AbstractImmutableData<ImmutableChatData, ChatData>
{
	private Boolean worldChatEnabled;
	private Boolean tradeChatEnabled;
	private Boolean localChatEnabled;
	private String mode;
	
	ImmutableChatData(Boolean worldChatEnabled, Boolean tradeChatEnabled, Boolean localChatEnabled, String mode)
	{
		this.worldChatEnabled = worldChatEnabled;
		this.tradeChatEnabled = tradeChatEnabled;
		this.localChatEnabled = localChatEnabled;
		this.mode = mode;
		
		registerGetters();
	}
	
	@Override
	protected void registerGetters()
	{
		registerFieldGetter(ChatKeys.WORLD_ON, () -> this.worldChatEnabled);
		registerFieldGetter(ChatKeys.TRADE_ON, () -> this.tradeChatEnabled);
		registerFieldGetter(ChatKeys.LOCAL_ON, () -> this.localChatEnabled);
		registerFieldGetter(ChatKeys.MODE, () -> this.mode);
		
		registerKeyValue(ChatKeys.WORLD_ON, this::worldChatEnabled);
		registerKeyValue(ChatKeys.TRADE_ON, this::tradeChatEnabled);
		registerKeyValue(ChatKeys.LOCAL_ON, this::localChatEnabled);
		registerKeyValue(ChatKeys.MODE, this::mode);
	}
	
	public ImmutableValue<Boolean> worldChatEnabled()
	{
		return Sponge.getRegistry().getValueFactory().createValue(ChatKeys.WORLD_ON, worldChatEnabled).asImmutable();
	}
	
	public ImmutableValue<Boolean> tradeChatEnabled()
	{
		return Sponge.getRegistry().getValueFactory().createValue(ChatKeys.TRADE_ON, tradeChatEnabled).asImmutable();
	}
	
	public ImmutableValue<Boolean> localChatEnabled()
	{
		return Sponge.getRegistry().getValueFactory().createValue(ChatKeys.LOCAL_ON, localChatEnabled).asImmutable();
	}
	
	public ImmutableValue<String> mode()
	{
		return Sponge.getRegistry().getValueFactory().createValue(ChatKeys.MODE, mode).asImmutable();
	}
	
	@Override
	public ChatData asMutable()
	{
		return new ChatData(worldChatEnabled, tradeChatEnabled, localChatEnabled, mode);
	}
	
	@Override
	public int getContentVersion()
	{
		return 1;
	}
	
	// Writes data to NBT
	@Override
	public DataContainer toContainer()
	{
		return super.toContainer()
				.set(ChatKeys.WORLD_ON.getQuery(), this.worldChatEnabled)
				.set(ChatKeys.TRADE_ON.getQuery(), this.tradeChatEnabled)
				.set(ChatKeys.LOCAL_ON.getQuery(), this.localChatEnabled)
				.set(ChatKeys.MODE.getQuery(), this.mode);
	}
}
