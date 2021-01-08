package com.solexgames.command;

import com.solexgames.DataPlugin;
import com.solexgames.network.NetworkServer;
import com.solexgames.util.ColorUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class DataDumpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof ConsoleCommandSender) {
            System.out.println("no");
            return false;
        }

        Player player = (Player) commandSender;
        if (player.hasPermission("argon.manager")) {
            if (args.length == 0) {
                player.sendMessage(ColorUtil.translate("&7&m" + StringUtils.repeat("-", 53)));
                player.sendMessage(ColorUtil.translate("&3&lNetwork Data:"));
                player.sendMessage(ColorUtil.translate("  "));
                DataPlugin.getInstance().getServerManager().getNetworkServers().forEach(networkServer -> {
                    player.sendMessage(ColorUtil.translate(" &8&l• &b" + networkServer.getServerName() + "&7(TPS: " + networkServer.getTicksPerSecond() + ") (Online: " + networkServer.getOnlinePlayers() + ") (Max: " + networkServer.getMaxPlayerLimit() + ") (Status: " + networkServer.getServerStatus() + ") (Type: " + networkServer.getServerType().getServerTypeString() + ")"));
                });
                player.sendMessage(ColorUtil.translate("  "));
                player.sendMessage(ColorUtil.translate("&7&m" + StringUtils.repeat("-", 53)));
            }
            if (args.length > 0) {
                String server = args[0];
                NetworkServer networkServer = NetworkServer.getByName(server);

                if (networkServer != null) {
                    player.sendMessage(ColorUtil.translate("&7&m" + StringUtils.repeat("-", 53)));
                    player.sendMessage(ColorUtil.translate("&3&l" + networkServer.getServerName() + " Data:"));
                    player.sendMessage(ColorUtil.translate("  "));
                    player.sendMessage(ColorUtil.translate("&bServer Type: &3" + networkServer.getServerType().getServerTypeString()));
                    player.sendMessage(ColorUtil.translate("&bServer Status: &3" + networkServer.getServerStatus()));
                    player.sendMessage(ColorUtil.translate("&bMax Players: &3" + networkServer.getMaxPlayerLimit()));
                    player.sendMessage(ColorUtil.translate("&bOnline Players: &3" + networkServer.getOnlinePlayers()));
                    player.sendMessage(ColorUtil.translate("&bTicks Per Second: &3" + networkServer.getTicksPerSecond()));
                    player.sendMessage(ColorUtil.translate("&7&m" + StringUtils.repeat("-", 53)));
                } else {
                    player.sendMessage(ColorUtil.translate("&cThat server is not online or does not exist."));
                }
            }
        } else {
            player.sendMessage(ColorUtil.translate("&cNo permission."));
        }
        return false;
    }
}
