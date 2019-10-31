package com.blk.testcolorchooser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blk.testcolorchooser.scenarios.LedScenario;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<LedScenario> itemList;
    private Context context;
    public RecyclerViewAdapter(Context context, ArrayList<LedScenario> itemList) {
        this.itemList = itemList;
        this.context = context;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scheme, null);
        RecyclerViewHolder rcv = new RecyclerViewHolder(layoutView);
        return rcv;
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.itemText.setText(itemList.get(position).getNameMode());
        holder.itemColor.setText(itemList.get(position).getColorMode());
        holder.itemBright.setText(itemList.get(position).getBrigthness());
    }
    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
