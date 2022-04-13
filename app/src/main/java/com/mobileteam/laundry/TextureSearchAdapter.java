package com.mobileteam.laundry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class TextureSearchAdapter extends RecyclerView.Adapter<TextureSearchAdapter.ViewHolder>{
    static private String[] txtData;
    private LayoutInflater txtInflater;

    TextureSearchAdapter(Context context, ArrayList<String> data) {
        txtInflater = LayoutInflater.from(context);
        txtData = data.toArray(new String[0]);
    }

    public void update(ArrayList<String> list) {
        txtData = list.toArray(new String[0]);
    } //리스트 항목을 업데이트

    public int getItemCount() {
        return txtData.length;
    } //리스트 항목의 개수를 반환

    public String getItem(int id) {
        return txtData[id];
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
        holder.texture.setText(txtData[position]);
    }

}

