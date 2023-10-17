package AdvancedModifications.items.util;

import AdvancedModifications.items.PortableModificationTable;

public enum CustomItemList {
    PORTABLE_MODIFICATION_TABLE(PortableModificationTable.INSTNACE);

    private CustomItem reference;
    private CustomItemList(CustomItem reference) {
        this.reference = reference;
    }

    public CustomItem getReference() {
        return reference;
    }
}
