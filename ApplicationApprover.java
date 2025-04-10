public class ApplicationApprover implements IApplicationApprover {
    private final IFlatQuotaValidator quotaValidator = new FlatQuotaValidator();
    private final IFlatQuotaReducer quotaReducer = new FlatQuotaReducer();

    @Override
    public void approveApplication(Applicant applicant) {
        HDBManager manager = (HDBManager) LoginSession.getUser();
        Project project = applicant.getProject();

        if (!project.getManager().equals(manager) ||
            !ActiveProjectResolver.getCurrentProject(manager).equals(project)) {
            System.out.println("❌ Cannot approve: Not your active project.");
            return;
        }

        if (!quotaValidator.isFlatAvailable(project, applicant.getRequestedFlatType())) {
            System.out.println("❌ No units available for requested flat type.");
            return;
        }

        quotaReducer.reduceFlatQuota(project, applicant.getRequestedFlatType());
        applicant.setStatus(ApplicationStatus.APPROVED);

        System.out.println("✅ Application approved.");
    }
}