public class PendingOfficerViewer implements IPendingOfficerViewer {
    private final ManagerController controller;

    public PendingOfficerViewer(ManagerController controller) {
        this.controller = controller;
    }

    @Override
    public void viewPendingOfficers(Project project) {
        HDBManager manager = controller.getCurrentManager();

        if (!project.getManager().equals(manager)) {
            System.out.println("❌ Not your project.");
            return;
        }

        for (User user : Users.getAllUsers()) {
            if (user instanceof HDBOfficer officer) {
                boolean isAssignedToThisProject = officer.getAssignedProject().equals(project.getName());
                boolean isPendingApproval = !officer.isApproved();

                if (isAssignedToThisProject && isPendingApproval) {
                    System.out.println(officer);
                }
            }
        }
    }
}
