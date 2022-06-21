package com.project.board;

public class Board {
    private String title;
    private String content;
    private String name;
    private String createdTs;
    private String updatedTs;
    private String deletedTs;

    public Board() {}

    public Board(String title, String content, String name, String createdTs, String updatedTs, String deletedTs) {
        this.title = title;
        this.content = content;
        this.name = name;
        this.createdTs = createdTs;
        this.updatedTs = updatedTs;
        this.deletedTs = deletedTs;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getCreatedTs() {
        return createdTs;
    }

    public String getUpdatedTs() {
        return updatedTs;
    }

    public String getDeletedTs() {
        return deletedTs;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedTs(String createdTs) {
        this.createdTs = createdTs;
    }

    public void setUpdatedTs(String updatedTs) { this.updatedTs = updatedTs;}

    public void setDeletedTs(String deletedTs) {
        this.deletedTs = deletedTs;
    }

}