package com.example.mmdouhapps.activites;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.mmdouhapps.R;
import com.example.mmdouhapps.adapters.NoteAdapter;
import com.example.mmdouhapps.database.NotesDatabase;
import com.example.mmdouhapps.entities.Note;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static int REQUEST_CODE_ADD_NOTE = 1;


    ImageView imageAddNoteMain ;
    RecyclerView notesRecycler ;
    private List<Note> noteList ;
    private NoteAdapter noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageAddNoteMain = findViewById(R.id.addNoteMain);
        notesRecycler = findViewById(R.id.notes_RecyclerView);




        imageAddNoteMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(getApplicationContext() , CreateNoteActivity.class) , REQUEST_CODE_ADD_NOTE
                );
            }
        });

        notesRecycler.setLayoutManager(new StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL));

        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(noteList);
        notesRecycler.setAdapter(noteAdapter);
        getNotes();

    }


    private void getNotes (){

        @SuppressLint("StaticFieldLeak")
        class getNoteTask extends AsyncTask<Void , Void , List<Note>>{


            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase.getNotesDatabase(getApplicationContext()).noteDeo().getAllNote();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);

                if (noteList.size() == 0){
                    noteList.addAll(notes);
                    noteAdapter.notifyDataSetChanged();
                }
                else {
                    noteList.add(0 , notes.get(0));
                    noteAdapter.notifyItemInserted(0);
                }
                notesRecycler.smoothScrollToPosition(0);

            }

        }

        new getNoteTask().execute();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK){
            getNotes();
        }


    }
}