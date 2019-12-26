package com.simamat.musranpripmmuska.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.simamat.musranpripmmuska.Interface.ItemClickListener;
import com.simamat.musranpripmmuska.Model.Pemilih;
import com.simamat.musranpripmmuska.PilihanActivity;
import com.simamat.musranpripmmuska.R;

public class CalonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvCalonNama, tvCalonNomer, tvJumlahSuaraCalon;
    public ImageView ivCalonImage;
    public LinearLayout listCalon;

    private ItemClickListener itemClickListener;

    public CalonViewHolder(@NonNull final View itemView) {
        super(itemView);

        //deskripsi dari layout
        listCalon = (LinearLayout) itemView.findViewById(R.id.list_calon);
        tvCalonNama = (TextView) itemView.findViewById(R.id.calon_nama);
        ivCalonImage = (ImageView) itemView.findViewById(R.id.calon_image);
        tvCalonNomer = (TextView) itemView.findViewById(R.id.tv_calon_nomer_urut);
        tvJumlahSuaraCalon = (TextView) itemView.findViewById(R.id.tv_jumlah_suara_masuk);

        //menghubungkan dengan on Click
        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
