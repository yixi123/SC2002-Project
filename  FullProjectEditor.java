public class FullProjectEditor implements IProjectEditorComposite {
    private final IUnitEditor unitEditor = new UnitEditor();
    private final IDateEditor dateEditor = new DateEditor();
    private final IOfficerSlotEditor slotEditor = new OfficerSlotEditor();

    @Override
    public void editUnits() {
        unitEditor.editUnits();
    }

    @Override
    public void editDates() {
        dateEditor.editDates();
    }

    @Override
    public void editOfficerSlots() {
        slotEditor.editOfficerSlots();
    }
}