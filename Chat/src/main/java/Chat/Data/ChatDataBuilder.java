package Chat.Data;

import java.util.Optional;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

public class ChatDataBuilder extends AbstractDataBuilder<ChatData> implements DataManipulatorBuilder<ChatData, ImmutableChatData>
{
	public ChatDataBuilder()
	{
		super(ChatData.class, 1);
	}
	
	@Override
	public ChatData create()
	{
		return new ChatData(true, true, true, "Local");
	}
	
	@Override
	public Optional<ChatData> createFrom(DataHolder dataHolder)
	{
		return create().fill(dataHolder);
	}
	
	@Override
	protected Optional<ChatData> buildContent(DataView container) throws InvalidDataException
	{
		return create().from(container);
	}
}
