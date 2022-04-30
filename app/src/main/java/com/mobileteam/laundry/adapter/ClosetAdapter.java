package com.mobileteam.laundry.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileteam.laundry.Cloth;
import com.mobileteam.laundry.R;
import com.mobileteam.laundry.domain.Clothes;

import java.util.ArrayList;
import java.util.List;

public class ClosetAdapter extends RecyclerView.Adapter<ClosetAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton closetItemButton;

        ViewHolder(View itemView) {
            super(itemView);
            closetItemButton = (ImageButton) itemView.findViewById(R.id.closet_item);
        }
    }

    public ClosetAdapter(List<Clothes> data) {
        this.data = data;
    }

    private List<Clothes> data;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.closet_item, parent, false);

        view.getLayoutParams().height = parent.getWidth() / 3;

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bitmap image = data.get(position).getImage();
        if(image == null) {
            // 등록된 이미지가 없으면
            holder.closetItemButton.setImageResource(R.drawable.ic_add_circle);
        } else {
            holder.closetItemButton.setImageBitmap(image);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
