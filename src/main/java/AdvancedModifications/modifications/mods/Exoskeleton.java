package AdvancedModifications.modifications.mods;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import AdvancedModifications.modifications.Modification;
import net.md_5.bungee.api.ChatColor;

public class Exoskeleton extends Modification {

    private static final String[] NAME = new String[] {ChatColor.GREEN + "Exoskeleton", ChatColor.YELLOW + "Exoskeleton", ChatColor.GOLD + "Exoskeleton"};
    private static final List<String> LORE = Arrays.asList(new String[] {""});
    private static final boolean IS_SHINY = false;
    private static final ItemStack[] BASE_ITEMS = {new ItemStack(Material.IRON_LEGGINGS),
                                                   new ItemStack(Material.GOLDEN_LEGGINGS),
                                                   new ItemStack(Material.GOLDEN_LEGGINGS)};
    private static final Material[] MODULAR_ITEM_TYPES = {Material.NETHERITE_LEGGINGS};

    public static final Exoskeleton INSTANCE = new Exoskeleton();

    public Exoskeleton() {
        super(NAME, LORE, IS_SHINY, BASE_ITEMS);
    }

    @Override
    public Material[] getApplicableItems() {
        return MODULAR_ITEM_TYPES;
    }

    @Override
    public void onEvent(Event e) {
        if(e instanceof PlayerMoveEvent) {
            PlayerMoveEvent event = (PlayerMoveEvent) e;

            //System.out.println(event.getPlayer().isSprinting());
        }
    }
}
