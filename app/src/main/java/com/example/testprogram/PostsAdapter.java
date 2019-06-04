package com.example.testprogram;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testprogram.model.Hit;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<Hit> itemList;

    // Constructor of the class
    public PostsAdapter(int layoutId, ArrayList<Hit> itemList) {
        listItemLayout = layoutId;
        this.itemList = itemList;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        TextView postTitle = holder.postTitle;
        TextView createdDate = holder.createdDate;
        postTitle.setText(itemList.get(listPosition).title);
        createdDate.setText(itemList.get(listPosition).created_at);
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView postTitle;
        public TextView createdDate;

        public ViewHolder(View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.post_title);
            createdDate = itemView.findViewById(R.id.created_time);

        }
    }
    public void setHits(ArrayList<Hit> hits){
        itemList = hits;
    }
}