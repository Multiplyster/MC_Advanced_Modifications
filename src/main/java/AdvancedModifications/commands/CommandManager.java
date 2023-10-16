package AdvancedModifications.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import AdvancedModifications.Main;

public class CommandManager implements CommandExecutor {

    public static CommandManager INSTANCE = new CommandManager();

    public void init() {
        Main.getPlugin(Main.class).getCommand(null).setExecutor(INSTANCE);

        Main.INSTANCE.logger.info("Hooked commands!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch(label.toLowerCase()) {
            case "hello":
                sender.sendMessage(ChatColor.GREEN + "Hello");
                break;
        }

        return false;
    }
    
}
