package com.example.notepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.notepad.adapters.NotesAdapter;
import com.example.notepad.callback.NoteEventListener;
import com.example.notepad.db.NotesDB;
import com.example.notepad.db.NotesDao;
import com.example.notepad.model.Note;
import com.example.notepad.utils.NoteUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteEventListener {
    private static  final String tag="MainActivity";
    private RecyclerView recyclerView;
    private ArrayList<Note> notes;
    private NotesAdapter adapter;
    private NotesDao dao;
    private String NOTE_EXTRA_Key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // init recycler view
        recyclerView=findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //loadNotes();

        // init fab view
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: 5/6/2020 add new note
                //Snackbar.make(view, "Your note will be saved", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
                //Save("Note1.txt");

                onAddNewNote();
            }
        });
        //EditText1=(EditText)findViewById(R.id.EditText1);
        //EditText1.setText(Open("Note1.txt"));
        dao= NotesDB.getInstance(this).notesDao();
    }

    private void loadNotes() {
        this.notes= new ArrayList<>();
        List<Note> list = dao.getNotes(); // get All NOtes from database

        this.notes.addAll(list);
        this.adapter = new NotesAdapter(this,notes);
        this.adapter.setListener(this);
        this.recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }

    private void onAddNewNote() {
        // TODO: 5/7/2020 start EditNoteActivity
        startActivity(new Intent(this,EditNoteActivity.class));
    }

   /* public void Save(String filename){
        try{
            OutputStreamWriter out=new OutputStreamWriter(openFileOutput(filename,0));
            out.write(EditText1.getText().toString());
            out.close();
            Toast.makeText(this,"Note Saved!",Toast.LENGTH_SHORT).show();
        }
        catch(Throwable t){
            Toast.makeText(this,"Exception: "+t.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public boolean FileExists(String fname){
        File file=getBaseContext().getFileStreamPath(fname);
        return file.exists();
    } */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*public String Open(String fileName){
        String content="";
        if(FileExists(fileName)){
            try{
                InputStream in =openFileInput(fileName);
                if(in!=null){
                    BufferedReader br=new BufferedReader(new InputStreamReader(in));
                    String str="";
                    StringBuilder buf= new StringBuilder();
                    while((str=br.readLine())!=null){
                        buf.append(str+"\n");
                    }in.close();
                    content=buf.toString();
                }
            }catch (java.io.FileNotFoundException e){}catch (Throwable t){
                Toast.makeText(this,"Exception: "+t.toString(),Toast.LENGTH_LONG).show();
            }
        }
        return content;
    } */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadNotes();
    }

    @Override
    public void onNoteClick(Note note) {
        // TODO: 5/7/2020 note opens

        //Toast.makeText(this,note.getId(),Toast.LENGTH_SHORT).show();
        Intent edit=new Intent(this,EditNoteActivity.class);
        edit.putExtra(NOTE_EXTRA_Key,note.getId());
        startActivity(edit);
       // Log.d(tag,"onNoteClick: "+note.toString()); to check if click was working
    }

    @Override
    public void onNoteLongClick(final Note note) {
        // TODO: 5/7/2020 note deletes, shares etc

        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // TODO: 5/7/2020 delete Note rom database and refresh
                        dao.deleteNote(note); // delete
                        loadNotes(); // refreshed
                    }
                })
                .setNegativeButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // TODO: 5/7/2020 share note text
                        Intent share=new Intent(Intent.ACTION_SEND);
                        // Make Logic to SHAREEE
                        String text=note.getNoteText()+"\n Create on: "+
                                NoteUtils.dateFromLong(note.getNoteDate());
                            //Log.d(tag,"onClick:"+text);
                                share.setType("text/plain");
                                share.putExtra(Intent.EXTRA_TEXT,text);
                                startActivity(share);
                    }
                })
                .create()
                .show();
        //Log.d(tag,"OnNoteLongClick: "+note.getId());
    }
}
