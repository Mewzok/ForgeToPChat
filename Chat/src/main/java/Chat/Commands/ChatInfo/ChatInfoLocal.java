package Chat.Commands.ChatInfo;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import Chat.Main;

public class ChatInfoLocal implements CommandExecutor
{
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
	{
		Text localText = Text.of(TextColors.GRAY, TextStyles.BOLD, "Local", TextColors.RESET, TextStyles.RESET, " is the default chat"
				+ " channel. While its implementation may change based on server settings, it typically has no cost or cooldown, but only reaches players within a"
				+ " certain number of blocks. Local chat's settings on this server are currently: "
				+ "\nDistance: " + Main.confLocalDist + " blocks"
				+ "\nCooldown: " + Main.confLocalCD + " seconds"
				+ "\nCost: $" + Main.confLocalCost + " per message");
		src.sendMessage(localText);
		return CommandResult.success();
	}
}
