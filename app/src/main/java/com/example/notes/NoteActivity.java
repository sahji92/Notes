package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notes.model.Note;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener, View.OnClickListener {

    private String Tag;
    private LinedEditText linedEditText;
    private EditText editTitle;
    private TextView viewTitle;
    private boolean isNewNote;
    private Note initialNote;
    private GestureDetector gestureDetector;
    private static final int EDIT_MODE_ENABLED=1;
    private static final int EDIT_MODE_DISABLED=0;
    private int mode;
    private RelativeLayout back_arrow_container,check_container;
    private ImageButton check,backArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
      linedEditText=findViewById(R.id.note_text);
      editTitle=findViewById(R.id.note_edit_title);
      viewTitle=findViewById(R.id.note_text_title);
      back_arrow_container=findViewById(R.id.back_arrow_container);
      check_container=findViewById(R.id.check_container);
      check=findViewById(R.id.toolbar_check);
      backArrow=findViewById(R.id.toolbar_back_arrow);
      if(getIncomingIntent()){
          //this is new note ...edit mode
          setNewNoteProperties();
          enableEditMode();
      }
      else{
         // this is not a new note ..view mode
          setNoteProperties();
      }
      setListeners();
    }
    private void enableEditMode(){
        back_arrow_container.setVisibility(View.GONE);
        check_container.setVisibility(View.VISIBLE);

        viewTitle.setVisibility(View.GONE);
        editTitle.setVisibility(View.VISIBLE);
        mode=EDIT_MODE_ENABLED;
    }
    private void disableEditMode(){
        back_arrow_container.setVisibility(View.VISIBLE);
        check_container.setVisibility(View.GONE);

        viewTitle.setVisibility(View.VISIBLE);
        editTitle.setVisibility(View.GONE);
        mode=EDIT_MODE_DISABLED;
    }
    public void setListeners(){
        linedEditText.setOnTouchListener(this);
        gestureDetector=new GestureDetector(this,this);
        check.setOnClickListener(this);
        viewTitle.setOnClickListener(this);
    }
    private boolean getIncomingIntent(){
        if(getIntent().hasExtra("selected_note")){
            initialNote=getIntent().getParcelableExtra("selected_note");
            isNewNote=false;
            mode=EDIT_MODE_DISABLED;
            return false;
        }
        isNewNote=true;
        mode=EDIT_MODE_ENABLED;
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
        enableEditMode();
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.toolbar_check:
                 disableEditMode();
                break;
            case R.id.note_text_title:
                  enableEditMode();
                  editTitle.requestFocus();
                  editTitle.setSelection(editTitle.length());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(mode==EDIT_MODE_ENABLED){
          onClick(check);
        }
        else
        super.onBackPressed();
    }
}