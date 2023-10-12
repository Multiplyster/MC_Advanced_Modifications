package advancedmodifications.modifications.mods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import advancedmodifications.modifications.Modification;

public class Exoskeleton extends Modification {

    private static final String NAME = "Exoskeleton";
    private static final List<String> LORE = Arrays.asList(new String[] {""});
    private static final boolean IS_SHINY = false;
    private static final ItemStack[] BASE_ITEMS = {new ItemStack(Material.IRON_LEGGINGS),
                                                   new ItemStack(Material.GOLDEN_LEGGINGS),
                                                   new ItemStack(Material.DIAMOND_LEGGINGS)};
    private static final ItemStack[] APPLICABLE_ITEMS = {new ItemStack(Material.NETHERITE_LEGGINGS)};

    public Exoskeleton() {
        super(NAME, LORE, IS_SHINY, BASE_ITEMS);
    }

    @Override
    public ItemStack[] getValidApplicableItems() {
        return APPLICABLE_ITEMS;
    }

    @Override
    public void onEvent(Event event) {
        System.out.println(event.getEventName());
    }
    
}