package com.example.sagar.democp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

public class ProgramItemAdapter extends RecyclerView.Adapter<ProgramItemAdapter.ProgramItemViewHolder> implements Filterable {
    private Context mcontext;
    private ArrayList<ProgramItem> mProgramList;
    private ArrayList<ProgramItem> mProgramListFull;

    Drawable ImageIcon;
    ColorGenerator generator = ColorGenerator.MATERIAL;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class ProgramItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewProgram;
        public ImageView mImageViewProgram;

        public ProgramItemViewHolder(View itemView) {
            super(itemView);

            mTextViewProgram = itemView.findViewById(R.id.program_item);
            mImageViewProgram = itemView.findViewById(R.id.iv_program_item);

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

    public ProgramItemAdapter(Context context, ArrayList<ProgramItem> ProgramList) {
        mcontext = context;
        mProgramList = ProgramList;
        mProgramListFull = new ArrayList<>(mProgramList);
    }

    @NonNull
    @Override
    public ProgramItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.program_list_item, parent, false);
        return new ProgramItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramItemViewHolder holder, int position) {
        ProgramItem currentItem = mProgramList.get(position);

        String topic = currentItem.getTopic();
        String upperString = topic.substring(0, 1).toUpperCase() + topic.substring(1);

        ImageIcon = TextDrawable.builder().buildRoundRect(upperString.substring(0, 1), generator.getRandomColor(), 10);

        holder.mTextViewProgram.setText(topic);
        holder.mImageViewProgram.setImageDrawable(ImageIcon);
    }

    @Override
    public int getItemCount() {
        return mProgramList.size();
    }

    @Override
    public Filter getFilter() {
        return mProgramFilter;
    }

    public Filter mProgramFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ProgramItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mProgramListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ProgramItem item : mProgramListFull) {
                    if (item.getTopic().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mProgramList.clear();
            mProgramList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };
}
