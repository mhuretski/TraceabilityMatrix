package tracibility.zephyr.file.entity;

import java.time.ZonedDateTime;

public class ExecutionStatus {

    private ZonedDateTime executedOnDate;
    private String executedStatus;

    public ExecutionStatus(ZonedDateTime executedOnDate, String executedStatus) {
        this.executedOnDate = executedOnDate;
        this.executedStatus = executedStatus;
    }

    public void update(ZonedDateTime executedOnDate, String executedStatus) {
        this.executedStatus = executedStatus;
        this.executedOnDate = executedOnDate;
    }

    public ZonedDateTime getExecutedOnDate() {
        return executedOnDate;
    }

    public String getExecutedStatus() {
        return executedStatus;
    }

    @Override
    public String toString() {
        return "{" +
                "executedOnDate=" + executedOnDate +
                ", executedStatus='" + executedStatus + '\'' +
                '}';
    }

}
