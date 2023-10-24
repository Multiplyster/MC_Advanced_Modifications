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

    private static final String[] NAME = new String[] {ChatColor.GREEN + "Exoskeleton", ChatColor.YELLOW + "Exoskeleton", ChatColor.AQUA + "Exoskeleton"};
    private static final List<String> LORE = Arrays.asList(new String[] {ChatColor.GRAY + "Tired of your base movement speed?", ChatColor.GRAY + "Try using an exoskeleton! When", ChatColor.GRAY + "applied to modular leggings, your", ChatColor.GRAY +"base movement speed is increased."});
    private static final boolean IS_SHINY = true;
    private static final ItemStack[] BASE_ITEMS = {new ItemStack(Material.IRON_LEGGINGS),
                                                   new ItemStack(Material.GOLDEN_LEGGINGS),
                                                   new ItemStack(Material.DIAMOND_LEGGINGS)};
    private static final ItemStack[] MODULAR_ITEM_TYPES = {};

    public static final Exoskeleton INSTANCE = new Exoskeleton();

    public Exoskeleton() {
        super(NAME, LORE, IS_SHINY, BASE_ITEMS);
    }

    public Exoskeleton(Integer tier) {
        super(NAME, tier, LORE, IS_SHINY, BASE_ITEMS);
    }

    @Override
    public ItemStack[] getValidApplicableItems() {
        return MODULAR_ITEM_TYPES;
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

        /* Initialize namespaced keys */
        NamespacedKey mk1Key = new NamespacedKey(Main.INSTANCE, ChatColor.stripColor(new Exoskeleton(0).toString()).replaceAll(" ", "_"));
        NamespacedKey mk2Key = new NamespacedKey(Main.INSTANCE, ChatColor.stripColor(new Exoskeleton(1).toString()).replaceAll(" ", "_"));
        NamespacedKey mk3Key = new NamespacedKey(Main.INSTANCE, ChatColor.stripColor(new Exoskeleton(2).toString()).replaceAll(" ", "_"));

        /* Initialize recipes */
        ShapedRecipe mk1Recipe = new ShapedRecipe(mk1Key, new Exoskeleton(0).getModificationItem());
        ShapedRecipe mk2Recipe = new ShapedRecipe(mk2Key, new Exoskeleton(1).getModificationItem());
        ShapedRecipe mk3Recipe = new ShapedRecipe(mk3Key, new Exoskeleton(2).getModificationItem());

        /* Set recipe shapes */
        mk1Recipe.shape("sls", "rnr", "sls");
        mk2Recipe.shape(" s ", "lel", " s ");
        mk3Recipe.shape(" s ", "lel", " s ");

        /* Declare default speed potion */
        ItemStack defaultSpeedPot = new ItemStack(Material.POTION);
        PotionMeta defualtSpeedMeta = (PotionMeta) defaultSpeedPot.getItemMeta();
        defualtSpeedMeta.setBasePotionData(new PotionData(PotionType.SPEED, false, false));
        defaultSpeedPot.setItemMeta(defualtSpeedMeta);

        /* Declare extended time speed potion */
        ItemStack extendedSpeedPot = new ItemStack(Material.POTION);
        PotionMeta extendedSpeedMeta = (PotionMeta) extendedSpeedPot.getItemMeta();
        extendedSpeedMeta.setBasePotionData(new PotionData(PotionType.SPEED, true, false));
        extendedSpeedPot.setItemMeta(extendedSpeedMeta);

        /* Declare upgraded speed potion */
        ItemStack upgradedSpeedPot = new ItemStack(Material.POTION);
        PotionMeta upgradedSpeedMeta = (PotionMeta) extendedSpeedPot.getItemMeta();
        upgradedSpeedMeta.setBasePotionData(new PotionData(PotionType.SPEED, false, true));
        upgradedSpeedPot.setItemMeta(upgradedSpeedMeta);

        /* Declare mk1 recipe ingredient keys */
        mk1Recipe.setIngredient('s', new RecipeChoice.ExactChoice(defaultSpeedPot));
        mk1Recipe.setIngredient('l', Material.IRON_LEGGINGS);
        mk1Recipe.setIngredient('r', Material.REDSTONE);
        mk1Recipe.setIngredient('n', Material.NETHERITE_SCRAP);

        /* Declare mk2 recipe ingredient keys */
        mk2Recipe.setIngredient('s', new RecipeChoice.ExactChoice(extendedSpeedPot));
        mk2Recipe.setIngredient('l', Material.GOLDEN_LEGGINGS);
        mk2Recipe.setIngredient('e', new RecipeChoice.ExactChoice(new Exoskeleton(0).getModificationItem()));

        /* Declare mk3 recipe ingredient keys */
        mk3Recipe.setIngredient('s', new RecipeChoice.ExactChoice(upgradedSpeedPot));
        mk3Recipe.setIngredient('l', Material.DIAMOND_LEGGINGS);
        mk3Recipe.setIngredient('e', new RecipeChoice.ExactChoice(new Exoskeleton(1).getModificationItem()));

        /* Add all recipes to recipe array */
        recipes[0] = mk1Recipe;
        recipes[1] = mk2Recipe;
        recipes[2] = mk3Recipe;

        return recipes;
    }
}
