public class OfficerApprover implements IOfficerApprover {
    @Override
    public void approveOfficer(HDBOfficer officer) {
        HDBManager manager = (HDBManager) LoginSession.getUser();
        Project project = ProjectDB.findByName(officer.getAssignedProject());

        if (!project.getManager().equals(manager)) {
            System.out.println("❌ Unauthorized project access.");
            return;
        }

        if (!project.isVisible()) {
            System.out.println("❌ Project is not active.");
            return;
        }

        if (project.getOfficerSlots() == 0) {
            System.out.println("❌ No officer slots available.");
            return;
        }

        officer.setApproved(true);
        project.setOfficerSlots(project.getOfficerSlots() - 1);

        System.out.println("✅ Officer approved.");
    }
}