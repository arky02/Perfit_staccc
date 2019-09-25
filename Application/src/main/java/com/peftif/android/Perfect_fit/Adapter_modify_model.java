package com.peftif.android.Perfect_fit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.peftif.android.Perfect_fit.ModelData.Data_model;

import java.util.ArrayList;

public class Adapter_modify_model extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Data_model> ModelDataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        ImageView btn;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.item_name);
            btn = itemView.findViewById(R.id.item_btn);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ModelDataList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }



    public Adapter_modify_model(ArrayList<Data_model> modelDataList) {
        ModelDataList = modelDataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_model, viewGroup, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

        myViewHolder.tv_name.setText(ModelDataList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return ModelDataList.size();
    }
}
