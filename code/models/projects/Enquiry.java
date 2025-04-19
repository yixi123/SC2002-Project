package models.projects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Enquiry {
    private int id;
    private String userID;
    private String projectID;
    private String content;
    private LocalDateTime timestamp; 
    private String replierUserID;
    private String replyContent;
    private LocalDateTime replierTimestamp;

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

    public Enquiry(int id, String userID, String projectID, String content, LocalDateTime timestamp) {
        this(id, userID, projectID, content, timestamp, null, null, null);
    }

    public Enquiry(int id, String userID, String projectID, String content) {
        this(id, userID, projectID, content, LocalDateTime.now(), null, null, null);
    }

    public int getId() {
        return id;
    }

    public String getUserID() {
        return userID;
    }

    public String getProjectID() {
        return projectID;
    }


    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp; 
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp; 
    }

    public void setContent(String content) {
        this.content = content;
        setTimestamp(LocalDateTime.now());
    }

    public String getReplierUserID() {
        return replierUserID;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public LocalDateTime getReplierTimestamp() {
        return replierTimestamp;
    }

    public void setReplierUserID(String replierUserID) {
        this.replierUserID = replierUserID;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public void setReplierTimestamp(LocalDateTime replierTimestamp) {
        this.replierTimestamp = replierTimestamp;
    }

    public void setReply(String replierUserID, String replyContent) {
        this.replierUserID = replierUserID;
        this.replyContent = replyContent;
        this.replierTimestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedTimestamp = timestamp.format(dateFormatter);
        String formattedReplierTimestamp = (replierTimestamp == null) ? "" : replierTimestamp.format(dateFormatter);

        String enq = String.format("User   =%-10s  [%s]\nProject=%s\n%s\n", userID, formattedTimestamp, projectID, content);
        String rep = (replierUserID == null)? "No Reply" : String.format("Replier =%-10s  [%s]\n%s", replierUserID, formattedReplierTimestamp, replyContent);
        return enq + "\n" + rep;
    }

    public String toString(boolean displayProjectName) {
        if (displayProjectName) {
            return toString();
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedTimestamp = timestamp.format(dateFormatter);
        String formattedReplierTimestamp = (replierTimestamp == null) ? "" : replierTimestamp.format(dateFormatter);

        String enq = String.format("User    =%-10s  [%s]\n", userID, formattedTimestamp, content);
        String rep = (replierUserID == null)? "No Reply" : String.format("Replier = %-10s  [%s]\n%s", replierUserID, formattedReplierTimestamp, replyContent);
        return enq + "\n" + rep;
    }

}
