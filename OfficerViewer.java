public class OfficerViewer implements ICommonView {
    private final ManagerController controller;

    public OfficerViewer(ManagerController controller) {
        this.controller = controller;
    }

    @Override
    public void viewPendingOfficers(Project project) {
        HDBManager manager = controller.getCurrentManager();

        if (!project.getManager().equals(manager)) {
            System.out.println("❌ Not your project.");
            return;
        }

        for (HDBOfficer officer : OfficerDB.getAllOfficers()) {
            if (officer.getAssignedProject().equals(project.getName()) && !officer.isApproved()) {
                System.out.println(officer);
            }
        }
    }

    @Override
    public void viewApprovedOfficers(Project project) {
        HDBManager manager = controller.getCurrentManager();

        if (!project.getManager().equals(manager)) {
            System.out.println("❌ Not your project.");
            return;
        }

        for (HDBOfficer officer : OfficerDB.getAllOfficers()) {
            if (officer.getAssignedProject().equals(project.getName()) && officer.isApproved()) {
                System.out.println(officer);
            }
        }
    }
}

