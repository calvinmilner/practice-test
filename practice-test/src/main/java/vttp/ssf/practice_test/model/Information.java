package vttp.ssf.practice_test.model;

import jakarta.validation.constraints.Size;

public class Information {
    @Size(max=50)
    private String id;
    @Size(min=10, max=50)
    private String name;
    @Size(max=255)
    private String description;
    
    private String dueDate;
    private String priority;
    private String status;
    private String created;
    private String updated;

    public Information(String id, String name, String description, String dueDate, String priority, String status,
            String created, String updated) {
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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + description + "," + dueDate
                + "," + priority + "," + status + "," + created + "," + updated;
    }
    
}
