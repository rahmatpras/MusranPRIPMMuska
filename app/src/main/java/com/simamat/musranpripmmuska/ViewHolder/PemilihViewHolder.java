package com.simamat.musranpripmmuska.ViewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simamat.musranpripmmuska.Interface.ItemClickListener;
import com.simamat.musranpripmmuska.R;

public class PemilihViewHolder extends RecyclerView.ViewHolder {

    public TextView tvPemilihNama, tvPemilihKelas;
    public LinearLayout listPemilih;

    private ItemClickListener itemClickListener;

    public PemilihViewHolder(@NonNull View itemView) {
        super(itemView);

        listPemilih = (LinearLayout) itemView.findViewById(R.id.list_pemilih);
        tvPemilihNama = (TextView) itemView.findViewById(R.id.tv_pemilih_nama);
        tvPemilihKelas = (TextView) itemView.findViewById(R.id.tv_pemilih_kelas);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}
