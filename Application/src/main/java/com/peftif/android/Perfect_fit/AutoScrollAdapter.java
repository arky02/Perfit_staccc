package com.peftif.android.Perfect_fit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AutoScrollAdapter extends PagerAdapter {

    List data;
    Context context;

    public AutoScrollAdapter(List data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.auto_viewpager,null);
        ImageView image_container = (ImageView) v.findViewById(R.id.image);
        if(data.get(position).equals(R.drawable.illust_2)) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(265*2, 298*2);
            image_container.setLayoutParams(lp);
        }
        else {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(233*2, 298*2);
            image_container.setLayoutParams(lp);
        }
        Glide.with(context).load(data.get(position)).into(image_container);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
