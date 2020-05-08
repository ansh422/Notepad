package com.example.notepad.callback;

import com.example.notepad.model.Note;

public interface NoteEventListener {
    /**
     *
     * @param note: note item
     */
    void onNoteClick(Note note);

    /**
     *
      * @param note: item
     */
    void onNoteLongClick(Note note);


}
