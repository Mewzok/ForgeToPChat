package Chat.Commands.ChatInfo;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class ChatInfoSystem implements CommandExecutor
{
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
	{
		Text systemText = Text.of(TextColors.LIGHT_PURPLE, TextStyles.BOLD, "System", TextColors.RESET, TextStyles.RESET, " channel is the channel meant only for "
				+ "system events and for admins to announce system-related occurences.");
		src.sendMessage(systemText);
		return CommandResult.success();
	}
}
