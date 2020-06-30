package Chat;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.logging.Logger;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.advancement.AdvancementEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.living.humanoid.player.KickPlayerEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.data.Has;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.world.Location;

import com.google.common.base.Predicate;
import com.google.inject.Inject;

import Chat.Commands.SetLocalChatViewOff;
import Chat.Commands.SetLocalChatViewOn;
import Chat.Commands.SetTradeChatViewOff;
import Chat.Commands.SetTradeChatViewOn;
import Chat.Commands.SetWorldChatViewOff;
import Chat.Commands.SetWorldChatViewOn;
import Chat.Commands.ViewChatStats;
import Chat.Commands.SetMode.SetMode;
import Chat.Commands.SetMode.SetModeLocal;
import Chat.Commands.SetMode.SetModeSystem;
import Chat.Commands.SetMode.SetModeTrade;
import Chat.Commands.SetMode.SetModeWorld;
import Chat.Data.ChatData;
import Chat.Data.ChatDataBuilder;
import Chat.Data.ChatKeys;
import Chat.Data.ImmutableChatData;
import Chat.system.Utility;
import Chat.system.channels.LocalMessageChannelInput;
import Chat.system.channels.SystemMessageChannelInput;
import Chat.system.channels.TradeMessageChannelInput;
import Chat.system.channels.WorldMessageChannelInput;

@Plugin(id = "chat", name = "Chat", version = "1.0", description = "Chat")
public class Main
{
	public static WorldMessageChannelInput wmci = new WorldMessageChannelInput();
	public static TradeMessageChannelInput tmci = new TradeMessageChannelInput();
	public static LocalMessageChannelInput lmci = new LocalMessageChannelInput();
	public static SystemMessageChannelInput smci = new SystemMessageChannelInput();
	
	@Inject
	PluginContainer container;
	
	CommandSpec wcviCommandSpec = CommandSpec.builder()
			.description(Text.of("Enables the viewing of world chat."))
			.executor(new SetWorldChatViewOn())
			.build();
	CommandSpec wcvoCommandSpec = CommandSpec.builder()
			.description(Text.of("Disables the viewing of world chat."))
			.executor(new SetWorldChatViewOff())
			.build();
	CommandSpec tcviCommandSpec = CommandSpec.builder()
			.description(Text.of("Enables the viewing of trade chat."))
			.executor(new SetTradeChatViewOn())
			.build();
	CommandSpec tcvoCommandSpec = CommandSpec.builder()
			.description(Text.of("Disables the viewing of trade chat."))
			.executor(new SetTradeChatViewOff())
			.build();
	CommandSpec lcviCommandSpec = CommandSpec.builder()
			.description(Text.of("Enables the viewing of local chat."))
			.executor(new SetLocalChatViewOn())
			.build();
	CommandSpec lcvoCommandSpec = CommandSpec.builder()
			.description(Text.of("Disables the viewing of local chat."))
			.executor(new SetLocalChatViewOff())
			.build();
	CommandSpec vcsCommandSpec = CommandSpec.builder()
			.description(Text.of("Views current chat status."))
			.executor(new ViewChatStats())
			.build();
	CommandSpec smwCommandSpec = CommandSpec.builder()
			.description(Text.of("Sets the current chat mode to world chat."))
			.executor(new SetModeWorld())
			.build();
	CommandSpec smtCommandSpec = CommandSpec.builder()
			.description(Text.of("Sets the current chat mode to trade chat."))
			.executor(new SetModeTrade())
			.build();
	CommandSpec smlCommandSpec = CommandSpec.builder()
			.description(Text.of("Sets the current chat mode to local chat."))
			.executor(new SetModeLocal())
			.build();
	CommandSpec smsCommandSpec = CommandSpec.builder()
			.description(Text.of("Sets the current chat mode to system chat."))
			.executor(new SetModeSystem())
			.permission("Chat.adminthing")
			.build();
	CommandSpec smCommandSpec = CommandSpec.builder()
			.description(Text.of("Sets the current chat mode."))
			.child(smwCommandSpec, "world", "w")
			.child(smtCommandSpec, "trade", "t")
			.child(smlCommandSpec, "local", "l")
			.child(smsCommandSpec, "system", "s")
			.executor(new SetMode())
			.build();
	
	@Listener
	public void preInit(GamePreInitializationEvent e)
	{
		ChatKeys.dummy();
		DataRegistration.builder()
		.dataName("Chat Data")
		.manipulatorId("chat_data")
		.dataClass(ChatData.class)
		.immutableClass(ImmutableChatData.class)
		.builder(new ChatDataBuilder())
		.buildAndRegister(container);
	}
	
	@Listener
	public void init(GameInitializationEvent e)
	{
		// Commands
		Sponge.getCommandManager().register(this, wcviCommandSpec, "worldchaton", "wcon", "worldchat", "wc");
		Sponge.getCommandManager().register(this, wcvoCommandSpec, "worldchatoff", "wcoff");
		Sponge.getCommandManager().register(this, tcviCommandSpec, "tradechaton", "tcon", "tradechat", "tc");
		Sponge.getCommandManager().register(this, tcvoCommandSpec, "tradechatoff", "tcoff");
		Sponge.getCommandManager().register(this, lcviCommandSpec, "localchaton", "lcon", "localchat", "lc");
		Sponge.getCommandManager().register(this, lcvoCommandSpec, "localchatoff", "lcoff");
		Sponge.getCommandManager().register(this, vcsCommandSpec, "chatstats", "viewchatstats", "chats");
		Sponge.getCommandManager().register(this, smCommandSpec, "chat", "mode", "chatmode");
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
	public void messageSent(MessageChannelEvent.Chat e)
	{
		if(e.getSource() instanceof Player)
		{
			Player player = (Player) e.getSource();
			
			if (player.get(ChatKeys.MODE).get().equals("World"))
			{
				e.setChannel(wmci);
			} else if (player.get(ChatKeys.MODE).get().equals("Trade"))
			{
				e.setChannel(tmci);
			} else if (player.get(ChatKeys.MODE).get().equals("Local"))
			{
				e.setChannel(lmci);
				
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
}
