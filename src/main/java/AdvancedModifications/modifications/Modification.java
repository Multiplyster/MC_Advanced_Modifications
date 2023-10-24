package AdvancedModifications.modifications;

import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Modification {

    private String[] names;
    private List<String> lore;
    private boolean isShiny;
    private ItemStack[] modificationItems;
    private Integer tier;

    /**
     * @param name Name of the modification without color
     * @param lore Lore of the item
     * @param isShiny Determines if the modification is glowing or not
     * @param baseItem Modification
     */
    public Modification(String[] names, List<String> lore, boolean isShiny, ItemStack[] modificationItems) {
        this.names = names;
        this.lore = lore;
        this.isShiny = isShiny;
        this.modificationItems = modificationItems;

        if(modificationItems.length > 1)
            tier = 0;

        Integer baseTier = tier;
        for(int i = 0; i < modificationItems.length; i++) {
            ItemStack baseItem = modificationItems[i];
            ItemMeta meta = baseItem.getItemMeta();

            if(tier != null)
                tier = i;

            meta.setDisplayName(toString());
            meta.setLore(lore);

            if(isShiny)
                meta.addEnchant(Enchantment.DURABILITY, 1, true);

            baseItem.setItemMeta(meta);
        }

        tier = baseTier;
    }

    /**
     * @param name Name of the modification without color\
     * @param tier Tier of the item (0 is start)
     * @param lore Lore of the item
     * @param isShiny Determines if the modification is glowing or not
     * @param baseItem Display item of modification
     */
    public Modification(String[] names, Integer tier, List<String> lore, boolean isShiny, ItemStack[] baseItems) {
        this(names, lore, isShiny, baseItems);

        this.tier = tier;
    }

    /**
     * @return Name of the modification with color based on tier
     */
    public String getBaseName() {
        if(tier == null)
            return names[0];

        return names[tier];
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

    public ItemStack getModificationItem() {
        return modificationItems[tier];
    }

    public ItemStack[] getAllModificationItems() {
        return modificationItems;
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
        if(modificationItems.length == 1)
            return false;

        if(tier == modificationItems.length - 1)
            return false;

        tier++;
        return true;
    }

    public boolean equals(Modification other) {
        if(!getBaseName().equalsIgnoreCase(other.getBaseName())) // Name check
            return false;

        for(int i = 0; i < modificationItems.length; i++){
            if(!modificationItems[i].isSimilar(other.getModificationItem())) // Material, durability, and meta check
                return false;
        }

        if(isShiny != other.isShiny())
            return false;
        return true;
    }

    public String toString() {
        if(tier == null)
            return getBaseName();

        return getBaseName() + " MK" + (tier + 1);
    }

    public ShapedRecipe getRecipe() {
        return getAllRecipes()[tier];
    }

    public abstract ItemStack[] getValidApplicableItems();
    public abstract ShapedRecipe[] getAllRecipes();
    public abstract void onEvent(Event event);
}
