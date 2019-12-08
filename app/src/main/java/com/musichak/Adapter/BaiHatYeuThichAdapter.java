package com.musichak.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.musichak.Activity.PlayMusicActivity;
import com.musichak.Model.BaiHat;
import com.musichak.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BaiHatYeuThichAdapter extends RecyclerView.Adapter<BaiHatYeuThichAdapter.ViewHolder> {

    Context context;
    ArrayList<BaiHat> listBaiHatYeuThich;

    public BaiHatYeuThichAdapter(Context context, ArrayList<BaiHat> listBaiHatYeuThich) {
        this.context = context;
        this.listBaiHatYeuThich = listBaiHatYeuThich;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_bai_hat_yeuthich,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = listBaiHatYeuThich.get(position);
        holder.txtTenBaiHat.setText(baiHat.getTenBaiHat());
        holder.txtCaSi.setText(baiHat.getCaSi());
        Picasso.with(context).load(baiHat.getHinhBaiHat()).into(holder.imageViewHinh);
    }

    @Override
    public int getItemCount() {
        return listBaiHatYeuThich.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewHinh, imageViewLuotThich;
        TextView txtTenBaiHat, txtCaSi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewHinh = itemView.findViewById(R.id.imageviewBaiHatYeuThich);
            imageViewLuotThich = itemView.findViewById(R.id.imageviewLuotThich);
            txtTenBaiHat = itemView.findViewById(R.id.textviewBaiHatYeuThich);
            txtCaSi = itemView.findViewById(R.id.textviewCaSiBaiHatYeuThich);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayMusicActivity.class);
                    intent.putExtra("cakhuc",listBaiHatYeuThich.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
