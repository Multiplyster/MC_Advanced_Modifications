package AdvancedModifications.items.util;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import AdvancedModifications.Main;

public class ItemManager implements Listener {

    public static final ItemManager INSTANCE = new ItemManager();

    public void init() {
        for(CustomItemList i : CustomItemList.values()) {
            Bukkit.addRecipe(i.getReference().getRecipe());
        }

        Main.INSTANCE.logger.info("Added all recipes!");

        Main.INSTANCE.getServer().getPluginManager().registerEvents(this, Main.INSTANCE);
        Main.INSTANCE.logger.info("Registered item listeners");
    }

    @EventHandler
    public void onPlayerUseEvent(PlayerInteractEvent e) {
        /* Action check */
        if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        /* Blocks event from happening twice */
        if(e.getHand() == EquipmentSlot.OFF_HAND)
            return;

        /* Is holding an item */
        ItemStack i = e.getItem();
        if(i == null)
            return;

        /* Item has meta */
        ItemMeta meta = i.getItemMeta();
        if(meta == null)
            return;

        /* Item has lore */
        if(!meta.hasLore())
            return;

        /* Check if valid item */
        for(CustomItemList item : CustomItemList.values()) {
            if(meta.getDisplayName().equalsIgnoreCase(item.getReference().getItemMeta().getDisplayName())) {
                item.getReference().onEvent(e);
                break;
            }
        }
    }
}
