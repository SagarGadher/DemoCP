package com.example.sagar.democp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PatternProgramAdapter extends RecyclerView.Adapter <PatternProgramAdapter.ProgramItemViewHolder>{

    private Context mcontext;
    private ArrayList<PatternProgramItem> mProgramList;

    private PatternProgramAdapter.OnItemClickListener mListener;

    @NonNull
    @Override
    public ProgramItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.pattern_program_list_item, parent, false);
        return new ProgramItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramItemViewHolder holder, int position) {
        PatternProgramItem currentItem = mProgramList.get(position);

        int ivImage = currentItem.getImage();
        //holder.mTextViewProgram.setText(currentItem.getTopic());
        holder.mImageViewProgram.setImageResource(ivImage);
    }

    @Override
    public int getItemCount() {
        return mProgramList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(PatternProgramAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public class ProgramItemViewHolder extends RecyclerView.ViewHolder {
        //public TextView mTextViewProgram;
        public ImageView mImageViewProgram;

        public ProgramItemViewHolder(View itemView) {
            super(itemView);

            //mTextViewProgram = itemView.findViewById(R.id.program_item);
            mImageViewProgram = itemView.findViewById(R.id.iv_pattern_program_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public PatternProgramAdapter(Context mcontext, ArrayList<PatternProgramItem> mProgramList) {
        this.mcontext = mcontext;
        this.mProgramList = mProgramList;
    }
}
