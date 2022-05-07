package com.mobileteam.laundry.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobileteam.laundry.R;
import com.mobileteam.laundry.enums.ClothesColor;

import java.util.ArrayList;

public class ColorSearchAdapter extends RecyclerView.Adapter<ColorSearchAdapter.ViewHolder> {

    static private ArrayList<ClothesColor> colData;
    private LayoutInflater colInflater;

    public ColorSearchAdapter(Context context, ArrayList<ClothesColor> data) {
        colInflater = LayoutInflater.from(context);
        colData = data;
    }

//    public void update(String[] list) {
//        colData = list;
//    } //리스트 항목을 업데이트

    public int getItemCount() {
        return colData.size();
    } //리스트 항목의 개수를 반환

    public ClothesColor getItem(int id) {
        return colData.get(id);
    } //해당 인덱스의 리스트 항목을 반환

    public int getColor(ClothesColor clothesColor) {  //String 값에 해당하는 int 값을 반환
        switch (clothesColor) {
            case WHITE: return Color.WHITE;
            case GRAY: return Color.GRAY;
            case BLACK: return Color.BLACK;
            case RED: return Color.RED;
            case GREEN: return Color.GREEN;
            case BLUE: return Color.BLUE;
            case CYAN: return Color.CYAN;
            case YELLOW: return Color.YELLOW;
            case MAGENTA: return Color.MAGENTA;
        }

        return 0;
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
        holder.color.setBackgroundColor(getColor(colData.get(position)));
    }

}
