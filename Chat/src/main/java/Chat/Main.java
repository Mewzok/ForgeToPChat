package Chat;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.advancement.AdvancementEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.living.humanoid.player.KickPlayerEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.data.Has;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameAboutToStartServerEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.text.Text;

import com.google.inject.Inject;

import Chat.Commands.ChatSettings;
import Chat.Commands.SetAllChatViewOff;
import Chat.Commands.SetAllChatViewOn;
import Chat.Commands.SetLocalChatViewOff;
import Chat.Commands.SetLocalChatViewOn;
import Chat.Commands.SetTradeChatViewOff;
import Chat.Commands.SetTradeChatViewOn;
import Chat.Commands.SetWorldChatViewOff;
import Chat.Commands.SetWorldChatViewOn;
import Chat.Commands.ViewChatStats;
import Chat.Commands.ChatInfo.ChatInfo;
import Chat.Commands.ChatInfo.ChatInfoLocal;
import Chat.Commands.ChatInfo.ChatInfoSystem;
import Chat.Commands.ChatInfo.ChatInfoTrade;
import Chat.Commands.ChatInfo.ChatInfoWorld;
import Chat.Commands.SetMode.ChatHelp;
import Chat.Commands.SetMode.SetMode;
import Chat.Commands.SetMode.SetModeLocal;
import Chat.Commands.SetMode.SetModeSystem;
import Chat.Commands.SetMode.SetModeTrade;
import Chat.Commands.SetMode.SetModeWorld;
import Chat.Data.ChatData;
import Chat.Data.ChatDataBuilder;
import Chat.Data.ChatKeys;
import Chat.Data.ImmutableChatData;
import Chat.system.Config;
import Chat.system.Utility;
import Chat.system.channels.LocalMessageChannelInput;
import Chat.system.channels.SystemMessageChannelInput;
import Chat.system.channels.TradeMessageChannelInput;
import Chat.system.channels.WorldMessageChannelInput;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

@Plugin(id = "topchat", name = "Chat", version = "1.0", description = "Chat")
public class Main
{
	@Inject
	PluginContainer container;
	
	private static Logger logger;
	public static WorldMessageChannelInput wmci = new WorldMessageChannelInput();
	public static TradeMessageChannelInput tmci = new TradeMessageChannelInput();
	public static LocalMessageChannelInput lmci = new LocalMessageChannelInput();
	public static SystemMessageChannelInput smci = new SystemMessageChannelInput();
	
	public static int confLocalDist;
	public static long confLocalCD;
	public static BigDecimal confLocalCost;
	public static long confTradeCD;
	public static BigDecimal confTradeCost;
	public static long confWorldCD;
	public static BigDecimal confWorldCost;
	
	public static EconomyService economyService;
	
	public static Logger getLogger()
	{
		return logger;
	}
	
	@Inject
	Game game;
	
	
// Configuration items
	@Inject @DefaultConfig(sharedRoot = true)
	private Path path;
	
