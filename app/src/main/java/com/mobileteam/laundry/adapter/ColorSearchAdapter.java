package com.mobileteam.laundry.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobileteam.laundry.R;

import java.util.ArrayList;

public class ColorSearchAdapter extends RecyclerView.Adapter<ColorSearchAdapter.ViewHolder> {

    static private String[] colData;
    private LayoutInflater colInflater;

    public ColorSearchAdapter(Context context, ArrayList<String> data) {
        colInflater = LayoutInflater.from(context);
        colData = data.toArray(new String[0]);
    }

    public void update(String[] list) {
        colData = list;
    } //리스트 항목을 업데이트

    public int getItemCount() {
        return colData.length;
    } //리스트 항목의 개수를 반환

    public String getItem(int id) {
        return colData[id];
    } //해당 인덱스의 리스트 항목을 반환

    public int getColor(String name) {  //String 값에 해당하는 int 값을 반환
        switch (name) {
            case "White": return Color.WHITE;
            case "Gray": return Color.GRAY;
            case "Black": return Color.BLACK;
            case "Red": return Color.RED;
            case "Green": return Color.GREEN;
            case "Blue": return Color.BLUE;
            case "Cyan": return Color.CYAN;
            case "Yellow": return Color.YELLOW;
            case "Magenta": return Color.MAGENTA;
        }
        return 000000;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView color;
        ViewHolder(View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.colorcheck);
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = colInflater.inflate(R.layout.color_item, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.color.setBackgroundColor(getColor(colData[position]));
    }

}
