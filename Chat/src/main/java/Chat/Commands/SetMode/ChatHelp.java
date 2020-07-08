package Chat.Commands.SetMode;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public class ChatHelp implements CommandExecutor
{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
	{
		Text msg = Text.builder("/chat help: Shows all ToPChat commands.\n"
				+ "/chat info: Gives detailed explanation of how ToPChat works and how to use each channel.\n"
				+ "/chat info local: Gives detailed explanation of how local chat works and its current settings.\n"
				+ "/chat info trade: Gives detailed explanation of how trade chat works and its current settings.\n"
				+ "/chat info world: Gives detailed explanation of how world chat works and its current settings.\n"
				+ "/chat info system: Gives detailed explanation of how system chat works.\n"
				+ "/localchaton: Enables the viewing of local chat.\n"
				+ "/localchatoff: Disables the viewing of local chat.\n"
				+ "/tradechaton: Enables the viewing of trade chat.\n"
				+ "/tradechatoff: Disables the viewing of trade chat.\n"
				+ "/worldchaton: Enables the viewing of world chat.\n"
				+ "/worldchatoff: Disables the viewing of world chat.\n"
				+ "/viewchatstats: Displays your current chat status.\n"
				+ "/chat local: Sets your current chat output to Local.\n"
				+ "/chat world: Sets your current chat output to World.\n"
				+ "/chat trade: Sets your current chat output to Trade.\n").build();
		src.sendMessage(msg);
		return CommandResult.success();
	}

}
