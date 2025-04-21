package models.enums;

/**
 * Enum representing the status of a project application by an applicant.
 */
public enum ProjectAppStat {
  /** Application is pending review. */
  PENDING,

  /** Application has been approved and is successful. */
  SUCCESSFUL,

  /** Application has been reviewed and rejected. */
  UNSUCCESSFUL,

  /** Application has been confirmed/booked. */
  BOOKED,

  /** Applicant has requested to withdraw the application. */
  WITHDRAW_REQ,

  /** Application has been withdrawn. */
  WITHDRAWN
}
