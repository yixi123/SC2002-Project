public interface IEnquiryProjectValidator {
    boolean canReplyTo(Enquiry enquiry, HDBManager manager);
}