	@Inject @DefaultConfig(sharedRoot = true)
	ConfigurationLoader<CommentedConfigurationNode> loader;
	Config config;
// End of configuration items
	// SetAllChatViewOn
	CommandSpec acviCommandSpec = CommandSpec.builder()
			.permission("topchat.command.setallchatviewon")
			.description(Text.of("Enables the viewing of all chat channels."))
			.executor(new SetAllChatViewOn())
			.build();
	// SetAllChatViewOff
	CommandSpec acvoCommandSpec = CommandSpec.builder()
			.permission("topchat.command.setallchatviewoff")
			.description(Text.of("Disables the viewing of all chat channels."))
			.executor(new SetAllChatViewOff())
			.build();
	// SetWorldChatViewOn
	CommandSpec wcviCommandSpec = CommandSpec.builder()
			.permission("topchat.command.setworldchatviewon")
			.description(Text.of("Enables the viewing of world chat."))
			.executor(new SetWorldChatViewOn())
			.build();
	// SetWorldChatViewOff
	CommandSpec wcvoCommandSpec = CommandSpec.builder()
			.permission("topchat.command.setworldchatviewoff")
			.description(Text.of("Disables the viewing of world chat."))
			.executor(new SetWorldChatViewOff())
			.build();
	// SetTradeChatViewOn
	CommandSpec tcviCommandSpec = CommandSpec.builder()
			.permission("topchat.command.settradechatviewon")
			.description(Text.of("Enables the viewing of trade chat."))
			.executor(new SetTradeChatViewOn())
			.build();
	// SetTradeChatViewOff
	CommandSpec tcvoCommandSpec = CommandSpec.builder()
			.permission("topchat.command.settradechatviewoff")
			.description(Text.of("Disables the viewing of trade chat."))
			.executor(new SetTradeChatViewOff())
			.build();
	// SetTradeChatViewOn
	CommandSpec lcviCommandSpec = CommandSpec.builder()
			.permission("topchat.command.setlocalchatviewon")
			.description(Text.of("Enables the viewing of local chat."))
			.executor(new SetLocalChatViewOn())
			.build();
	// SetLocalChatViewOff
	CommandSpec lcvoCommandSpec = CommandSpec.builder()
			.permission("topchat.command.setlocalchatviewoff")
			.description(Text.of("Disables the viewing of local chat."))
			.executor(new SetLocalChatViewOff())
			.build();
	// ViewChatStats
	CommandSpec vcsCommandSpec = CommandSpec.builder()
			.permission("topchat.command.viewchatstats")
			.description(Text.of("Displays your current chat status."))
			.executor(new ViewChatStats())
			.build();
	// SetModeWorld
	CommandSpec smwCommandSpec = CommandSpec.builder()
			.permission("topchat.command.setmodeworld")
			.description(Text.of("Sets the current chat mode to world chat."))
			.executor(new SetModeWorld())
			.build();
	// SetModeTrade
	CommandSpec smtCommandSpec = CommandSpec.builder()
			.permission("topchat.command.setmodetrade")
			.description(Text.of("Sets the current chat mode to trade chat."))
			.executor(new SetModeTrade())
			.build();
	// SetModeLocal
	CommandSpec smlCommandSpec = CommandSpec.builder()
			.permission("topchat.command.setmodelocal")
			.description(Text.of("Sets the current chat mode to local chat."))
			.executor(new SetModeLocal())
			.build();
	// SetModeSystem
	CommandSpec smsCommandSpec = CommandSpec.builder()
			.permission("topchat.command.setmodesystem")
			.description(Text.of("Sets the current chat mode to system chat."))
			.executor(new SetModeSystem())
			.build();
	// ChatHelp
	CommandSpec tchCommandSpec = CommandSpec.builder()
			.permission("topchat.command.chathelp")
			.description(Text.of("Displays all ToPChat commands."))
			.executor(new ChatHelp())
			.build();
	// chat info local
	CommandSpec cilCommandSpec = CommandSpec.builder()
			.permission("topchat.command.chatinfolocal")
			.description(Text.of("Displays information about local chat."))
			.executor(new ChatInfoLocal())
			.build();
	// chat info trade
	CommandSpec citCommandSpec = CommandSpec.builder()
			.permission("topchat.command.chatinfotrade")
			.description(Text.of("Displays information about trade chat."))
			.executor(new ChatInfoTrade())
			.build();
	// chat info world
	CommandSpec ciwCommandSpec = CommandSpec.builder()
			.permission("topchat.command.chatinfoworld")
			.description(Text.of("Displays information about world chat."))
			.executor(new ChatInfoWorld())
			.build();
	// chat info system
	CommandSpec cisCommandSpec = CommandSpec.builder()
			.permission("topchat.command.chatinfosystem")
			.description(Text.of("Displays information about system chat."))
			.executor(new ChatInfoSystem())
			.build();
	// chat info
	CommandSpec ciCommandSpec = CommandSpec.builder()
			.permission("topchat.command.chatinfo")
			.description(Text.of("Displays general information about ToPChat."))
			.child(cilCommandSpec, "local", "l")
			.child(citCommandSpec, "trade", "l")
			.child(ciwCommandSpec, "world", "w")
			.child(cisCommandSpec, "system", "s")
			.executor(new ChatInfo())
			.build();
	// SetMode
	CommandSpec smCommandSpec = CommandSpec.builder()
			.permission("topchat.command.setmode")
			.description(Text.of("Sets the current chat mode or provides help."))
			.child(smwCommandSpec, "world", "w")
			.child(smtCommandSpec, "trade", "t")
			.child(smlCommandSpec, "local", "l")
			.child(smsCommandSpec, "system", "s")
			.child(tchCommandSpec, "help", "chathelp", "topchathelp")
			.child(ciCommandSpec, "info", "information")
			.executor(new SetMode())
			.build();
	// TopChatReload
	CommandSpec tcrCommandSpec = CommandSpec.builder()
			.permission("topchat.command.topchatreload")
			.description(Text.of("Reloads the ToPChat config."))
			.executor(new TopChatReload())
			.build();
	// ViewServerChatStats
	CommandSpec csCommandSpec = CommandSpec.builder()
			.permission("topchat.command.chatsettings")
			.description(Text.of("Displays the server's current chat settings."))
			.executor(new ChatSettings())
			.build();
	
