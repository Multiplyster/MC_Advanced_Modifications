package AdvancedModifications;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import AdvancedModifications.commands.CommandManager;
import AdvancedModifications.items.util.ItemManager;
import AdvancedModifications.modifications.ModificationManager;

public class Main extends JavaPlugin {

    public Logger logger = getLogger();
    public static Main INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        
        ModificationManager.INSTNACE.init();
        CommandManager.INSTANCE.init();
        ItemManager.INSTANCE.init();

        getServer().getPluginManager().registerEvents(GeneralListeners.INSTANCE, INSTANCE);

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
