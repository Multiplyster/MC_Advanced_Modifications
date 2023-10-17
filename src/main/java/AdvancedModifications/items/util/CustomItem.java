package AdvancedModifications.items.util;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class CustomItem extends ItemStack {
    
    public CustomItem(Material material, String name, String[] lore) {
        super(material);

        ItemMeta meta = getItemMeta();
        meta.setDisplayName(name);

        List<String> l = Arrays.asList(lore);
        meta.setLore(l);
        
        setItemMeta(meta);
    }

    public boolean equal(CustomItem other) {
        if(!other.hasItemMeta())
            return false;

        ItemMeta meta = other.getItemMeta();
        if(!meta.hasLore())
            return false;

        if(!getItemMeta().getDisplayName().equalsIgnoreCase(meta.getDisplayName()))
            return false;

        return true;
    }

    public abstract ShapedRecipe getRecipe();
    public abstract void onEvent(Event e);
}
