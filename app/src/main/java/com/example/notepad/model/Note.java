package com.example.notepad.model;


import androidx.annotation.ColorLong;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id=-1;
    @ColumnInfo(name = "text")
    private String noteText;
    @ColumnInfo(name = "date")
    private long noteDate;

    public Note(){

    }
    public Note(String noteText, long noteDate) {
        this.noteText = noteText;
        this.noteDate = noteDate;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public long getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(long noteDate) {
        this.noteDate = noteDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString(){
        return "Note{"+
                "id="+id+
                ",noteDate="+noteDate+
                '}';
    }
}
