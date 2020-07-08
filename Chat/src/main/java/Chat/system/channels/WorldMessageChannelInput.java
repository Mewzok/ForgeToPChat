package Chat.system.channels;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.service.economy.transaction.TransactionResult;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.channel.MutableMessageChannel;
import org.spongepowered.api.text.chat.ChatType;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import Chat.Main;
import Chat.system.Utility;

public class WorldMessageChannelInput implements MutableMessageChannel
{
	private Set<MessageReceiver> members;
	private HashMap<Player, Long> cooldown = new HashMap<>();
	
	public WorldMessageChannelInput()
	{
		this(Collections.emptySet());
	}
	
	public WorldMessageChannelInput(Collection<MessageReceiver> members)
	{
		this.members = Collections.newSetFromMap(new WeakHashMap<>());
		this.members.addAll(members);
	} 
	
	@Override
	public Collection<MessageReceiver> getMembers()
	{
		return Collections.unmodifiableSet(this.members);
	}

	@Override
	public boolean addMember(MessageReceiver member)
	{
		return this.members.add(member);
	}

	@Override
	public boolean removeMember(MessageReceiver member)
	{
		return this.members.remove(member);
	}

	@Override
	public void clearMembers()
	{
		this.members.clear();
	}
	
	@Override
	public Optional<Text> transformMessage(Object sender, MessageReceiver recipient, Text original, ChatType type)
	{
		Optional<EconomyService> serviceOpt = Sponge.getServiceManager().provide(EconomyService.class);
		EconomyService ecoService = Main.economyService;
		BigDecimal amount = Main.confWorldCost;
		
		// Check cost if economy plugin is found
		if(serviceOpt.isPresent())
		{
			TransactionResult result = Utility.MessageCost(ecoService, (Player)sender, amount);
			
			if(result.getResult() != ResultType.SUCCESS)
			{	
				((Player)sender).sendMessage(Text.of(TextColors.RED, TextStyles.ITALIC, "You don't have enough money. Speaking in this channel currently costs $" +
				amount + " per message."));
				return Optional.empty();
			} else 
			{
				if(amount.doubleValue() != 0.0)
					((Player) sender).sendMessage(Text.of(TextColors.DARK_GRAY, TextStyles.ITALIC, "$" + amount + " deducted."));
			}
		}
		// Check cooldown
		String msg = Utility.CommandCooldown((Player)sender, cooldown, Main.confWorldCD);
		
		if(msg == null)
		{
			Text text = original;
			if(this.members.contains(recipient))
			{
				text = Text.of("[World]", text);
			}
			return Optional.of(text);
		} else
		{ // Return empty due to still on cooldown
			((Player) sender).sendMessage(Text.of(msg));
			return Optional.empty();
		}
	}
}
