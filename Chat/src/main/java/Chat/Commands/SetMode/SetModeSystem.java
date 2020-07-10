package Chat.Commands.SetMode;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import Chat.Data.ChatKeys;

public class SetModeSystem implements CommandExecutor
{	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
	{
		if(src instanceof Player)
		{
			if(((Player)src).hasPermission("topchat.command.setmodesystem"))
			{
				((Player)src).offer(ChatKeys.MODE, "System");
				Text msg = Text.of(TextColors.BLUE, TextStyles.BOLD, "system");
				src.sendMessage(Text.of("You are now typing in ", msg, " chat."));
			}
		}
		return CommandResult.success();
	}
}