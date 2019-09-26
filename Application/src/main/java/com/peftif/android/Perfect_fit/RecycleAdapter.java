package com.peftif.android.Perfect_fit;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peftif.android.Perfect_fit.ModelData.Data_model;

import java.util.List;

/**
 * Created by csa on 3/1/2017.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Myholder> {
    List<Data_model> dataModelArrayList;

    public RecycleAdapter(List<Data_model> dataModelArrayList) {
        this.dataModelArrayList = dataModelArrayList;
    }

    public interface OnItemClickListener{
        void onItemClick(View v,int pos);
    }
    private OnItemClickListener mListener = null;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    class Myholder extends RecyclerView.ViewHolder {
        TextView name, city;

        public Myholder(View item_model) {
            super(item_model);

            name = (TextView) item_model.findViewById(R.id.item_name);
            city = (TextView) item_model.findViewById(R.id.item_height);

            item_model.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
        }
    }


    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_model, null);
        return new Myholder(view);

    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        Data_model dataModel = dataModelArrayList.get(position);
        holder.name.setText(dataModel.getName());
        holder.city.setText(dataModel.getHeight());

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }
}