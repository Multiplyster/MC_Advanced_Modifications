package AdvancedModifications.modifications;

import AdvancedModifications.modifications.mods.Exoskeleton;

public enum ModificationList {
    EXOSKELETON(Exoskeleton.INSTANCE)/* MODS HERE */;

    private Modification reference;
    private ModificationList(Modification reference) {
        this.reference = reference;
    }

    public Modification getReference() {
        return reference;
    }
}