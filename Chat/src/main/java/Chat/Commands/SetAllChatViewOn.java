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

public class SetAllChatViewOn implements CommandExecutor
{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
	{
		if(src instanceof Player)
		{
			if(((Player)src).hasPermission("topchat.command.setallchatviewon"))
			{
				Optional<ChatData> chatOptional = ((CompositeValueStore<DataHolder, DataManipulator<?, ?>>) src).get(ChatData.class);
				if(chatOptional.isPresent())
				{
					((Player) src).offer(ChatKeys.WORLD_ON, true);
					((Player) src).offer(ChatKeys.TRADE_ON, true);
					((Player) src).offer(ChatKeys.LOCAL_ON, true);
					Main.wmci.addMember(src);
					Main.tmci.addMember(src);
					Main.lmci.addMember(src);
					Text on = Text.of(TextColors.GREEN, TextStyles.ITALIC, "on");
					src.sendMessage(Text.of("All chat channels ", on, "."));
				}
			}
		}
		return CommandResult.success();
	}
}
