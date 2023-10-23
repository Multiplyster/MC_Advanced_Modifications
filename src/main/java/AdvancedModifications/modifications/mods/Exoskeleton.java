package AdvancedModifications.modifications.mods;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import AdvancedModifications.Main;
import AdvancedModifications.modifications.Modification;

public class Exoskeleton extends Modification {

    private static final String[] NAME = new String[] {ChatColor.GREEN + "Exoskeleton", ChatColor.YELLOW + "Exoskeleton", ChatColor.GOLD + "Exoskeleton"};
    private static final List<String> LORE = Arrays.asList(new String[] {""});
    private static final boolean IS_SHINY = false;
    private static final ItemStack[] BASE_ITEMS = {new ItemStack(Material.IRON_LEGGINGS),
                                                   new ItemStack(Material.GOLDEN_LEGGINGS),
                                                   new ItemStack(Material.GOLDEN_LEGGINGS)};
    private static final ItemStack[] APPLICABLE_ITEMS = {new ItemStack(Material.NETHERITE_LEGGINGS)};

    public static final Exoskeleton INSTANCE = new Exoskeleton();

    public Exoskeleton() {
        super(NAME, LORE, IS_SHINY, BASE_ITEMS);
    }

    public Exoskeleton(Integer tier) {
        super(NAME, tier, LORE, IS_SHINY, BASE_ITEMS);
    }

    @Override
    public ItemStack[] getValidApplicableItems() {
        return APPLICABLE_ITEMS;
    }

    @Override
    public void onEvent(Event e) {
        if(e instanceof PlayerMoveEvent) {
            PlayerMoveEvent event = (PlayerMoveEvent) e;

            //System.out.println(event.getPlayer().isSprinting());
        }
    }

    public ShapedRecipe[] getAllRecipes() {
        ShapedRecipe[] recipes = new ShapedRecipe[3];

        NamespacedKey mk1Key = new NamespacedKey(Main.INSTANCE, ChatColor.stripColor(new Exoskeleton(0).getName()).replaceAll(" ", "_"));
        NamespacedKey mk2Key = new NamespacedKey(Main.INSTANCE, ChatColor.stripColor(new Exoskeleton(1).getName()).replaceAll(" ", "_"));
        NamespacedKey mk3Key = new NamespacedKey(Main.INSTANCE, ChatColor.stripColor(new Exoskeleton(2).getName()).replaceAll(" ", "_"));

        ShapedRecipe mk1recipe = new ShapedRecipe(mk1Key, new Exoskeleton(0).getModificationItem());
        ShapedRecipe mk2recipe = new ShapedRecipe(mk2Key, new Exoskeleton(1).getModificationItem());
        ShapedRecipe mk3recipe = new ShapedRecipe(mk3Key, new Exoskeleton(2).getModificationItem());

        mk1recipe.shape("sls", "rnr", "sls");
        mk2recipe.shape(" s ", "lel", " s ");

        ItemStack speed1Pot = new ItemStack(Material.POTION);
        PotionMeta speed1Meta = (PotionMeta) speed1Pot.getItemMeta();
        speed1Meta.setBasePotionData(new PotionData(PotionType.SPEED, false, false));
        speed1Pot.setItemMeta(speed1Meta);

        ItemStack speed2Pot = new ItemStack(Material.POTION);
        PotionMeta speed2Meta = (PotionMeta) speed2Pot.getItemMeta();
        speed2Meta.setBasePotionData(new PotionData(PotionType.SPEED, false, true));
        speed2Pot.setItemMeta(speed2Meta);

        mk1recipe.setIngredient('s', new RecipeChoice.ExactChoice(speed1Pot));
        mk1recipe.setIngredient('l', Material.IRON_LEGGINGS);
        mk1recipe.setIngredient('r', Material.REDSTONE);
        mk1recipe.setIngredient('n', Material.NETHERITE_SCRAP);

        mk2recipe.setIngredient('s', new RecipeChoice.ExactChoice(speed2Pot));
        mk2recipe.setIngredient('l', Material.GOLDEN_LEGGINGS);
        mk2recipe.setIngredient('e', new RecipeChoice.ExactChoice(new Exoskeleton(0).getModificationItem()));

        recipes[0] = mk1recipe;
        recipes[1] = mk2recipe;
        recipes[2] = mk3recipe;

        return recipes;
    }
}
