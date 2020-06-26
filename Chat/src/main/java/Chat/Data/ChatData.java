package Chat.Data;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

public class ChatData extends AbstractData<ChatData, ImmutableChatData>
{
	private Boolean worldChatEnabled;
	private Boolean tradeChatEnabled;
	private Boolean localChatEnabled;
	private String mode;
	
	ChatData(Boolean worldChatEnabled, Boolean tradeChatEnabled, Boolean localChatEnabled, String mode)
	{
		this.worldChatEnabled = worldChatEnabled;
		this.tradeChatEnabled = tradeChatEnabled;
		this.localChatEnabled = localChatEnabled;
		this.mode = mode;
		
		registerGettersAndSetters();
	}
	
	@Override
	protected void registerGettersAndSetters()
	{
		registerFieldGetter(ChatKeys.WORLD_ON, () -> this.worldChatEnabled);
		registerFieldGetter(ChatKeys.TRADE_ON, () -> this.tradeChatEnabled);
		registerFieldGetter(ChatKeys.LOCAL_ON, () -> this.localChatEnabled);
		registerFieldGetter(ChatKeys.MODE, () -> this.mode);
		
		registerFieldSetter(ChatKeys.WORLD_ON, worldChatEnabled -> this.worldChatEnabled = worldChatEnabled);
		registerFieldSetter(ChatKeys.TRADE_ON, tradeChatEnabled -> this.tradeChatEnabled = tradeChatEnabled);
		registerFieldSetter(ChatKeys.LOCAL_ON, localChatEnabled -> this.localChatEnabled = localChatEnabled);
		registerFieldSetter(ChatKeys.MODE, mode -> this.mode = mode);
		
		registerKeyValue(ChatKeys.WORLD_ON, this::worldChatEnabled);
		registerKeyValue(ChatKeys.TRADE_ON, this::tradeChatEnabled);
		registerKeyValue(ChatKeys.LOCAL_ON, this::localChatEnabled);
		registerKeyValue(ChatKeys.MODE, this::mode);
	}
	
	public Value<Boolean> worldChatEnabled()
	{
		return Sponge.getRegistry().getValueFactory().createValue(ChatKeys.WORLD_ON, worldChatEnabled);
	}
	
	public Value<Boolean> tradeChatEnabled()
	{
		return Sponge.getRegistry().getValueFactory().createValue(ChatKeys.TRADE_ON, tradeChatEnabled);
	}
	
	public Value<Boolean> localChatEnabled()
	{
		return Sponge.getRegistry().getValueFactory().createValue(ChatKeys.LOCAL_ON, localChatEnabled);
	}
	
	public Value<String> mode()
	{
		return Sponge.getRegistry().getValueFactory().createValue(ChatKeys.MODE, mode);
	}
	
	@Override
	public Optional<ChatData> fill(DataHolder dataHolder, MergeFunction overlap)
	{
		Optional<ChatData> otherData_ = dataHolder.get(ChatData.class);
		if(otherData_.isPresent())
		{
			ChatData otherData = otherData_.get();
			ChatData finalData = overlap.merge(this, otherData);
			this.worldChatEnabled = finalData.worldChatEnabled;
			this.tradeChatEnabled = finalData.tradeChatEnabled;
			this.localChatEnabled = finalData.localChatEnabled;
			this.mode = finalData.mode;
		}
		return Optional.of(this);
	}
	
	@Override
	public Optional<ChatData> from(DataContainer container)
	{
		return from((DataView) container);
	}
	
	public Optional<ChatData> from(DataView view)
	{
		if(view.contains(ChatKeys.WORLD_ON.getQuery()) && view.contains(ChatKeys.TRADE_ON.getQuery())
				&& view.contains(ChatKeys.LOCAL_ON.getQuery()))
		{
			this.worldChatEnabled = view.getBoolean(ChatKeys.WORLD_ON.getQuery()).get();
			this.tradeChatEnabled = view.getBoolean(ChatKeys.TRADE_ON.getQuery()).get();
			this.localChatEnabled = view.getBoolean(ChatKeys.LOCAL_ON.getQuery()).get();
			this.mode = view.getString(ChatKeys.MODE.getQuery()).get();
			return Optional.of(this);
		} else
		{
			return Optional.empty();
		}
	}
	
	@Override
	public ChatData copy()
	{
		return new ChatData(this.worldChatEnabled, this.tradeChatEnabled, this.localChatEnabled, this.mode);
	}
	
	@Override
	public ImmutableChatData asImmutable()
	{
		return new ImmutableChatData(this.worldChatEnabled, this.tradeChatEnabled, this.localChatEnabled, this.mode);
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