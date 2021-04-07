package com.example.mmdouhapps.adapters;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mmdouhapps.R;
import com.example.mmdouhapps.entities.Note;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> notes ;

    public NoteAdapter(List<Note> notes ) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_note , parent , false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setNote(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle , textSubtitle , textDataTime ;
        LinearLayout layoutNote ;
        RoundedImageView imageNote ;

         ViewHolder(@NonNull View itemView) {
            super(itemView);
             textTitle = itemView.findViewById(R.id.textTitle);
             textSubtitle = itemView.findViewById(R.id.textSubtitle);
             textDataTime = itemView.findViewById(R.id.textDataTime);
             layoutNote = itemView.findViewById(R.id.layout_note);
             imageNote = itemView.findViewById(R.id.imageNote);
        }

        void setNote (Note note){
             textTitle.setText(note.getTitle());

             if (note.getSubtitle().trim().isEmpty()){
                 textSubtitle.setVisibility(View.GONE);
             }else {
                 textSubtitle.setText(note.getSubtitle());
             }

             textDataTime.setText(note.getDataTime());

            GradientDrawable gradientDrawable = (GradientDrawable) layoutNote.getBackground();

            if (note.getColor() != null){
                gradientDrawable.setColor(Color.parseColor(note.getColor()));
            }else {
                gradientDrawable.setColor(Color.parseColor("#333333"));
            }

            if (note.getImagePath() != null) {
             imageNote.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
             imageNote.setVisibility(View.VISIBLE);
            }
            else {
                imageNote.setVisibility(View.GONE);
            }

        }

    }


}
