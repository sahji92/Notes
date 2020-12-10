package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notes.model.Note;
import com.example.notes.persistance.NoteRepository;
import com.example.notes.util.Utility;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener, View.OnClickListener, TextWatcher {

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
    private NoteRepository noteRepository;
    private Note mFinalNote;
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
      noteRepository=new NoteRepository(this);
      if(getIncomingIntent()){
          //this is new note ...edit mode
          setNewNoteProperties();
          enableEditMode();
      }
      else{
         // this is not a new note ..view mode
          setNoteProperties();
          disableContentInteraction();
      }
      setListeners();
    }
    private void disableContentInteraction(){
        linedEditText.setKeyListener(null);
        linedEditText.setFocusable(false);
        linedEditText.setFocusableInTouchMode(false);
        linedEditText.setCursorVisible(false);
        linedEditText.clearFocus();
    }
    private void enableContentInteraction(){
        linedEditText.setKeyListener(new EditText(this).getKeyListener());
        linedEditText.setFocusable(true);
        linedEditText.setFocusableInTouchMode(true);
        linedEditText.setCursorVisible(true);
        linedEditText.requestFocus();
    }
    private void enableEditMode(){
        back_arrow_container.setVisibility(View.GONE);
        check_container.setVisibility(View.VISIBLE);

        viewTitle.setVisibility(View.GONE);
        editTitle.setVisibility(View.VISIBLE);
        mode=EDIT_MODE_ENABLED;
        enableContentInteraction();
    }
    private void disableEditMode(){
        back_arrow_container.setVisibility(View.VISIBLE);
        check_container.setVisibility(View.GONE);

        viewTitle.setVisibility(View.VISIBLE);
        editTitle.setVisibility(View.GONE);
        mode=EDIT_MODE_DISABLED;
        disableContentInteraction();
        // Check if they typed anything into the note. Don't want to save an empty note.
        String temp = linedEditText.getText().toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");
        if(temp.length() > 0){
            mFinalNote.setTitle(editTitle.getText().toString());
            mFinalNote.setContent(linedEditText.getText().toString());
            String timestamp=Utility.getCurrentTimeStamp();
            // If the note was altered, save it.
            if(!mFinalNote.getContent().equals(initialNote.getContent())
                    || !mFinalNote.getTitle().equals(initialNote.getTitle())){
                saveChanges();
            }
        }
        saveChanges();
    }
    private void hideSoftKeyboard(){
        InputMethodManager imm=(InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view=this.getCurrentFocus();
        if(view==null){
         view=new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
    public void setListeners(){
        linedEditText.setOnTouchListener(this);
        gestureDetector=new GestureDetector(this,this);
        check.setOnClickListener(this);
        viewTitle.setOnClickListener(this);
        backArrow.setOnClickListener(this);
        editTitle.addTextChangedListener(this);
    }
    private boolean getIncomingIntent(){
        if(getIntent().hasExtra("selected_note")){
            initialNote=getIntent().getParcelableExtra("selected_note");
            mFinalNote = new Note();
            mFinalNote.setTitle(initialNote.getTitle());
            mFinalNote.setContent(initialNote.getContent());
            mFinalNote.setTimestamp(initialNote.getTimestamp());
            mFinalNote.setId(initialNote.getId());
            isNewNote=false;
            mode=EDIT_MODE_DISABLED;
            return false;
        }
        isNewNote=true;
        mode=EDIT_MODE_ENABLED;
        return true;
    }
    private void saveChanges(){
     if(isNewNote){
      saveNewNote();
     }
     else {
         updateNote();
     }
    }
    private void updateNote(){
        noteRepository.updateNoteTask(mFinalNote);
    }
    private void saveNewNote(){
        noteRepository.insertNoteTask(mFinalNote);
    }
    private void setNoteProperties(){
        viewTitle.setText(initialNote.getTitle());
        editTitle.setText(initialNote.getTitle());
        linedEditText.setText(initialNote.getContent());
    }
    private void setNewNoteProperties(){
        viewTitle.setText("Note title");
        editTitle.setText("Note title");
        mFinalNote=new Note();
        initialNote=new Note();
        mFinalNote.setTitle("Note Title");
        initialNote.setTitle("Note Title");
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
                hideSoftKeyboard();
                 disableEditMode();
                break;
            case R.id.note_text_title:
                  enableEditMode();
                  editTitle.requestFocus();
                  editTitle.setSelection(editTitle.length());
                break;
            case R.id.toolbar_back_arrow:
                finish();
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("mode",mode);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
         mode=savedInstanceState.getInt("mode");
         if(mode==EDIT_MODE_ENABLED){
           enableEditMode();
         }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
       viewTitle.setText(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}