	@Listener
	public void preInit(GamePreInitializationEvent e) throws IOException, ObjectMappingException
	{	
		ChatKeys.dummy();
		DataRegistration.builder()
		.name("Chat Data")
		.id("chat_data")
		.dataClass(ChatData.class)
		.immutableClass(ImmutableChatData.class)
		.builder(new ChatDataBuilder())
		.build();
		
		config = Utility.ConfigHandler(game, container, config, path, loader, logger);
		
		confLocalDist = config.localInfo.distance;
		confLocalCD = config.localInfo.cooldown;
		confLocalCost = BigDecimal.valueOf(config.localInfo.cost).setScale(2, RoundingMode.HALF_UP);
		confTradeCD = config.tradeInfo.cooldown;
		confTradeCost = BigDecimal.valueOf(config.tradeInfo.cost).setScale(2, RoundingMode.HALF_UP);
		confWorldCD = config.worldInfo.cooldown;
		confWorldCost = BigDecimal.valueOf(config.worldInfo.cost).setScale(2, RoundingMode.HALF_UP);
	}
	
	@Listener
	public void init(GameInitializationEvent e)
	{
		// Commands
		Sponge.getCommandManager().register(this, acviCommandSpec, "allchaton", "acon", "allchat", "ac");
		Sponge.getCommandManager().register(this, acvoCommandSpec, "allchatoff", "acoff");
		Sponge.getCommandManager().register(this, wcviCommandSpec, "worldchaton", "wcon", "worldchat", "wc");
		Sponge.getCommandManager().register(this, wcvoCommandSpec, "worldchatoff", "wcoff");
		Sponge.getCommandManager().register(this, tcviCommandSpec, "tradechaton", "tcon", "tradechat", "tc");
		Sponge.getCommandManager().register(this, tcvoCommandSpec, "tradechatoff", "tcoff");
		Sponge.getCommandManager().register(this, lcviCommandSpec, "localchaton", "lcon", "localchat", "lc");
		Sponge.getCommandManager().register(this, lcvoCommandSpec, "localchatoff", "lcoff");
		Sponge.getCommandManager().register(this, vcsCommandSpec, "chatstats", "viewchatstats", "chats");
		Sponge.getCommandManager().register(this, smCommandSpec, "mode", "cm", "chat");
		Sponge.getCommandManager().register(this, tcrCommandSpec, "topchatreload", "tcr", "chatreload");
		Sponge.getCommandManager().register(this, csCommandSpec, "chatsettings", "cs", "serverchat");
	}
	
	@Listener
	public void onGameAboutToStartServer(GameAboutToStartServerEvent e)
	{
		Utility.testForEco();
	}
	
	@Listener
	public void onFirstJoin(ClientConnectionEvent.Join e, @Getter("getTargetEntity") @Has(value = ChatData.class, inverse = true) Player player)
	{
		Utility.onJoinCreation(player);
	}
	
	@Listener
	public void onReturningJoin(ClientConnectionEvent.Join e, @Getter("getTargetEntity") @Has(value = ChatData.class, inverse = false) Player player)
	{	
		Utility.onJoinCreation(player);
		
		smci.addMember(player);
	} 
	
	@Listener
	public void onChangeServiceProvider(ChangeServiceProviderEvent e)
	{
		if(e.getService().equals(EconomyService.class))
		{
			economyService = (EconomyService) e.getNewProviderRegistration().getProvider();
		}
	}
	
