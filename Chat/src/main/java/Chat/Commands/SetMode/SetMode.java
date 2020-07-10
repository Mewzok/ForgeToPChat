package Chat.Commands.SetMode;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class SetMode implements CommandExecutor
{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
	{
		if(src instanceof Player)
		{
			if(((Player)src).hasPermission("topchat.command.setmode"))
			{
				src.sendMessage(Text.of("Improper command usage. Use /chat help for a list of commands or /chat <chat-mode> to change chat mode."));
			}
		} else
			src.sendMessage(Text.of("Improper command usage. Use /chat help for a list of commands or /chat <chat-mode> to change chat mode."));
		return CommandResult.success();
	}

}
