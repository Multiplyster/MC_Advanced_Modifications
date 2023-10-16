package AdvancedModifications.modifications;

import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;

import AdvancedModifications.Main;

public class ModificationManager implements Listener {

    public static ModificationManager INSTNACE = new ModificationManager();
    private Thread eventDispatchThread;

    public void init() {
        /* Recieves all events disptached by the server and sends them to the function 'dispatchEvents' */
        RegisteredListener registeredListener = new RegisteredListener(this, (listener, event) -> onEvent(event), EventPriority.NORMAL, Main.INSTANCE, false);
        for(HandlerList handler : HandlerList.getHandlerLists()) {
            handler.register(registeredListener);
        }

        Main.INSTANCE.logger.info("Initialized modification listener!");
    }

    public void onEvent(Event e) {
        if(eventDispatchThread == null) { /* Initialize thread if null */
            eventDispatchThread = new Thread("Event Dispatch Thread") {
                @Override
                public void run() {
                    for(ModificationList mod : ModificationList.values()) {
                        mod.getReference().onEvent(e);
                    }
                }
            };
        }

        eventDispatchThread.start();
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
                    if(mod.getReference().getName().startsWith(str) || ChatColor.stripColor(mod.getReference().getName()).startsWith(str))
                        return mod.getReference();
                    break;

                case 1:
                    if(ChatColor.stripColor(mod.getReference().getName()).equalsIgnoreCase(str))
                        return mod.getReference();
                    break;

                case 2:
                    if(mod.getReference().getName().equalsIgnoreCase(str))
                        return mod.getReference();
                    break;

                case 3:
                    if(mod.getReference().getName().equals(str))
                        return mod.getReference();
                    break;
            }
        }
        
        return null;
    }
}
