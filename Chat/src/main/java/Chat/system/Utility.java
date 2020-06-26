package Chat.system;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import Chat.Data.ChatKeys;

public class Utility
{
	public static Text chatStateBuilder(Player player)
	{
		Text msg;
		Text worldState;
		Text tradeState;
		Text localState;
		Text mode;
		
		if(player.get(ChatKeys.WORLD_ON).get().booleanValue() == true)
		{
			worldState = Text.of(TextColors.GREEN, TextStyles.ITALIC, "on");
		} else if(player.get(ChatKeys.WORLD_ON).get().booleanValue()==false)
		{
			worldState = Text.of(TextColors.RED, TextStyles.ITALIC, "off");
		} else
			worldState = Text.of(TextColors.LIGHT_PURPLE, TextStyles.ITALIC, "unknown");
		
		if(player.get(ChatKeys.TRADE_ON).get().booleanValue() == true)
		{
			tradeState = Text.of(TextColors.GREEN, TextStyles.ITALIC, "on");
		} else if(player.get(ChatKeys.TRADE_ON).get().booleanValue()==false)
		{
			tradeState = Text.of(TextColors.RED, TextStyles.ITALIC, "off");
		} else
			tradeState = Text.of(TextColors.LIGHT_PURPLE, TextStyles.ITALIC, "unknown");
		
		if(player.get(ChatKeys.LOCAL_ON).get().booleanValue() == true)
		{
			localState = Text.of(TextColors.GREEN, TextStyles.ITALIC, "on");
		} else if(player.get(ChatKeys.LOCAL_ON).get().booleanValue()==false)
		{
			localState = Text.of(TextColors.RED, TextStyles.ITALIC, "off");
		} else
			localState = Text.of(TextColors.LIGHT_PURPLE, TextStyles.ITALIC, "unknown");
		
		mode = Text.of(TextColors.BLUE, TextStyles.BOLD, player.get(ChatKeys.MODE).get());
		
		msg = Text.of("World  chat: ", worldState, "\nTrade chat: ", tradeState, "\nLocal  chat: ", localState,
				"\nCurrent chat mode: ", mode);
		
		return msg;
	}
	
	public static String ChatDefinition(MessageChannelEvent.Chat e)
	{
		Optional<String> mode;
		String msgType = "Unknown Type";
		if(e.getSource() instanceof Player)
		{
			Player player = (Player)e.getSource();
			
			mode = player.get(ChatKeys.MODE);
			
			if(mode.toString() == "World")
			{
				msgType = "World";
			} else if(mode.toString() == "Trade")
			{
				msgType = "Trade";
			} else if(mode.toString() == "Local")
			{
				msgType = "Local";
			} else
			{
				msgType = "Unknown";
			}
		}
		return msgType;
	}
	
	public static void CheckBlockMessageType(String msgType, MessageChannelEvent.Chat e)
	{
		
	}
}
