public interface IReportFilterService {
    List<Applicant> applyFilter(List<Applicant> applicants, ReportFilter filter);
}