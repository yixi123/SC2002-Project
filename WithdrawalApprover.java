public class WithdrawalApprover implements IWithdrawalApprover {
    @Override
    public void approveWithdrawal(Applicant applicant) {
        HDBManager manager = (HDBManager) LoginSession.getUser();
        Project project = applicant.getProject();

        if (!project.getManager().equals(manager) ||
            !ActiveProjectResolver.getCurrentProject(manager).equals(project)) {
            System.out.println("❌ Cannot approve withdrawal: Not your active project.");
            return;
        }

        applicant.setStatus(ApplicationStatus.WITHDRAWN);
        System.out.println("✅ Withdrawal approved.");
    }
}