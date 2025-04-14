package models.projects;

import java.time.LocalDateTime;

public class Enquiry {
    private int id;
    private String user;
    private String projectID;
    private String category;
    private String content;
    private LocalDateTime timestamp; 
    private int replyId;

    public Enquiry(int id, String user, String projectID, String category, String content, LocalDateTime timestamp, int replyId) {
        this.id = id;
        this.user = user;
        this.projectID = projectID;
        this.category = category;
        this.content = content;
        this.timestamp = timestamp; 
        this.replyId = replyId;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getProjectID() {
        return projectID;
    }

    public String getCategory() {
        return category;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp; 
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("User=%s\nProject=%s\nContent=%s\nTimestamp=%s",
                user, projectID, content, timestamp);
    }
}
