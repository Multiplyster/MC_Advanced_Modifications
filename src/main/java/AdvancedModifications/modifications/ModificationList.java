package advancedmodifications.modifications;

public enum ModificationList {
    ;

    Modification reference;
    private ModificationList(Modification reference) {
        this.reference = reference;
    }

    public Modification getReference() {
        return reference;
    }
}
