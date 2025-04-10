import java.util.List;
import java.util.stream.Collectors;

public class ReportFilterService implements IReportFilterService {
    @Override
    public List<Applicant> applyFilter(List<Applicant> applicants, ReportFilter filter) {
        return applicants.stream()
            .filter(a -> filter.getMaritalStatus() == null || a.getMaritalStatus().equals(filter.getMaritalStatus()))
            .filter(a -> filter.getFlatType() == null || a.getRequestedFlatType() == filter.getFlatType())
            .collect(Collectors.toList());
    }
}