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

public class ChatInfoTrade implements CommandExecutor
{
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
	{
		Text tradeText = Text.of(TextColors.AQUA, TextStyles.BOLD, "Trade", TextColors.RESET, TextStyles.RESET, " is the channel meant for "
				+ "buying, selling, and trading. While its implementation may change based on server settings, it typically has no cost, but has a sizeable"
				+ " cooldown to prevent spam. Messages sent through this channel will reach everyone on the server. Trade chat's settings on this server are "
				+ "currently:\n"
				+ "Cooldown: " + Main.confTradeCD + " seconds\n"
				+ "Cost: $" + Main.confTradeCost + " per message");
		src.sendMessage(tradeText);
		return CommandResult.success();
	}
}
