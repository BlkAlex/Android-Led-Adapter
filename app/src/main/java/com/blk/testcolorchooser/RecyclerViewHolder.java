package com.blk.testcolorchooser;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView itemColor;
    public TextView itemBright;
    public TextView itemText;
    public RecyclerViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        itemColor = itemView.findViewById(R.id.color);
        itemText = itemView.findViewById(R.id.name);
        itemBright = itemView.findViewById(R.id.bright);
    }
    @Override
    public void onClick(View view) {
    }
}