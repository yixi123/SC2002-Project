public class PendingOfficerViewer implements IPendingOfficerViewer {
    @Override
    public void viewPendingOfficers(Project project) {
        HDBManager manager = (HDBManager) LoginSession.getUser();

        if (!project.getManager().equals(manager)) {
            System.out.println("❌ Not your project.");
            return;
        }

        for (HDBOfficer o : OfficerDB.getAllOfficers()) {
            if (o.getAssignedProject().equals(project.getName()) && !o.isApproved()) {
                System.out.println(o);
            }
        }
    }
}