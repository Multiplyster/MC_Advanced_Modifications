package AdvancedModifications.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;
import org.bukkit.inventory.ShapedRecipe;

import AdvancedModifications.Main;
import AdvancedModifications.items.util.CustomItem;

public class PortableModificationTable extends CustomItem {

    public static final String NAME = ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + "Portable Modification Table";
    public static final PortableModificationTable INSTNACE = new PortableModificationTable();
    
    public PortableModificationTable() {
        super(Material.END_CRYSTAL, NAME, new String[] {ChatColor.GRAY + "Use to upgrade modifications, tools, and armor!"});
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
        System.out.println(e.getEventName());
    }
}
