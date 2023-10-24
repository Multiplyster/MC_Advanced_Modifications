package AdvancedModifications.items.modularitems;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import AdvancedModifications.items.util.CustomItem;
import AdvancedModifications.modifications.Modification;

public abstract class ModularItem extends CustomItem {

    public static ChatColor[] nameColors = new ChatColor[] {ChatColor.GREEN, ChatColor.GOLD, ChatColor.DARK_PURPLE};

    private static final int MAX_SLOTS = 3;
    
    private List<Modification> appliedModifications = new ArrayList<Modification>();

    public ModularItem(Material material) {
        super(material);

        if(appliedModifications.isEmpty()) {
            for(int i = 0; i < 3; i++){
                appliedModifications.add(null);
            }
        }
    }

    public ModularItem(Material material, int tier, boolean append) {
        super(material);

        if(appliedModifications.isEmpty()) {
            for(int i = 0; i < 3; i++) {
                appliedModifications.add(null);
            }
        }

        ItemMeta meta = getItemMeta();
        List<String> lore = meta.getLore();

        if(!append)
            lore.clear();

        lore.add(nameColors[tier] + "Modular" + (new ItemStack(material).getItemMeta().getDisplayName()) + "MKI");
        lore.add(ChatColor.GRAY + "Available Modification Slots: " + (tier + 1) + "/3");

        for(int i = 1; i <= 3; i++) {
            if(tier >= i)
                lore.add(ChatColor.GRAY + "Slot " + i + ": " + ChatColor.ITALIC + "EMPTY");
            else
                lore.add(ChatColor.GRAY + "Slot " + i + ": " + ChatColor.ITALIC + "" + ChatColor.RED + "LOCKED");
        }
    }

    /**
     * Upgrades the given item
     * @param item Item to upgrade
     */
    public static void upgrade(ItemStack item) { /* Has to be static since we can upgrade a normal piece of netherite */
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
        lore.set(tier + 1, ChatColor.GRAY + "Slot " + tier + ": " + ChatColor.ITALIC + "EMPTY");
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

    public void applyModification(Modification mod) {
        int slot = getAvailableSlot();

        if(slot == -1)
            return;

        /* Add mod to list */
        appliedModifications.set(slot - 2, mod);
        
        /* Update meta */
        ItemMeta meta = getItemMeta();
        List<String> lore = meta.getLore();
        lore.set(slot, "Slot " + (slot - 1) + ": " + mod.getName());
        meta.setLore(lore);
    }

    public List<Modification> getAppliedModifications() {
        return appliedModifications;
    }

    public void removeModification(int slot) {
        ItemMeta meta = getItemMeta();
        List<String> lore = meta.getLore();
        String line = lore.get(slot + 1);

        if(ChatColor.stripColor(line.substring(line.indexOf(": "))).equalsIgnoreCase("Empty"))
            return;

        /* Remove from list */
        lore.set(slot - 1, null);

        /* Update meta */
        lore.set(slot + 1, line.substring(0, line.indexOf(": ") + 2) + ChatColor.ITALIC + "EMPTY");
        meta.setLore(lore);
    }

    /**
     * Gets the first available modification slot for this item
     * @return Line index of lore (-1 if no slots available)
     */
    public int getAvailableSlot() {
        ItemMeta meta = getItemMeta();
        List<String> lore = meta.getLore();

        for(int i = 2; i < 2 + MAX_SLOTS; i++) {
            String line = lore.get(i);

            if(ChatColor.stripColor(line.substring(line.indexOf(": "))).equalsIgnoreCase("Empty")) {
                return i;
            }
        }
        
        return -1;
    }

    /**
     * Checks if the given modification can be applied to this item
     * @param mod Mod to apply
     */
    public boolean canApplyModification(Modification mod) {
        if(mod == null)
            return false;

        if(!getItemMeta().getLore().get(0).contains("Modular"))
            return false;

        if(getAvailableSlot() == -1)
            return false;

        for(ItemStack item : mod.getValidApplicableItems()) {
            if(getType() == item.getType()) {
                return true;
            }
        }

        return false;
    }

    public boolean isSimilar(ModularItem other) {
        if(this.getType() != other.getType())
            return false;

        ItemMeta meta = other.getItemMeta();
        List<String> lore = meta.getLore();

        if(lore.isEmpty())
            return false;

        return true;
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
}
