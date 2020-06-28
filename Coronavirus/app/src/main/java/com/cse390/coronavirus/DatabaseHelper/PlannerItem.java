package com.cse390.coronavirus.DatabaseHelper;

import java.util.Date;

public class PlannerItem {

    private String category;
    private String description;
    private boolean completed;
    private Date dueDate;

    public PlannerItem(){

    }

    public PlannerItem(String category, String description, boolean completed, Date dueDate){
        this.category = category;
        this.description = description;
        this.completed = completed;
        this.dueDate = dueDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }
}
