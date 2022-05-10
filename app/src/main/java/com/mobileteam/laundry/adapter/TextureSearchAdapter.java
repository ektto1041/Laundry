package com.mobileteam.laundry.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileteam.laundry.AppData;
import com.mobileteam.laundry.R;

import java.util.ArrayList;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class TextureSearchAdapter extends RecyclerView.Adapter<TextureSearchAdapter.ViewHolder>{
    private long clothesId;
    private ArrayList<String> data;
    private LayoutInflater txtInflater;

    public TextureSearchAdapter(Context context, ArrayList<String> data, long clothesId) {
        txtInflater = LayoutInflater.from(context);
        this.data = data;
        this.clothesId = clothesId;
    }

    public int getItemCount() {
        return data.size();
    } //리스트 항목의 개수를 반환

    public String getItem(int id) {
        return data.get(id);
    } //해당 인덱스의 리스트 항목을 반환

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView texture;
        ImageButton xButton;

        ViewHolder(View itemView) {
            super(itemView);
            texture = itemView.findViewById(R.id.texture_name);
            xButton = itemView.findViewById(R.id.texture_item_x_button);

            xButton.setOnClickListener(v -> {
                String textureName = data.get(getAdapterPosition());

                data.remove(textureName);
                notifyDataSetChanged();

                if(clothesId != -1) {
                    AppData.getDb().textureDao().deleteByName(textureName, clothesId)
                            .doOnSuccess(id -> Log.d("#####", id + " is deleted (Texture)"))
                            .doOnError(e -> Log.e("#####", e.toString()))
                            .subscribeOn(Schedulers.io()).subscribe();
                }
            });
        }
    }

    public TextureSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = txtInflater.inflate(R.layout.texture_item, parent, false);
        return new TextureSearchAdapter.ViewHolder(view);
    }

    public void onBindViewHolder(TextureSearchAdapter.ViewHolder holder, int position) {
        holder.texture.setText(data.get(position));
    }

    public void setClothesId(long clothesId) {
        if(clothesId != -1) this.clothesId = clothesId;
    }
}

