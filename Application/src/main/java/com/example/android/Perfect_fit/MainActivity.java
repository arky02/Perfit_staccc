package com.example.android.Perfect_fit;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class MainActivity extends AppCompatActivity {

    LinearLayout button_name;
    ViewPager vp;
    ImageView image_1,image_2;
    AutoScrollViewPager autoViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_name = findViewById(R.id.button_name);
        image_1 = findViewById(R.id.image_1);
        image_2 = findViewById(R.id.image_2);

        Intent intent = getIntent();

//        Log.e("check disance", ""+intent.getDoubleExtra("legDistance", 0.0));
//        Log.e("check disance", ""+intent.getDoubleExtra("armDistance", 0.0));

        List datas = new ArrayList();
        datas.add(R.drawable.illust_1);
        datas.add(R.drawable.illust_2);
        datas.add(R.drawable.illust_3);

        autoViewPager = findViewById(R.id.autoViewPager);
        AutoScrollAdapter scrollAdapter = new AutoScrollAdapter(datas, this);
        autoViewPager.setAdapter(scrollAdapter);
        autoViewPager.setInterval(4000);
        autoViewPager.startAutoScroll();

        vp = findViewById(R.id.vp);
        vp.setOffscreenPageLimit(2);
        MoviePagerAdapter adapter = new MoviePagerAdapter(getSupportFragmentManager());

        FirstFragmentActivity fragment1 = new FirstFragmentActivity();
        adapter.addItem(fragment1);

        SecondFragmentActivity fragment2 = new SecondFragmentActivity();
        adapter.addItem(fragment2);

        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == 2){
                    GlobalData data = (GlobalData) getApplicationContext();
                    ImageSetting(data.getCheckFragment());
                }
            }
        });

        button_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModifyModelActivity.class);
                intent.putExtra("height",getIntent().getStringExtra("height"));
                intent.putExtra("name",getIntent().getStringExtra("name"));
                startActivity(intent);
                overridePendingTransition(R.anim.sliding_up, R.anim.stay);
            }
        });
    }

    class MoviePagerAdapter extends FragmentStatePagerAdapter {

        ArrayList<Fragment> items = new ArrayList<>();

        public MoviePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addItem(Fragment item) {
            items.add(item);
        }

        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }

    public void ImageSetting(int check) {
        switch (check) {
            case 1 :
                image_1.setImageResource(R.drawable.gray_dot);
                image_2.setImageResource(R.drawable.pink_dot);
                break;
            case 2 :
                image_1.setImageResource(R.drawable.pink_dot);
                image_2.setImageResource(R.drawable.gray_dot);
                break;
        }
    }
}
