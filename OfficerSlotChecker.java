public class OfficerSlotChecker implements IOfficerSlotChecker {
    @Override
    public boolean isSlotAvailable(Project project) {
        return project.getOfficerSlots() > 0;
    }
}