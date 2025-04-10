public class ReportGenerator implements IReportGenerator {
    private final IReportFilterService filterService = new ReportFilterService();

    @Override
    public void generateReport(Project project, ReportFilter filter) {
        HDBManager manager = (HDBManager) LoginSession.getUser();

        if (!project.getManager().equals(manager)) {
            System.out.println("❌ You can only generate reports for your own projects.");
            return;
        }

        List<Applicant> applicants = ApplicantDB.getApplicantsByProject(project);
        List<Applicant> filtered = filterService.applyFilter(applicants, filter);

        System.out.println("📊 Filtered Report:");
        for (Applicant a : filtered) {
            System.out.println(a);
        }
    }
}