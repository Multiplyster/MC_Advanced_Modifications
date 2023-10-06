package advancedmodifications.modifications;

import net.md_5.bungee.api.ChatColor;

public enum ModificationList {
    ;

    Modification reference;
    private ModificationList(Modification reference) {
        this.reference = reference;
    }

    public Modification getReference() {
        return reference;
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
            throw new IllegalArgumentException("Exactness must be between 0 and 3");

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