package com.example.notes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.notes.adapters.NotesRecyclerAdapter;
import com.example.notes.model.Note;
import com.example.notes.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity implements NotesRecyclerAdapter.OnNoteListener {
    private RecyclerView recyclerView;
    private ArrayList<Note> notes=new ArrayList<>();
    private NotesRecyclerAdapter adapter;
    private String Tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        recyclerView=findViewById(R.id.recyclerView);
        initRecyclerView();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle("Notes");
        addFakeNotes();
    }
    public void addFakeNotes(){
        for(int i=0;i<30;i++) {
            Note note = new Note();
            note.setTitle("AAA" + i);
            note.setTimestamp("Jan 2019");
            note.setContent("SSSSSSSSSSSSSSS");
            notes.add(note);
        }
    }
    public void initRecyclerView(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        adapter=new NotesRecyclerAdapter(notes,this);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator=new VerticalSpacingItemDecorator(10);
        recyclerView.addItemDecoration(verticalSpacingItemDecorator);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent=new Intent(this,NoteActivity.class);
        intent.putExtra("selected_note",notes.get(position));
        startActivity(intent);
    }
}