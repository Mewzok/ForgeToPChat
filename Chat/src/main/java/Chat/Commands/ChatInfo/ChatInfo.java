package Chat.Commands.ChatInfo;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.ClickAction;
import org.spongepowered.api.text.action.HoverAction;
import org.spongepowered.api.text.action.TextAction;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import Chat.Main;

public class ChatInfo implements CommandExecutor
{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
	{
		Text localText = Text.builder("Local").color(TextColors.GRAY).style(TextStyles.BOLD).onClick(TextActions.runCommand("/chat info local")).build();
		Text tradeText = Text.builder("Trade").color(TextColors.AQUA).style(TextStyles.BOLD).onClick(TextActions.runCommand("/chat info trade")).build();
		Text worldText = Text.builder("World").color(TextColors.WHITE).style(TextStyles.BOLD).onClick(TextActions.runCommand("/chat info world")).build();
		Text systemText = Text.builder("System").color(TextColors.LIGHT_PURPLE).style(TextStyles.BOLD).onClick(TextActions.runCommand("/chat info system")).build();
		
		Text msg = Text.builder("ToPChat has four different channels: ").append(localText).append(Text.of(", ")).append(tradeText).append(Text.of(", "))
				.append(worldText).append(Text.of(", and ")).append(systemText).append(Text.of(". Each channel can be switched on or off individually. In this way, "
				+ "you can, for example, receive messages from world chat while disallowing all messages from trade chat. Use /chat help to see a list of commands. "
				+ "While you can have all channel inputs on or off, you may only set your output to one channel at a time. For example, you must switch your channel "
				+ "mode to trade chat to speak through trade, or local to speak through local. Input and output work independently of each other, so you may, for "
				+ "example, speak through trade chat but only receive messages through local chat. Hover over a channel's name in this text to learn more about it."))
				.build();
		
		src.sendMessage(msg);
		return CommandResult.success();
	}

}
