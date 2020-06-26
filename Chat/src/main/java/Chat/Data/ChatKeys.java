package Chat.Data;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.util.TypeTokens;

public class ChatKeys
{
	private ChatKeys() {}
	public static void dummy() {} // invoke static constructor
	public static final Key<Value<Boolean>> WORLD_ON;
	public static final Key<Value<Boolean>> TRADE_ON;
	public static final Key<Value<Boolean>> LOCAL_ON;
	public static final Key<Value<String>> MODE;
	
	static 
	{
		WORLD_ON = Key.builder()
				.type(TypeTokens.BOOLEAN_VALUE_TOKEN)
				.id("Chat:WORLD_ON")
				.name("World On")
				.query(DataQuery.of('.', "chat.worldon"))
				.build();
		TRADE_ON = Key.builder()
				.type(TypeTokens.BOOLEAN_VALUE_TOKEN)
				.id("Chat:TRADE_ON")
				.name("Trade On")
				.query(DataQuery.of('.', "chat.tradeon"))
				.build();
		LOCAL_ON = Key.builder()
				.type(TypeTokens.BOOLEAN_VALUE_TOKEN)
				.id("Chat:LOCAL_ON")
				.name("Local On")
				.query(DataQuery.of('.', "chat.localon"))
				.build();
		MODE = Key.builder()
				.type(TypeTokens.STRING_VALUE_TOKEN)
				.id("Chat:MODE")
				.name("Mode")
				.query(DataQuery.of('.', "chat.mode"))
				.build();
	}
}
