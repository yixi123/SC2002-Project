package models.projects;

import java.time.LocalDateTime;

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

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public String toString() {
        String enq = String.format("User=%s [%s]\nProject=%s\n%s\n", userID, timestamp, projectID, content);
        String rep = (replierUserID == null)? "No Reply" : String.format("Replier=%s [%s]\n %s", replierUserID, replierTimestamp, replyContent);
        return enq + rep;
    }

}
