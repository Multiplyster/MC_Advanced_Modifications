package AdvancedModifications.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import AdvancedModifications.Main;
import AdvancedModifications.items.modularitems.ModularItem;
import AdvancedModifications.items.util.CustomItem;

public class PortableModificationTable extends CustomItem {

    public static final String NAME = ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + "Portable Modification Table";
    public static final PortableModificationTable INSTNACE = new PortableModificationTable();

    private static ItemStack fillerItem; /* Filler items */
    private static ItemStack exitItem; /* Button clicked to exit menu */
    private static ItemStack lockedSlotItem; /* Item shown on locked slots */
    private static ItemStack upgradeItemItem; /* Button clicked to upgrade items */

    private static final int ITEM_SLOT = (9 * 1) + 1;
    private static final int ITEM_UPGRADE_SLOT =  (9 * 3) + 1;
    private static final int MODIFICATION_SLOT_OFFSET = (9 * 1) + 6;
    private static final int EXIT_ITEM_SLOT = 9 * 6 - 1;

    /* Netherite -> Modular item mki */
    private static ItemStack[] t1UpgradeMaterials = new ItemStack[] {new ItemStack(Material.NETHERITE_INGOT, 1), new ItemStack(Material.DIAMOND, 5)};
    /* mki - mkii */
    private static ItemStack[] t2UpgradeMaterials = new ItemStack[] {new ItemStack(Material.DIAMOND, 5), new ItemStack(Material.GOLD_BLOCK, 1)};
    /* mkii - mkiii */
    private static ItemStack[] t3UpgradeMaterials = new ItemStack[] {new ItemStack(Material.DIAMOND, 5), new ItemStack(Material.EMERALD_ORE, 1)};
    
    public PortableModificationTable() {
        super(Material.END_CRYSTAL, NAME, new String[] {ChatColor.GRAY + "Use to upgrade modifications, tools, and armor!"});

        /* Set filler item */
        fillerItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = fillerItem.getItemMeta();
        fillerMeta.setDisplayName(" ");

        /* Set exit item */
        exitItem = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exitItem.getItemMeta();
        exitMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Exit");
        List<String> exitLore = new ArrayList<String>();
        exitLore.add(ChatColor.GRAY + "Click to exit!");
        exitMeta.setLore(exitLore);

        /* Set the locked slot item */
        lockedSlotItem = new ItemStack(Material.IRON_BARS);
        ItemMeta lockedSlotMeta = lockedSlotItem.getItemMeta();
        lockedSlotMeta.setDisplayName(ChatColor.RED + "Unavailable modification slot!");
        List<String> lockedSlotLore = new ArrayList<String>();
        lockedSlotLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "This slot is unavailable to modify! Try");
        lockedSlotLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "upgrading your armor to unlcok more slots!");
        lockedSlotMeta.setLore(lockedSlotLore);

        /* Set the upgrade armor button item */
        upgradeItemItem = new ItemStack(Material.GREEN_CONCRETE);
        ItemMeta upgradeArmorMeta = upgradeItemItem.getItemMeta();
        upgradeArmorMeta.setDisplayName(ChatColor.DARK_GREEN + "Click to upgrade!");
        List<String> upgradeArmorLore = new ArrayList<String>();
        upgradeArmorLore.add(ChatColor.GRAY + "Upgrading your armor will take");
    }

    @Override
    public ShapedRecipe getRecipe() {
        NamespacedKey key = new NamespacedKey(Main.INSTANCE, ChatColor.stripColor(NAME).replaceAll(" ", "_"));
        ShapedRecipe recipe = new ShapedRecipe(key, new PortableModificationTable());

        recipe.shape("ngn", "gsg", "ngn");
        
        recipe.setIngredient('n', Material.NETHERITE_SCRAP);
        recipe.setIngredient('g', Material.GLASS);
        recipe.setIngredient('s', Material.NETHER_STAR);

        return recipe;
    }

    public void onEvent(Event e) {
        if(e instanceof PlayerInteractEvent) {
            PlayerInteractEvent event = (PlayerInteractEvent) e;
            Player player = event.getPlayer();

            if(event.getHand() == EquipmentSlot.OFF_HAND)
                return;

            if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
                return;

            player.openInventory(getInventory());
            player.playSound(player, Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);
        } else if(e instanceof InventoryClickEvent) {
            InventoryClickEvent event = (InventoryClickEvent) e;
            Player player = (Player) event.getWhoClicked();

            /* Don't do anything if its the player's inventory */
            if(event.getClickedInventory().equals(event.getView().getBottomInventory()))
                return;

            int slot = event.getSlot();
            if(slot == ITEM_SLOT) {

            } else { /* Cancel event is any other action */
                event.setCancelled(true);

                if(slot == EXIT_ITEM_SLOT)
                    player.closeInventory();
            }
        }
    }

    public Inventory getInventory() {
        Inventory inv = Bukkit.createInventory(null, 9 * 6, NAME);

        /* Set contents to gray glass pane */
        for(int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, fillerItem);
        }

        inv.setItem(EXIT_ITEM_SLOT, exitItem);
        inv.setItem(ITEM_SLOT, null);
        inv.setItem(ITEM_UPGRADE_SLOT, upgradeItemItem);

        /* Empty modification slots */
        for(int i = 0; i < ModularItem.MAX_SLOTS; i++) {
            inv.setItem(MODIFICATION_SLOT_OFFSET + (18 * i), lockedSlotItem);
        }

        return inv;
    }
}
