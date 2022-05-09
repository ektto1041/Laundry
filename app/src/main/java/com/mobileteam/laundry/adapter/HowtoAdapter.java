//package com.mobileteam.laundry.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.mobileteam.laundry.R;
//
//import java.util.ArrayList;
//
//public class HowtoAdapter extends RecyclerView.Adapter<HowtoAdapter.ViewHolder>{
//    static private String[] HowData = new String[4];
//    private Context cxt;
//    private LayoutInflater HowInflater;
//
//    public HowtoAdapter(Context context, String[] data) {
//        cxt = context;
//        HowInflater = LayoutInflater.from(context);
//        HowData = data;
//    }
//
//    public void update(ArrayList<String> list) {
//        HowData = list.toArray(new String[0]);
//    } //리스트 항목을 업데이트
//
//    public int getItemCount() {
//        return HowData.length;
//    } //리스트 항목의 개수를 반환
//
//    public String getItem(int id) {
//        return HowData[id];
//    } //해당 인덱스의 리스트 항목을 반환
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView Howto;
//        ViewHolder(View itemView) {
//            super(itemView);
//            Howto = itemView.findViewById(R.id.howtocheck);
//        }
//    }
//
//    //string 값에 따라 이미지를 찾아오는 function, 임시 이미지 사용함
//    private int getres(String name, int idx) {
//        int img = -1;
//        View view;
//        if(idx == 0) {
//            switch (name) {
//                case "세탁기":
//                case "물세탁":
//                case "세탁기만": img = R.drawable.ic_40;
//            }
//        } else if(idx == 1) {
//            switch (name) {
//                case "강":
//                case "약": img = R.drawable.ic_60;
//            }
//
//        } else if(idx == 2) {
//            switch (name) {
//                case "상관없음":
//                case "중성세제": img = R.drawable.ic_95;
//            }
//
//        } else if(idx == 3) {
//            switch (name) {
//                case "95ºC":
//                case "60ºC":
//                case "40ºC":
//                case "30ºC": img = R.drawable.ic_40;
//            }
//        }
//        return img;
//    }
//    public HowtoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = HowInflater.inflate(R.layout.washing_sign_item, parent, false);
//        return new HowtoAdapter.ViewHolder(view);
//    }
//
//    public void onBindViewHolder(HowtoAdapter.ViewHolder holder, int position) {
//        holder.Howto.setBackgroundResource(getres(HowData[position], position));
//    }
//}
