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

public class BasicsItemAdapter extends RecyclerView.Adapter<BasicsItemAdapter.BasicsItemViewHolder> implements Filterable {
    private Context mcontext;
    private ArrayList<BasicsItem> mBasicsList;
    private ArrayList<BasicsItem> mBasicsListFull;

    Drawable ImageIcon;
    ColorGenerator generator = ColorGenerator.MATERIAL;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int postion);
    }

    public void setOnItemClickListener( OnItemClickListener listener){
        mListener = listener;
    }

    public BasicsItemAdapter(Context context,ArrayList<BasicsItem> basicsList ){
        mcontext = context;
        mBasicsList = basicsList;
        mBasicsListFull = new ArrayList<>(basicsList);
    }

    @NonNull
    @Override
    public BasicsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =LayoutInflater.from(mcontext).inflate(R.layout.basics_list_item,parent,false);
        return new BasicsItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BasicsItemViewHolder holder, int position) {
        BasicsItem currentItem = mBasicsList.get(position);

        String topic = currentItem.getTopic();
        String upperString = topic.substring(0,1).toUpperCase() + topic.substring(1);

        ImageIcon = TextDrawable.builder().buildRoundRect(upperString.substring(0,1),generator.getRandomColor(),10);

        holder.mImageView.setImageDrawable(ImageIcon);
        holder.mTextViewBaiscs.setText(upperString);
    }

    @Override
    public int getItemCount() {
        return mBasicsList.size();
    }

    public class BasicsItemViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewBaiscs;
        public ImageView mImageView;

        public BasicsItemViewHolder(View itemView) {
            super(itemView);

            mTextViewBaiscs = itemView.findViewById(R.id.basics_item);
            mImageView  = itemView.findViewById(R.id.iv_basics_item);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
//title="Basic Fragment Search" code belongs to search optionn for the basic fragment
    @Override
    public Filter getFilter() {
        return basicsFilter;
    }
    public Filter basicsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<BasicsItem> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(mBasicsListFull);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(BasicsItem item : mBasicsListFull){
                    if(item.getTopic().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mBasicsList.clear();
            mBasicsList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };
// End Basic Fragment Search
}
