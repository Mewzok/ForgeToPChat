package Chat.system.channels;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.channel.MutableMessageChannel;
import org.spongepowered.api.text.channel.type.FixedMessageChannel;
import org.spongepowered.api.text.chat.ChatType;
import org.spongepowered.api.text.format.TextColors;

import Chat.Main;
import Chat.system.Config;
import Chat.system.Utility;

public class LocalMessageChannelInput implements MutableMessageChannel
{
	private Set<MessageReceiver> members;
	
	public LocalMessageChannelInput()
	{
		this(Collections.emptySet());
	} 
	
	public LocalMessageChannelInput(Collection<MessageReceiver> members)
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
		Collection<Entity> withinRange = Utility.getEntities((Player) sender, Main.confLocalDist);
		if(this.members.contains(recipient) && withinRange.contains((Entity)recipient))
			{
				text = Text.of(TextColors.GRAY, "[Local]", text);
				return Optional.of(text);
			}
		else
		{
			return Optional.empty();
		}
	}
}
