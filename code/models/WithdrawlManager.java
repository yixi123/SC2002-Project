public class WithdrawalManager implements IWithdrawalManager {
    private final ManagerController controller;

    public WithdrawalManager(ManagerController controller) {
        this.controller = controller;
    }

    @Override
    public void handleWithdrawal(Applicant applicant, boolean approve) {
        HDBManager manager = controller.getCurrentManager();
        Project project = applicant.getProject();

        if (!project.getManager().equals(manager) || !project.isVisible()) {
            System.out.println("❌ Not your visible project.");
            return;
        }

        if (approve) {
            applicant.setStatus(ApplicationStatus.WITHDRAWN);
            System.out.println("✅ Withdrawal approved.");
        } else {
            applicant.setStatus(ApplicationStatus.REJECTED);
            System.out.println("❌ Withdrawal rejected.");
        }
    }
}
