public class WithdrawalRejector implements IWithdrawalRejector {
    @Override
    public void rejectWithdrawal(Applicant applicant) {
        HDBManager manager = (HDBManager) LoginSession.getUser();
        Project project = applicant.getProject();

        if (!project.getManager().equals(manager) ||
            !ActiveProjectResolver.getCurrentProject(manager).equals(project)) {
            System.out.println("❌ Cannot reject withdrawal: Not your active project.");
            return;
        }

        applicant.setStatus(ApplicationStatus.REJECTED);
        System.out.println("❌ Withdrawal rejected.");
    }
}