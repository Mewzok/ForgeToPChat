package Chat.system;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import Chat.Main;
import Chat.Data.ChatData;
import Chat.Data.ChatKeys;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

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
	
	public static Text serverChatState()
	{
		Text msg = Text.of("Local chat: \n\tDistance: " + Main.confLocalDist + " blocks\n\tCooldown: " + Main.confLocalCD + " seconds\n\tCost: $" + Main.confLocalCost
				+ " per message\n"
				+ "Trade chat: \n\tCooldown: " + Main.confTradeCD + " seconds.\n\tCost: $" + Main.confTradeCost + " per message\n"
				+ "World chat: \n\tCooldown: " + Main.confWorldCD + " seconds.\n\tCost: $" + Main.confWorldCost + " per message");
		
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
	
	// Cooldown manager begin
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
	// Cooldown manager end
	
	// Cost manager begin
	public static TransactionResult MessageCost(EconomyService economyService, Player player, BigDecimal amount)
	{
		Optional<UniqueAccount> uOpt = economyService.getOrCreateAccount(player.getUniqueId());
		if(uOpt.isPresent())
		{
			UniqueAccount acc = uOpt.get();
			
			TransactionResult result = acc.withdraw(economyService.getDefaultCurrency(), amount, Sponge.getCauseStackManager().getCurrentCause());
			return result;
		}
		return null;
	}
	// Cost manager end
	
	// Config handler begin
	public static Config ConfigHandler(Game game, PluginContainer plugin, Config config, Path path, ConfigurationLoader<CommentedConfigurationNode> loader,
			Logger logger) throws IOException, ObjectMappingException
	{
		Asset conf = game.getAssetManager().getAsset(plugin, "config.conf").get();
		ConfigurationNode root;
		try 
		{
			// Creates config directory
			if(!Files.exists(path))
			{
				conf.copyToFile(path);
			}
			root = loader.load();
			if (root.getNode("version").getInt() < 4)
			{
				root.mergeValuesFrom(loadDefault(game, plugin, loader));
				root.getNode("version").setValue(4);
				loader.save(root);
			}
			config = root.getValue(Config.type);
			
			return config;
		} catch(IOException ex)
		{
			logger.error("Error loading config.");
			throw ex;
		} catch (ObjectMappingException ex) 
		{
            logger.error("Invalid config file.");
            mapDefault(config, game, logger, plugin, loader);
            throw ex;
        }
	}
	
    public static void mapDefault(Config config, Game game, Logger logger, PluginContainer plugin, ConfigurationLoader<CommentedConfigurationNode> loader)
    		throws IOException, ObjectMappingException 
    {
        try 
        {
            config = loadDefault(game, plugin, loader).getValue(Config.type);
        } catch (IOException | ObjectMappingException ex) 
        {
            logger.error("Could not load the embedded default config. Disabling plugin.");
            game.getEventManager().unregisterPluginListeners(plugin);
            throw ex;
        } 
    }
	
	public static ConfigurationNode loadDefault(Game game, PluginContainer plugin, ConfigurationLoader<CommentedConfigurationNode> loader) throws IOException
	{
		return HoconConfigurationLoader.builder().setURL(game.getAssetManager().getAsset(plugin, "config.conf").get().getUrl()).build().load(loader.getDefaultOptions());
	}
	// Config handler end
	
	//Cost handler begin
	public static void testForEco()
	{
		Optional<EconomyService> serviceOpt = Sponge.getServiceManager().provide(EconomyService.class);
		if(!serviceOpt.isPresent())
		{
			System.out.println("No economy plugin found. Chat costs disabled.");
		} else
			System.out.println("Economy plugin found. Chat costs enabled.");
	}
	//Cost handler end
}
