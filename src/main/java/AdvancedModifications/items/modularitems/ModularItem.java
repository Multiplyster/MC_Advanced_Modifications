package AdvancedModifications.items.modularitems;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import AdvancedModifications.items.util.CustomItem;

public abstract class ModularItem extends CustomItem {

    public static ChatColor[] nameColors = new ChatColor[] {ChatColor.GREEN, ChatColor.GOLD, ChatColor.DARK_PURPLE};

    public ModularItem(Material material, String name) {
        super(material, name, new String[] {});
    }

    /**
     * Upgrades the given item
     * @param item Item to upgrade
     */
    public static void upgrade(ItemStack item) {
        if(!item.getType().toString().contains("netherite"))
            return;

        ItemMeta meta = item.getItemMeta();
        
        /* No lore means base netherite item */
        if(!meta.hasLore()) {
            List<String> lore = new ArrayList<String>();
            lore.add(nameColors[0] + "Modular" + (new ItemStack(item.getType()).getItemMeta().getDisplayName() + "MKI"));
            lore.add(ChatColor.GRAY + "Available Modification Slots: 1/3");
            lore.add(ChatColor.GRAY + "Slot 1: " + ChatColor.ITALIC + "EMPTY");
            lore.add(ChatColor.GRAY + "Slot 2: " + ChatColor.ITALIC + "" + ChatColor.RED + "LOCKED");
            lore.add(ChatColor.GRAY + "Slot 3: " + ChatColor.ITALIC + "" + ChatColor.RED + "LOCKED");
            meta.setLore(lore);
            return;
        }
        
        /* Assume the item is a modular item */
        ModularItem i = (ModularItem) item;
        List<String> lore = meta.getLore();
        int tier = i.getTier();

        /* Replace first line of old lore with new string */
        lore.set(0, nameColors[tier] + " Modular" + (new ItemStack(item.getType()).getItemMeta().getDisplayName() + "MK" + romanNumerFromInt(tier + 1)));
        meta.setLore(lore);
    }

    /**
     * @return The tier of this item based on the first line of the lore
     */
    public int getTier() {
        List<String> lore = this.getItemMeta().getLore();
        if(lore.isEmpty())
            return 0;

        String firstLine = lore.get(0);
        if(!firstLine.contains("MK"))
            return 0;

        return numFromRomanNumeral(firstLine.substring(firstLine.indexOf("MK")));
    }

    /** Only goes up to 3 */
    public static int numFromRomanNumeral(String num) {
        num = num.toLowerCase();

        switch(num) {
            case "i": return 1;
            case "ii": return 2;
            case "iii": return 3;
            default: return 0;
        }
    }

    /** Only goes up to 3 */
    public static String romanNumerFromInt(int num) {
        switch(num) {
            case 1: return "I";
            case 2: return "II";
            case 3: return "III";
            default: return null;
        }
    }

    @Override
    public abstract ShapedRecipe getRecipe();

    @Override
    public void onEvent(Event e) {
        throw new UnsupportedOperationException("Unimplemented method 'onEvent'");
    }
    
}
