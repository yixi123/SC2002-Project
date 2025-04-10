public class ApplicationRejector implements IApplicationRejector {
    @Override
    public void rejectApplication(Applicant applicant) {
        HDBManager manager = (HDBManager) LoginSession.getUser();
        Project project = applicant.getProject();

        if (!project.getManager().equals(manager) ||
            !ActiveProjectResolver.getCurrentProject(manager).equals(project)) {
            System.out.println("❌ Cannot reject: Not your active project.");
            return;
        }

        applicant.setStatus(ApplicationStatus.REJECTED);
        System.out.println("❌ Application rejected.");
    }
}