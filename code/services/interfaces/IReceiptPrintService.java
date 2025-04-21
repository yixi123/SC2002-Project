package services.interfaces;

/**
 * Interface for generating booking receipts for successful applicants.
 */
public interface IReceiptPrintService {

    /**
     * Generates a receipt for the given applicant ID.
     *
     * @param applicantId The NRIC of the applicant
     * @return A formatted receipt as a String, or null if no booking exists
     */
    String printReceipt(String applicantId);
}

