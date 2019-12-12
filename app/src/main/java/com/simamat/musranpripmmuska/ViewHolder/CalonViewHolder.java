package com.simamat.musranpripmmuska.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simamat.musranpripmmuska.Interface.ItemClickListener;
import com.simamat.musranpripmmuska.R;

public class CalonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvCalonNama;
    public ImageView ivCalonImage;
    public LinearLayout listCalon;

    private ItemClickListener itemClickListener;

    public CalonViewHolder(@NonNull View itemView) {
        super(itemView);

        listCalon = (LinearLayout) itemView.findViewById(R.id.list_calon);
        tvCalonNama = (TextView) itemView.findViewById(R.id.calon_nama);
        ivCalonImage = (ImageView) itemView.findViewById(R.id.calon_image);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(),false);

    }
}
