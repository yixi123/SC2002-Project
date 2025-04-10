public class OfficerRejector implements IOfficerRejector {
    @Override
    public void rejectOfficer(HDBOfficer officer) {
        HDBManager manager = (HDBManager) LoginSession.getUser();
        Project project = ProjectDB.findByName(officer.getAssignedProject());

        if (!project.getManager().equals(manager)) {
            System.out.println("❌ Unauthorized project access.");
            return;
        }

        officer.setApproved(false);  // Optional: could be removed
        System.out.println("❌ Officer rejected.");
    }
}