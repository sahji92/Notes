package com.example.notes.adapters;

import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.model.Note;
import com.example.notes.util.Utility;

import java.util.ArrayList;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder>{
      private ArrayList<Note> notes=new ArrayList<>();
    private String Tag;
     private OnNoteListener onNoteListener;
    public NotesRecyclerAdapter(ArrayList<Note> notes,OnNoteListener onNoteListener) {
        this.notes=notes;
        this.onNoteListener=onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note_list_item,parent,false);
        return new ViewHolder(view,onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            String month=notes.get(position).getTimestamp().substring(0,2);
            month= Utility.getMonthFromNumber(month);
            String year=notes.get(position).getTimestamp().substring(3);
            String timestamp=month+""+year;
            holder.title.setText(notes.get(position).getTitle());
            holder.timestamp.setText(timestamp);
        }
        catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       TextView title;
       TextView timestamp;
       OnNoteListener onNoteListener;
        public ViewHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);
            title=itemView.findViewById(R.id.note_title);
            timestamp=itemView.findViewById(R.id.note_timestamp);
            this.onNoteListener=onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
   public interface OnNoteListener{
     void onNoteClick(int position);
    }
}
