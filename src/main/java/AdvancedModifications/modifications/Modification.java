package advancedmodifications.modifications;

import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Modification {

    private String name;
    private List<String> lore;
    private boolean isShiny;
    private ItemStack[] baseItems;
    private Integer tier;

    /**
     * @param name Name of the modification without colors (split on ' ')
     * @param lore Lore of the item
     * @param isShiny Determines if the modification is glowing or not
     * @param baseItem Display item of modification
     */
    public Modification(String name, List<String> lore, boolean isShiny, ItemStack[] baseItems) {
        this.name = name;
        this.lore = lore;
        this.isShiny = isShiny;
        this.baseItems = baseItems;

        for(ItemStack baseItem : baseItems) {
            ItemMeta meta = baseItem.getItemMeta();
            meta.setDisplayName(getName());
            meta.setLore(lore);

            if(isShiny){
                baseItem.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            baseItem.setItemMeta(meta);
        }

        if(baseItems.length > 1)
            tier = 0;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
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
     * @param tier Index for base item (0 based)
     */
    public ItemStack getBaseItem(int tier) {
        return baseItems[tier];
    }

    /**
     * Gets the item of the upgrade
     */
    public ItemStack[] getBaseItems() {
        return baseItems;
    }

    /**
     * Gets the current tier of the modification (null if no tiers)
     */
    public Integer getTier() {
        return tier;
    }

    /**
     * Increments the tier of the modification
     * @return True if successful, false if unsuccessful
     */
    public boolean upgrade(ItemStack item) {
        if(baseItems.length == 1)
            return false;

        if(tier == baseItems.length - 1)
            return false;

        tier++;
        return true;
    }

    public boolean equals(Modification other) {
        if(!name.equalsIgnoreCase(other.getName())) // Name check
            return false;

        for(int i = 0; i < baseItems.length; i++){
            if(!baseItems[i].isSimilar(other.getBaseItem(i))) // Material, durability, and meta check
                return false;
        }

        if(isShiny != other.isShiny())
            return false;
        return true;
    }

    public String toString() {
        return name + " MK" + tier;
    }

    public abstract ItemStack[] getValidApplicableItems();
    public abstract void onEvent(Event event);
}
