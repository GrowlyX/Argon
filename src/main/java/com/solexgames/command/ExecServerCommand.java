package com.solexgames.command;

import com.solexgames.util.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ExecServerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof ConsoleCommandSender) {
            System.out.println("no");
            return false;
        }

        Player player = (Player) commandSender;
        if (args.length == 0) {
            player.sendMessage(ColorUtil.translate("&cComing soon."));
        }
        return false;
    }
}
