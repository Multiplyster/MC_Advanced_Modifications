package advancedmodifications.modifications;

import java.util.ArrayList;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Modification {

    private String name;
    private ArrayList<String> lore;
    private boolean isShiny;
    private ItemStack baseItem;

    /**
     * @param name Name of the modification without colors (split on ' ')
     * @param lore Lore of the item
     * @param isShiny Determines if the modification is glowing or not
     * @param baseItem Display item of modification
     */
    public Modification(String name, ArrayList<String> lore, boolean isShiny, ItemStack baseItem) {
        this.name = name;
        this.lore = lore;
        this.isShiny = isShiny;
        this.baseItem = baseItem;

        ItemMeta meta = baseItem.getItemMeta();
        meta.setDisplayName(getName());
        meta.setLore(lore);

        if(isShiny){
            baseItem.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        baseItem.setItemMeta(meta);
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getLore() {
        return lore;
    }

    /**
     * Determines if the item has enchantment glint
     */
    public boolean isShiny() {
        return isShiny;
    }

    /**
     * Gets the item of the upgrade
     */
    public ItemStack getBaseItem() {
        return baseItem;
    }

    public boolean equals(Modification other) {
        if(!name.equalsIgnoreCase(other.getName())) // Name check
            return false;

        if(!baseItem.isSimilar(other.getBaseItem())) // Material, durability, and meta check
            return false;

        if(isShiny != other.isShiny())
            return false;
        return true;
    }

    public String toString() {
        return name;
    }

    public abstract ItemStack[] getValidAplications();
    public abstract void onEvent(Event event);
}
