package lk.ruhcs.explorewild;

public class TaskData {
    private Integer taskId;
    private Integer keeperId;
    private String status;
    private String Description;

    public TaskData(Integer taskId, Integer keeperId, String status,String Description) {
        this.taskId = taskId;
        this.keeperId = keeperId;
        this.status = status;
        this.Description = Description;
    }

    public TaskData(Integer taskId, String status,String Description) {
        this.taskId = taskId;
//        this.keeperId = keeperId;
        this.status = status;
        this.Description = Description;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public Integer getKeeperId() {
        return keeperId;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return Description;
    }
}
