package com.mittas.notes.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String bodyText;

    private Date createdAt;

    private boolean isPinned;

    public Note(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Note(String title, String bodyText, Date createdAt) {
        this.title = title;
        this.bodyText = bodyText;
        this.createdAt = createdAt;
    }

    //    /**
//     * Firebase db will get the row id differently, so exclude id field
//     */
//    @Exclude
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }
}

