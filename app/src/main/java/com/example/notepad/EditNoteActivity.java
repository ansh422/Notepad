package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.notepad.callback.NoteEventListener;
import com.example.notepad.db.NotesDB;
import com.example.notepad.db.NotesDao;
import com.example.notepad.model.Note;

import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {
    private EditText inputNote;
    private NotesDao dao;
    private Note temp;
    public static final String NOTE_EXTRA_Key= "note_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        inputNote=findViewById(R.id.input_notes);
        dao=NotesDB.getInstance(this).notesDao();

        if(getIntent().getExtras()!=null){
            int id=getIntent().getExtras().getInt(NOTE_EXTRA_Key,0);
            temp=dao.getNoteById(id);
            inputNote.setText(temp.getNoteText());
        }else{
            temp= new Note();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_note_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.save_note)
            onSaveNote();
        return super.onOptionsItemSelected(item);
    }

    private void onSaveNote() {
        //TODO 7/5/2020 SAve Note
        String text= inputNote.getText().toString();
        if(!text.isEmpty()){
            long date=new Date().getTime(); // get current time
           // Note note= new Note(text,date);  because temp now has Note object

            // if note exits update else create new
            temp.setNoteText(text);
            temp.setNoteDate(date);

            if(temp.getId()==-1)
            dao.insertNote(temp);  // insert and save note to database
            else{
                dao.updateNote(temp);
            }
            finish(); // return to the MainActivity
        }

    }
}
