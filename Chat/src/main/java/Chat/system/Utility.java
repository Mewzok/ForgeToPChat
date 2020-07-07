package Chat.system;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import Chat.Main;
import Chat.Data.ChatData;
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
	
	public static Collection<Entity> getEntities(final Player player, int radius)
	{
		return player.getLocation().getExtent().getEntities(new Predicate<Entity>() { @Override public boolean test(Entity entity) {
						return entity.getLocation().getPosition().distance(player.getLocation().getPosition()) <= radius;}});
	}
	
	public static void onJoinCreation(Player player)
	{
		Text chatStatsMsg;
		// Instantiate or load chat data
		player.offer(player.getOrCreate(ChatData.class).get());
		
		// Instantiate player's chat mode
		if(player.get(ChatKeys.MODE).get().equals("World"))
		{
			player.offer(ChatKeys.MODE, "World");
		} else if(player.get(ChatKeys.MODE).get().equals("Trade"))
		{
			player.offer(ChatKeys.MODE, "Trade");
		} else if(player.get(ChatKeys.MODE).get().equals("Local"))
		{
			player.offer(ChatKeys.MODE, "Local");
		} else
			player.offer(ChatKeys.MODE, "Local");
		
		// Send player chat status on join
		chatStatsMsg = Utility.chatStateBuilder(player);
		player.sendMessage(chatStatsMsg);
		
		// Instantiate player's chat toggles
		boolean worldon = player.get(ChatKeys.WORLD_ON).get().booleanValue();
		boolean tradeon = player.get(ChatKeys.TRADE_ON).get().booleanValue();
		boolean localon = player.get(ChatKeys.LOCAL_ON).get().booleanValue();
		
		if(worldon == true)
		{
			Main.wmci.addMember(player);
		} else if(worldon == false)
		{
			Main.wmci.removeMember(player);
		}
		
		if(tradeon == true)
		{
			Main.tmci.addMember(player);
		} else if(tradeon == false)
		{
			Main.tmci.removeMember(player);
		}
		
		if(localon == true)
		{
			Main.lmci.addMember(player);
		} else if(localon == false)
		{
			Main.lmci.removeMember(player);
		}
	}
	
	public static String CommandCooldown(Player player, HashMap<Player, Long> cooldown, long cdSeconds)
	{
		int minuteCount = 0;
		long secondsRem = 0;
		String msg = "";
		
		if(cooldown.containsKey(player))
		{
			long playerCD = cooldown.get(player) / 1000;
			long curTime = System.currentTimeMillis() / 1000;
			
			if(playerCD + cdSeconds > curTime)
			{
				secondsRem = playerCD + cdSeconds - curTime;
				
				while(secondsRem >= 60)
				{
					minuteCount++;
					secondsRem -= 60;
				}
				// No minutes left, some seconds remaining
				if(minuteCount == 0 && secondsRem > 0)
				{
					if(secondsRem == 1)
						msg = "You must wait " + secondsRem + " second to use this channel again.";
					else
						msg = "You must wait " + secondsRem + " seconds to use this channel again.";
				} else if (minuteCount > 0 && secondsRem > 0)
				{ // Some minutes left, some seconds remaining
					if(minuteCount == 1 && secondsRem != 1)
						msg = "You must wait " + minuteCount + " minute and " + secondsRem + " seconds to use this channel again.";
					else if(minuteCount == 1 && secondsRem == 1)
						msg = "You must wait " + minuteCount + " minute and " + secondsRem + " second to use this channel again.";
					else if(minuteCount != 1 && secondsRem == 1)
						msg = "You must wait " + minuteCount + " minutes and " + secondsRem + " second to use this channel again.";
					else
						msg = "You must wait " + minuteCount + " minutes and " + secondsRem + " seconds to use this channel again.";
				} else if (minuteCount > 0 && secondsRem == 0)
				{ // Some minutes left, no seconds remaining
					if(minuteCount == 1)
						msg = "You must wait " + minuteCount + " minute to use this channel again.";
					else
						msg = "You must wait " + minuteCount + " minutes to use this channel again.";
				} else
				{ // No minutes left, no seconds remaining
					cooldown.remove(player);
					cooldown.put(player, System.currentTimeMillis());
					return null;
				}
				return msg;
			} else
			{
				cooldown.remove(player);
				cooldown.put(player, System.currentTimeMillis());
				return null;
			}
		} else
		{
			cooldown.put(player, System.currentTimeMillis());
			return null;
		}
	}
}
