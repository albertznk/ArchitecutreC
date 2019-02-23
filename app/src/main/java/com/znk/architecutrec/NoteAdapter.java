package com.znk.architecutrec;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.znk.architecutrec.MainActivity.ADD_NOTE_REQUEST;

public class NoteAdapter extends ListAdapter<Note,NoteAdapter.NoteHolder> {
        private OnItemClickListener listener;
 //   private List<Note>notes=new ArrayList<>();
    private MainActivity activity;
    Context context;
    public NoteAdapter(Context context) {
        super(Diff_Callback);
        this.context=context;
    }

    private static final DiffUtil.ItemCallback<Note>Diff_Callback=new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.getId()==t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.getTitle().equals(t1.getTitle())&&note.getDescription().equals(t1.getDescription())&& note.getPriority()==t1.getPriority();
        }
    };
//
//    protected NoteAdapter(@NonNull AsyncDifferConfig<Note> config) {
//        super(config);
//    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int view_type) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item,viewGroup,false);
         return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {
            Note currentnote=getItem(i);
            noteHolder.textView_title.setText(currentnote.getTitle());
            noteHolder.textView_priority.setText(String.valueOf(currentnote.getPriority()));
            noteHolder.textView_descrpition.setText(currentnote.getDescription());



    }
    public Note getNoteAt(int position){
        return getItem(position);
    }



    class NoteHolder extends RecyclerView.ViewHolder{
        private TextView textView_title;
        private TextView textView_priority;
        private TextView textView_descrpition;

        public NoteHolder(final View itemView) {
            super(itemView);

            textView_descrpition=(TextView)itemView.findViewById(R.id.textview_description);
            textView_priority=(TextView)itemView.findViewById(R.id.textview_priority);
            textView_title=(TextView)itemView.findViewById(R.id.textview_title);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Note current_n=getItem(getAdapterPosition());
                       int position=getAdapterPosition();
                       int id=current_n.getId();
                       String title=current_n.getTitle();
                       String  description=current_n.getDescription();
                       int priority=current_n.getPriority();
                       getNoteAt(position);
                       // Toast.makeText(context,String.valueOf(position),Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(context,UpdateNote.class);
                    intent.putExtra("id",id);
                    intent.putExtra("title",title);
                    intent.putExtra("description",description);
                    intent.putExtra("priority",priority);
                    ((Activity)context).startActivityForResult(intent,MainActivity.UPDATE_NOTE_REQUEST);


                }
            });
        }

    }
    public interface OnItemClickListener {
        void onItemClick(Note note);
    }


}
