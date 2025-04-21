package models.enums;

/**
 * Enum representing the various filter categories that can be applied
 * to search or display BTO projects in the system.
 */
public enum FilterCategory {
  /** Filter by project ID. */
  PROJECT_ID,

  /** Filter by project name. */
  PROJECT_NAME,

  /** Filter by opening date of the application. */
  OPENING_DATE,

  /** Filter by closing date of the application. */
  CLOSING_DATE,

  /** Filter by neighborhood or location. */
  NEIGHBORHOOD,

  /** Filter by flat type (e.g., 2-room, 3-room). */
  FLAT_TYPE
}
