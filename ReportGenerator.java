import java.util.List;

public class ReportGenerator implements IReportGenerator {
    private final IReportFilterService filterService = new ReportFilterService();
    private final ManagerController controller;

    public ReportGenerator(ManagerController controller) {
        this.controller = controller;
    }

    @Override
    public void generateReport(Project project, ReportFilter filter) {
        HDBManager manager = controller.getCurrentManager();

        if (!project.getManager().equals(manager)) {
            System.out.println("❌ You can only generate reports for your own projects.");
            return;
        }

        // Fetch all applicants for the given project directly from ApplicantDB
        List<Applicant> applicants = ApplicantDB.getApplicantsByProject(project);
        List<Applicant> filtered = filterService.applyFilter(applicants, filter);

        System.out.println("📊 Filtered Report:");
        for (Applicant a : filtered) {
            System.out.println(a);
        }
    }
}
