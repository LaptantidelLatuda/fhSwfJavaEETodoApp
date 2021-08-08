package com.webapp.backend.models;

import java.sql.Date;

public class Todo {
    private Integer idTodo;
    private String titel;
    private String description;
    private Date dueDate;
    private Boolean isDone;

    public Integer getIdTodo() {
        return idTodo;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setIdTodo(Integer idTodo) {
        this.idTodo = idTodo;
    }
}