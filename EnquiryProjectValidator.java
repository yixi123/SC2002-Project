import java.time.LocalDate;

public class EnquiryProjectValidator implements IEnquiryProjectValidator {
    @Override
    public boolean canReplyTo(Enquiry enquiry, HDBManager manager) {
        Project project = enquiry.getProject();
        if (!project.getManager().equals(manager)) return false;

        LocalDate today = LocalDate.now();
        boolean isActive = project.isVisible()
            && !today.isBefore(project.getOpeningDate())
            && !today.isAfter(project.getClosingDate());

        boolean isPast = !project.isVisible(); // allow for past-project reply
        return isActive || isPast;
    }
}