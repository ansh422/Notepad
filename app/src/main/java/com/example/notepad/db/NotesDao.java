package com.example.notepad.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notepad.model.Note;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // if the Note exists, replace it
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Update
    void updateNote(Note note);

    @Query("SELECT * FROM notes") // lists all notes from database
    List<Note> getNotes();

    @Query("SELECT * FROM notes WHERE id = :noteId") // get Note by Id
    Note getNoteById(int noteId);

    @Query("DELETE FROM notes WHERE id = :noteId")
    void deleteNoteById(int noteId);

}
