package AdvancedModifications;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import AdvancedModifications.items.util.ItemManager;

public class GeneralListeners implements Listener {
    public static final GeneralListeners INSTANCE = new GeneralListeners();

    private static final Advancement FREE_THE_END_ADVANCEMENT = Bukkit.getAdvancement(NamespacedKey.fromString("minecraft:end/kill_dragon"));

    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
        if(!event.getAdvancement().equals(FREE_THE_END_ADVANCEMENT))
            return;

        Bukkit.getScheduler().runTaskLater(Main.INSTANCE, new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                    if(player.getWorld().getName().equals("world_the_end")) {
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "As the final blow is dealt to the Ender Dragon, a radiant burst of energy envelops you, bathing the bedrock portal in a dazzling light. The ground trembles, and a mysterious ancient knowledge surges through your mind, unraveling the secrets of the End. With a newfound wisdom, you now discover the secrets of " + ChatColor.ITALIC + "modular items" + ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "!");
                        ItemManager.grantAllRecipes(player);
                    }
                }
            }
        }, 10);
    }
}
