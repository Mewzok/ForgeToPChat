package Chat.Commands.ChatInfo;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import Chat.Main;

public class ChatInfoWorld implements CommandExecutor
{
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
	{
		if(src instanceof Player)
		{
			if(((Player)src).hasPermission("topchat.command.chatinfoworld"))
			{
				Text worldText = Text.of(TextColors.WHITE, TextStyles.BOLD, "World", TextColors.RESET, TextStyles.RESET, " is the channel meant for generally speaking"
						+ " to the entire server. For example, when you need to ask everyone on the server a question, you would optimally use World chat. While its "
						+ "implementation may change based on server settings, it typically has a sizeable cost to avoid spam, but has no cooldown. "
						+ "World chat's settings on this server are currently: \n"
						+ "Cooldown: " + Main.confWorldCD + " seconds\n"
						+ "Cost: $" + Main.confWorldCost + " per message");
				src.sendMessage(worldText);
				return CommandResult.success();
			}
		} else
		{
			Text worldText = Text.of(TextColors.WHITE, TextStyles.BOLD, "World", TextColors.RESET, TextStyles.RESET, " is the channel meant for generally speaking"
					+ " to the entire server. For example, when you need to ask everyone on the server a question, you would optimally use World chat. While its "
					+ "implementation may change based on server settings, it typically has a sizeable cost to avoid spam, but has no cooldown. "
					+ "World chat's settings on this server are currently: \n"
					+ "Cooldown: " + Main.confWorldCD + " seconds\n"
					+ "Cost: $" + Main.confWorldCost + " per message");
			src.sendMessage(worldText);
			return CommandResult.success();
		}
		return CommandResult.success();
	}
}
