package lk.ruhcs.explorewild;

public class IssueData {
    private int issueId;
    private String description;

    public IssueData(int issueId, String description) {
        this.issueId = issueId;
        this.description = description;
    }

    public int getIssueId() {
        return issueId;
    }

    public String getDescription() {
        return description;
    }
}
