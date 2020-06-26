package Chat.Commands;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.value.mutable.CompositeValueStore;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import Chat.Main;
import Chat.Data.ChatData;
import Chat.Data.ChatKeys;
import Chat.system.channels.WorldMessageChannelInput;

public class SetWorldChatViewOff implements CommandExecutor
{
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
	{
		if(src instanceof Player)
		{
			Optional<ChatData> chatOptional = ((CompositeValueStore<DataHolder, DataManipulator<?, ?>>) src).get(ChatData.class);
			if(chatOptional.isPresent())
			{
				((Player) src).offer(ChatKeys.WORLD_ON, false);
				Main.wmci.removeMember(src);
				Text off = Text.of(TextColors.RED, TextStyles.ITALIC, "off");
				src.sendMessage(Text.of("World chat ", off, "."));
			}
		}
		return CommandResult.success();
	}
}
