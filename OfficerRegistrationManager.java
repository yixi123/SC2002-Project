public class OfficerRegistrationManager implements IOfficerRegistrationManager {
    private final ManagerController controller;

    public OfficerRegistrationManager(ManagerController controller) {
        this.controller = controller;
    }

    @Override
    public void handleOfficerRegistration(HDBOfficer officer, boolean approve) {
        HDBManager manager = controller.getCurrentManager();
        Project project = ProjectDB.findByName(officer.getAssignedProject());

        if (!project.getManager().equals(manager)) {
            System.out.println("❌ Unauthorized.");
            return;
        }

        if (!project.isVisible()) {
            System.out.println("❌ Project is not active.");
            return;
        }

        if (approve) {
            if (!isSlotAvailable(project)) {
                System.out.println("❌ No officer slots.");
                return;
            }
            officer.setApproved(true);
            project.setOfficerSlots(project.getOfficerSlots() - 1);
            System.out.println("✅ Officer approved.");
        } else {
            officer.setApproved(false);
            System.out.println("❌ Officer rejected.");
        }
    }

    private boolean isSlotAvailable(Project project) {
        return project.getOfficerSlots() > 0;
    }
}

