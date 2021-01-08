package com.solexgames.command;

import com.solexgames.DataPlugin;

public class CommandManager {

    public CommandManager() {
        DataPlugin.getInstance().getCommand("datadump").setExecutor(new DataDumpCommand());
        DataPlugin.getInstance().getCommand("forceupdate").setExecutor(new ForceUpdateCommand());
    }
}
