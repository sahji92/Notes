package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.example.notes.model.Note;

public class NoteActivity extends AppCompatActivity  {

    private String Tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        if(getIntent().hasExtra("selected_note")){
         Note note=getIntent().getParcelableExtra("selected_note");
            Log.d(Tag,note.toString());
        }
    }
}