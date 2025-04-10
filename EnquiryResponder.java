public class EnquiryResponder implements IEnquiryResponder {
    private final IEnquiryProjectValidator validator = new EnquiryProjectValidator();

    @Override
    public void replyToEnquiry(Enquiry enquiry, String response) {
        HDBManager manager = (HDBManager) LoginSession.getUser();

        if (!validator.canReplyTo(enquiry, manager)) {
            System.out.println("❌ You are not allowed to reply to this enquiry.");
            return;
        }

        enquiry.setResponse(response);
        System.out.println("📬 Enquiry responded.");
    }
}