	@Listener
	public void messageSent(MessageChannelEvent.Chat e)
	{
		Boolean success = false;
		if(e.getSource() instanceof Player)
		{
			Player player = (Player) e.getSource();
			
			if (player.get(ChatKeys.MODE).get().equals("World"))
			{
				success = Utility.checkWorldConditions(((Player)e.getSource()));
				if(success == true)
					e.setChannel(wmci);
				else
					e.setMessageCancelled(true);
			} else if (player.get(ChatKeys.MODE).get().equals("Trade"))
			{
				success = Utility.checkTradeConditions(((Player)e.getSource()));
				if(success == true)
					e.setChannel(tmci);
				else
					e.setMessageCancelled(true);
			} else if (player.get(ChatKeys.MODE).get().equals("Local"))
			{
				success = Utility.checkLocalConditions(((Player)e.getSource()));
				if(success == true)
					e.setChannel(lmci);
				else
					e.setMessageCancelled(true);
			} else if (player.get(ChatKeys.MODE).get().equals("System"))
			{
				e.setChannel(smci);
			} else
				e.setChannel(null);
		} else
			e.setChannel(smci);
	}
	
	@Listener
	public void messageSent(AdvancementEvent.Grant e)
	{
		e.setChannel(smci);
	}
	
	@Listener
	public void messageSent(ClientConnectionEvent.Disconnect e)
	{
		e.setChannel(smci);
	}
	
	@Listener
	public void messageSent(ClientConnectionEvent.Join e)
	{
		e.setChannel(smci);
	}
	
	@Listener
	public void messageSent(DestructEntityEvent e)
	{
		e.setChannel(smci);
	}
	
	@Listener
	public void messageSent(DestructEntityEvent.Death e)
	{
		e.setChannel(smci);
	}
	
	@Listener
	public void messageSent(KickPlayerEvent e)
	{
		e.setChannel(smci);
	}
	
	@Listener
	public void onReload(GameReloadEvent e) throws IOException, ObjectMappingException
	{
		try 
		{
			config = loader.load().getValue(Config.type);
		} catch (IOException ex)
		{
			logger.error("Invalid config.");
			throw ex;
		}
	}
	
	// Chat reload command
	public class TopChatReload implements CommandExecutor
	{

		@Override
		public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
		{
			if(src instanceof Player)
			{
				if(((Player)src).hasPermission("topchat.command.topchatreload"))
				{
					try
					{
						config = Utility.ConfigHandler(game, container, config, path, loader, logger);
						
						confLocalDist = config.localInfo.distance;
						confLocalCD = config.localInfo.cooldown;
						confLocalCost = BigDecimal.valueOf(config.localInfo.cost).setScale(2, RoundingMode.HALF_UP);
						confTradeCD = config.tradeInfo.cooldown;
						confTradeCost = BigDecimal.valueOf(config.tradeInfo.cost).setScale(2, RoundingMode.HALF_UP);
						confWorldCD = config.worldInfo.cooldown;
						confWorldCost = BigDecimal.valueOf(config.worldInfo.cost).setScale(2, RoundingMode.HALF_UP);
						
						src.sendMessage(Text.of("ToPChat config reloaded."));
					} catch (IOException | ObjectMappingException e)
					{
						logger.error("Failed to reload config.");
						e.printStackTrace();
					}
					return CommandResult.success();
				}
			} else
			{
				try
				{
					config = Utility.ConfigHandler(game, container, config, path, loader, logger);
					
					confLocalDist = config.localInfo.distance;
					confLocalCD = config.localInfo.cooldown;
					confLocalCost = BigDecimal.valueOf(config.localInfo.cost).setScale(2, RoundingMode.HALF_UP);
					confTradeCD = config.tradeInfo.cooldown;
					confTradeCost = BigDecimal.valueOf(config.tradeInfo.cost).setScale(2, RoundingMode.HALF_UP);
					confWorldCD = config.worldInfo.cooldown;
					confWorldCost = BigDecimal.valueOf(config.worldInfo.cost).setScale(2, RoundingMode.HALF_UP);
					
					src.sendMessage(Text.of("ToPChat config reloaded."));
				} catch (IOException | ObjectMappingException e)
				{
					logger.error("Failed to reload config.");
					e.printStackTrace();
				}
				return CommandResult.success();
			}
			return CommandResult.success();
		}
	} // Chat reload command end
}
