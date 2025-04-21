package models.projects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an enquiry made by a user regarding a BTO project,
 * including the original question and optional reply details.
 */
public class Enquiry {

    /** Unique identifier for the enquiry. */
    private int id;

    /** ID of the user who submitted the enquiry. */
    private String userID;

    /** ID of the project this enquiry refers to. */
    private String projectID;

    /** Content of the enquiry. */
    private String content;

    /** Timestamp of when the enquiry was submitted. */
    private LocalDateTime timestamp;

    /** ID of the user (usually an officer) who replied. */
    private String replierUserID;

    /** Content of the reply. */
    private String replyContent;

    /** Timestamp of when the reply was made. */
    private LocalDateTime replierTimestamp;

    /**
     * Constructs a full Enquiry with both question and reply.
     *
     * @param id               enquiry ID
     * @param userID           ID of the user who asked
     * @param projectID        related project ID
     * @param content          enquiry message
     * @param timestamp        time the enquiry was posted
     * @param replierUserID    ID of the replier
     * @param replyContent     reply message
     * @param replierTimestamp time the reply was posted
     */
    public Enquiry(int id, String userID, String projectID, String content, LocalDateTime timestamp,
                   String replierUserID, String replyContent, LocalDateTime replierTimestamp) {
        this.id = id;
        this.userID = userID;
        this.projectID = projectID;
        this.content = content;
        this.timestamp = timestamp;
        this.replierUserID = replierUserID;
        this.replyContent = replyContent;
        this.replierTimestamp = replierTimestamp;
    }

    /**
     * Constructs an enquiry with no reply yet.
     */
    public Enquiry(int id, String userID, String projectID, String content, LocalDateTime timestamp) {
        this(id, userID, projectID, content, timestamp, null, null, null);
    }

    /**
     * Constructs an enquiry using the current time as the timestamp.
     */
    public Enquiry(int id, String userID, String projectID, String content) {
        this(id, userID, projectID, content, LocalDateTime.now(), null, null, null);
    }

    /**
     * Gets the unique ID of this enquiry.
     * @return enquiry ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the user ID of the person who created the enquiry.
     * @return user ID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Gets the project ID this enquiry is associated with.
     * @return project ID
     */
    public String getProjectID() {
        return projectID;
    }

    /**
     * Gets the content of the enquiry.
     * @return enquiry content
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the timestamp when the enquiry was submitted.
     * @return enquiry timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp for the enquiry.
     * @param timestamp the new timestamp
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Updates the content of the enquiry and refreshes the timestamp.
     * @param content new enquiry content
     */
    public void setContent(String content) {
        this.content = content;
        setTimestamp(LocalDateTime.now());
    }

    /**
     * Gets the user ID of the officer or manager who replied.
     * @return replier's user ID
     */
    public String getReplierUserID() {
        return replierUserID;
    }

    /**
     * Gets the content of the reply.
     * @return reply content
     */
    public String getReplyContent() {
        return replyContent;
    }

    /**
     * Gets the timestamp when the reply was made.
     * @return reply timestamp
     */
    public LocalDateTime getReplierTimestamp() {
        return replierTimestamp;
    }

    /**
     * Sets the user ID of the replier.
     * @param replierUserID the user ID of the person replying
     */
    public void setReplierUserID(String replierUserID) {
        this.replierUserID = replierUserID;
    }

    /**
     * Sets the reply message.
     * @param replyContent the reply text
     */
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    /**
     * Sets the timestamp for when the reply was made.
     * @param replierTimestamp the timestamp of the reply
     */
    public void setReplierTimestamp(LocalDateTime replierTimestamp) {
        this.replierTimestamp = replierTimestamp;
    }

    /**
     * Updates the reply content, replier ID, and sets current timestamp.
     * @param replierUserID ID of the user replying
     * @param replyContent  reply content
     */
    public void setReply(String replierUserID, String replyContent) {
        this.replierUserID = replierUserID;
        this.replyContent = replyContent;
        this.replierTimestamp = LocalDateTime.now();
    }

    /**
     * Formats the enquiry and reply into a human-readable string.
     * @return formatted string containing enquiry and reply info
     */
    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedTimestamp = timestamp.format(dateFormatter);
        String formattedReplierTimestamp = (replierTimestamp == null) ? "" : replierTimestamp.format(dateFormatter);

        String enq = String.format("User   =%-10s  [%s]\nProject=%s\n%s\n", userID, formattedTimestamp, projectID, content);
        String rep = (replierUserID == null) ? "No Reply" : String.format("Replier =%-10s  [%s]\n%s", replierUserID, formattedReplierTimestamp, replyContent);
        return enq + "\n" + rep;
    }

    /**
     * Provides a simplified string output that optionally excludes project name.
     *
     * @param displayProjectName true to include project name, false to omit
     * @return simplified formatted string
     */
    public String toString(boolean displayProjectName) {
        if (displayProjectName) {
            return toString();
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedTimestamp = timestamp.format(dateFormatter);
        String formattedReplierTimestamp = (replierTimestamp == null) ? "" : replierTimestamp.format(dateFormatter);

        String enq = String.format("User    =%-10s  [%s]\n", userID, formattedTimestamp);
        String rep = (replierUserID == null) ? "No Reply" : String.format("Replier = %-10s  [%s]\n%s", replierUserID, formattedReplierTimestamp, replyContent);
        return enq + "\n" + rep;
    }
}
