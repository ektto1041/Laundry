package com.mobileteam.laundry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobileteam.laundry.R;

import java.util.ArrayList;

public class TextureSearchAdapter extends RecyclerView.Adapter<TextureSearchAdapter.ViewHolder>{
    private ArrayList<String> data;
    private LayoutInflater txtInflater;

    public TextureSearchAdapter(Context context, ArrayList<String> data) {
        txtInflater = LayoutInflater.from(context);
        this.data = data;
    }

    public int getItemCount() {
        return data.size();
    } //리스트 항목의 개수를 반환

    public String getItem(int id) {
        return data.get(id);
    } //해당 인덱스의 리스트 항목을 반환

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView texture;

        ViewHolder(View itemView) {
            super(itemView);
            texture = itemView.findViewById(R.id.texture_name);
        }
    }

    public TextureSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = txtInflater.inflate(R.layout.texture_item, parent, false);
        return new TextureSearchAdapter.ViewHolder(view);
    }

    public void onBindViewHolder(TextureSearchAdapter.ViewHolder holder, int position) {
        holder.texture.setText(data.get(position));
    }

}

