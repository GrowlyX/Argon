package com.solexgames.command;

import com.solexgames.DataPlugin;
import com.solexgames.network.NetworkServer;
import com.solexgames.util.ColorUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class DataDumpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof ConsoleCommandSender) {
            System.out.println("no");
            return false;
        }

        final Player player = (Player) commandSender;

        if (!player.hasPermission("argon.manager")) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return false;
        }

        if (args.length == 0) {
            final String networkData = DataPlugin.getInstance().getServerManager().getNetworkServers().stream()
                    .map(this::getNetworkData)
                    .collect(Collectors.joining("\n"));

            player.sendMessage(new String[]{
                    ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + StringUtils.repeat("-", 52),
                    ChatColor.DARK_AQUA + "Network Data: ",
                    networkData,
                    ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + StringUtils.repeat("-", 52),
            });
        } else {
            final String serverName = args[0];
            final NetworkServer server = NetworkServer.getByName(serverName);

            if (server == null) {
                player.sendMessage(ChatColor.RED + "No server with name \"" + serverName + "\" is online.");
                return false;
            }

            player.sendMessage(new String[]{
                    ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + StringUtils.repeat("-", 52),
                    ChatColor.DARK_AQUA + server.getServerName() + " Data:",
                    "",
                    this.getIndividualNetworkData(server),
                    ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + StringUtils.repeat("-", 52)
            });
        }

        return false;
    }

    /**
     * Get the data of the network
     *
     * @param server the server
     * @return the data in a formatted string
     */
    private String getNetworkData(NetworkServer server) {
        return ChatColor.GRAY + ChatColor.BOLD.toString() + "• " + String.join(" ", new String[]{
                ChatColor.AQUA + server.getServerName(),
                ChatColor.GRAY + "(TPS: " + server.getTicksPerSecond() + ")",
                ChatColor.GRAY + "(Online: " + server.getOnlinePlayers() + "/" + server.getMaxPlayerLimit() + ")",
                ChatColor.GRAY + "(State: " + server.getServerStatus().name() + ")",
                ChatColor.GRAY + "(Type: " + server.getServerType().name() + ")",
        });
    }

    /**
     * Get individual data of a {@link NetworkServer}
     *
     * @param server the server to get the data from
     * @return the formatted network data
     */
    private String getIndividualNetworkData(NetworkServer server) {
        return String.join("\n", new String[]{
                ChatColor.AQUA + "Server Type: " + ChatColor.DARK_AQUA + server.getServerType().getServerTypeString(),
                ChatColor.AQUA + "server Status: " + ColorUtil.translate(server.getServerStatus().getServerStatusFancyString()),
                ChatColor.AQUA + "Max Players: " + ChatColor.DARK_AQUA + server.getMaxPlayerLimit(),
                ChatColor.AQUA + "Online Players: " + ChatColor.DARK_AQUA + server.getOnlinePlayers(),
                ChatColor.AQUA + "Ticks per Second: " + ChatColor.DARK_AQUA + server.getTicksPerSecond(),
        });
    }
}
