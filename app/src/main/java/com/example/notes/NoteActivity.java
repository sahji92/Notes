package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notes.model.Note;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener {

    private String Tag;
    private LinedEditText linedEditText;
    private EditText editTitle;
    private TextView viewTitle;
    private boolean isNewNote;
    private Note initialNote;
    private GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
      linedEditText=findViewById(R.id.note_text);
      editTitle=findViewById(R.id.note_edit_title);
      viewTitle=findViewById(R.id.note_text_title);
      if(getIncomingIntent()){
          //this is new note ...edit mode
          setNewNoteProperties();
      }
      else{
         // this is not a new note ..view mode
          setNoteProperties();
      }
      setListeners();
    }
    public void setListeners(){
        linedEditText.setOnTouchListener(this);
        gestureDetector=new GestureDetector(this,this);
    }
    private boolean getIncomingIntent(){
        if(getIntent().hasExtra("selected_note")){
            initialNote=getIntent().getParcelableExtra("selected_note");
            isNewNote=false;
            return false;
        }
        isNewNote=true;
        return true;
    }
    private void setNoteProperties(){
        viewTitle.setText(initialNote.getTitle());
        editTitle.setText(initialNote.getTitle());
        linedEditText.setText(initialNote.getContent());
    }
    private void setNewNoteProperties(){
        viewTitle.setText("Note title");
        editTitle.setText("Note title");
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Toast.makeText(this,"ondoubletap",Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Toast.makeText(this,"ondown",Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Toast.makeText(this,"onscroll",Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Toast.makeText(this,"onlong press",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}