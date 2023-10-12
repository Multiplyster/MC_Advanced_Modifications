package AdvancedModifications;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    Logger logger = getLogger();

    @Override
    public void onEnable() {
        logger.info("Enabled!");
    }

    @Override
    public void onDisable() {
        logger.info("Disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.toLowerCase().equals("hello")) {
            sender.sendMessage("Hello!");
        }

        return false;
    }
}
