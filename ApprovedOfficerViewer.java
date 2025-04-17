public class ApprovedOfficerViewer implements IApprovedOfficerViewer {
    private final ManagerController controller;

    public ApprovedOfficerViewer(ManagerController controller) {
        this.controller = controller;
    }

    @Override
    public void viewApprovedOfficers(Project project) {
        HDBManager manager = controller.getCurrentManager();

        if (!project.getManager().equals(manager)) {
            System.out.println("❌ Not your project.");
            return;
        }

        for (User user : Users.getAllUsers()) {
            if (user instanceof HDBOfficer officer) {
                boolean isAssignedToProject = officer.getAssignedProject().equals(project.getName());
                boolean isApproved = officer.isApproved();

                if (isAssignedToProject && isApproved) {
                    System.out.println(officer);
                }
            }
        }
    }
}
