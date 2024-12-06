package vttp.ssf.practice_test.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Size;

public class Information {
    @Size(max=50)
    private String id;
    @Size(min=10, max=50)
    private String name;
    @Size(max=255)
    private String description;
    // @Pattern(pattern="EEE, dd-MM-yyyy")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;
    private String priority;
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updated;

    public Information() {}

    public Information(String id, String name, String description, Date dueDate, String priority, String status,
            Date created, Date updated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + description + "," + dueDate
                + "," + priority + "," + status + "," + created + "," + updated;
    }
    
    // Setting date format followed by converting date to epochMilliseconds value to store in redis
    public static long stringDateToEpochMilliSeconds(String jsonDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MM/dd/yyyy");
            long convertedDate = (sdf.parse(jsonDate)).getTime();
            return convertedDate;
    }

    public static Date epochMilliSecondsToDate(String jsonDate) {
        long date = Long.parseLong(jsonDate);
        Date convertedToDate = new Date(date);
        return convertedToDate;
    }
}
