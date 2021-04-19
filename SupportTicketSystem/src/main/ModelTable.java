package main;

public class ModelTable {

    String id,requesterID,date,description,title,category,priority;

    public ModelTable(String id, String requesterID, String date, String description, String title, String category, String priority) {

        this.id = id;
        this.requesterID = requesterID;
        this.date = date;
        this.description = description;
        this.title = title;
        this.category = category;
        this.priority = priority;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequesterID() {
        return requesterID;
    }

    public void setRequesterID(String requesterID) {
        this.requesterID = requesterID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
