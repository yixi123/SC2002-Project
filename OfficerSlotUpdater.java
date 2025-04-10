public class OfficerSlotUpdater implements IOfficerSlotUpdater {
    @Override
    public void decreaseSlot(Project project) {
        if (project.getOfficerSlots() > 0) {
            project.setOfficerSlots(project.getOfficerSlots() - 1);
        }
    }
}