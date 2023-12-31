package AdvancedModifications.modifications;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.RegisteredListener;

import AdvancedModifications.Main;

public class ModificationManager implements Listener {

    public static final ModificationManager INSTNACE = new ModificationManager();

    public void init() {
        /* Recieves all events disptached by the server and sends them to the function 'dispatchEvents' */
        RegisteredListener registeredListener = new RegisteredListener(this, (listener, event) -> dispatchEvents(event), EventPriority.NORMAL, Main.INSTANCE, false);
        for(HandlerList handler : HandlerList.getHandlerLists()) {
            handler.register(registeredListener);
        }

        for(ModificationList m : ModificationList.values()) {
            Modification mod = m.getReference();
            
            for(ShapedRecipe recipe : mod.getAllRecipes()) {
                Bukkit.addRecipe(recipe);
            }
        }

        Main.INSTANCE.logger.info("Initialized modification listener!");
    }

    public void dispatchEvents(Event e) {
        for(ModificationList m : ModificationList.values()) {
            m.getReference().onEvent(e);
        }
    }

    /**
     * Exactness:
     *  - 0: Check beginning, with and without color
     *  - 1: Same name ignore case, but no color
     *  - 2: Same name ignore case
     *  - 3: Same name
     * @param str String to check
     * @param exactness (0-3) Level of exactness
     * @return Mod if found, null if not
     */
    public static Modification modFromString(String str, int exactness) {
        if(exactness < 0 || exactness > 3)
            throw new IllegalArgumentException("Param 'exactness' must be between 0 and 3");

        for(ModificationList mod : ModificationList.values()) {
            switch(exactness) {
                case 0:
                    if(mod.getReference().getBaseName().startsWith(str) || ChatColor.stripColor(mod.getReference().getBaseName()).startsWith(str))
                        return mod.getReference();
                    break;

                case 1:
                    if(ChatColor.stripColor(mod.getReference().getBaseName()).equalsIgnoreCase(str))
                        return mod.getReference();
                    break;

                case 2:
                    if(mod.getReference().getBaseName().equalsIgnoreCase(str))
                        return mod.getReference();
                    break;

                case 3:
                    if(mod.getReference().getBaseName().equals(str))
                        return mod.getReference();
                    break;
            }
        }
        
        return null;
    }
}
