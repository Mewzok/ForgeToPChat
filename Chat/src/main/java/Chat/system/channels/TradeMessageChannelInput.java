package Chat.system.channels;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
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

public class TradeMessageChannelInput implements MutableMessageChannel
{
	private Set<MessageReceiver> members;
	private HashMap<Player, Long> cooldown = new HashMap<>();
	
	public TradeMessageChannelInput()
	{
		this(Collections.emptySet());
	}
	
	public TradeMessageChannelInput(Collection<MessageReceiver> members)
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
		Text text = original;
		if(this.members.contains(recipient))
		{
			text = Text.of(TextColors.AQUA, "[Trade]", text);
			return Optional.of(text);
		} else
		{
			return Optional.empty();
		}
	}
}
