package com.solexgames.command;

import com.solexgames.DataPlugin;
import com.solexgames.util.ColorUtil;
import com.solexgames.util.RedisUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ForceUpdateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof ConsoleCommandSender) {
            System.out.println("no");
            return false;
        }

        Player player = (Player) commandSender;

        if (player.hasPermission("argon.manager")) {
            if (args.length == 0) {
                Executor executor = Executors.newFixedThreadPool(1);
                executor.execute(() -> DataPlugin.getInstance().getRedisClient().write(RedisUtil.getServerUpdateMessage().toString()));
                player.sendMessage(ColorUtil.translate("&aForce-updated this server via RedisClient."));
            }
        } else {
            player.sendMessage(ColorUtil.translate("&cNo permission."));
        }
        return false;
    }
}
