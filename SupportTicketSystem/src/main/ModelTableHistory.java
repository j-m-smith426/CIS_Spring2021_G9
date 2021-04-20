package main;

public class ModelTableHistory {

    String id,requesterID,description,dueDate;

    public ModelTableHistory(String date, String id, String requesterID, String description) {

        this.id = id;
        this.requesterID = requesterID;
        this.dueDate = date;
        this.description = description;
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
        return dueDate;
    }

    public void setDate(String date) {
        this.dueDate = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   
}
