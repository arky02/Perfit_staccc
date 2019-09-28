package com.peftif.android.Perfect_fit;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.peftif.android.Perfect_fit.FloatingMiniPerfit.CheckFWPermission;
import com.peftif.android.Perfect_fit.FloatingMiniPerfit.FloatingWidgetService;
import com.peftif.android.Perfect_fit.ModelData.Data_model;
import com.peftif.android.Perfect_fit.ModelData.DatabaseHelper;
import com.peftif.android.Perfect_fit.tableimageOCR.TableimageToString;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FirstFragmentActivity.changeButtonEvent, SecondFragmentActivity.changeButtonEvent{

    LinearLayout button_name;
    ViewPager vp;
    ImageView image_1,image_2;
    ImageButton btn_navi;
    AutoScrollViewPager autoViewPager;
    private SwitchCompat switchh;
    List<Data_model> datamodel;
    DatabaseHelper databaseHelper;
    int check =0;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        switchh = (SwitchCompat)navigationView.getMenu().findItem(R.id.nav_floatingWidget).getActionView();
        button_name = findViewById(R.id.button_name);
        image_1 = findViewById(R.id.image_1);
        image_2 = findViewById(R.id.image_2);
      //  btn_navi = findViewById(R.id.navi_bar);

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
        MainActivity.MoviePagerAdapter adapter = new MainActivity.MoviePagerAdapter(getSupportFragmentManager());

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

        switchh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Intent mintent = new Intent(getApplicationContext(), CheckFWPermission.class);
                    startActivity(mintent);
                    GlobalData.isWidgetDistroyed = false;
                }else{
                    if (!GlobalData.isWidgetDistroyed){
                        ((FloatingWidgetService)FloatingWidgetService.mContext).stopWidget();
                    }

                }
            }
        });

        button_name.setOnClickListener(new View.OnClickListener() { //추후 업데이트
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

    @Override
    public void onChangeButtonListener(int id) {
        switch(id) {
            
            case 1 :
                showDialog2();
                break;
            case 2 :
                showDialog1();
                break;
        }
    }

    @Override
    public void onChangeButtonListener2(int id) {
        showDialog1();
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.widget_setting, menu);
        Log.e("perfit", "onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.e("perfit", "onOptionsItemSelected");

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            PickOtherCreateDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void PickOtherCreateDialog() {
        dialog = new Dialog(MainActivity.this);
        if (dialog.getWindow() != null) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setContentView(R.layout.tutorial);
        dialog.setCancelable(true);

        final ImageView btn_next = dialog.findViewById(R.id.btn_next);
        final ImageView btn_before = dialog.findViewById(R.id.btn_before);
        final ImageView image = dialog.findViewById(R.id.image);
        final LinearLayout tutorial = dialog.findViewById(R.id.tutorial);
        final LinearLayout end = dialog.findViewById(R.id.end);
        final Button btn_ok = dialog.findViewById(R.id.btn_ok00);
        btn_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check <= 0){
                    check = 0;
                }else{
                    --check;
                }

                switch (check) {
                    case 0:
                        image.setImageResource(R.drawable.story_1);
                        btn_ok.setVisibility(View.GONE);
                        break;
                    case 1:
                        image.setImageResource(R.drawable.story_2);
                        btn_ok.setVisibility(View.GONE);
                        break;
                    case 2:
                        image.setImageResource(R.drawable.story_3);
                        btn_ok.setVisibility(View.GONE);
                        break;
                    case 3:
                        image.setImageResource(R.drawable.story_4);
                        btn_ok.setVisibility(View.GONE);
                        break;
                    case 4:
                        image.setImageResource(R.drawable.story_5);
                        btn_ok.setVisibility(View.VISIBLE);
                        break;


//                    case 5 :
//                        tutorial.setVisibility(View.GONE);
//                        end.setVisibility(View.VISIBLE);
                }
                Log.e("check", check+"");
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check >= 4){
                    check = 4;
                }else{
                    ++check;
                }

                switch (check) {
                    case 0:
                        image.setImageResource(R.drawable.story_1);
                        btn_ok.setVisibility(View.GONE);
                        break;
                    case 1:
                        image.setImageResource(R.drawable.story_2);
                        btn_ok.setVisibility(View.GONE);
                        break;
                    case 2:
                        image.setImageResource(R.drawable.story_3);
                        btn_ok.setVisibility(View.GONE);
                        break;
                    case 3:
                        image.setImageResource(R.drawable.story_4);
                        btn_ok.setVisibility(View.GONE);
                        break;
                    case 4:
                        image.setImageResource(R.drawable.story_5);
                        btn_ok.setVisibility(View.VISIBLE);
                        break;

//                    case 5 :
//                        tutorial.setVisibility(View.GONE);
//                        end.setVisibility(View.VISIBLE);
                }
                Log.e("check", check+"");
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Log.e("perfit", "onNavigationItemSelected");
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_floatingWidget) {
            // Handle the camera action

        } else if (id == R.id.nav_send) {
            Intent mintent = new Intent(getApplicationContext(),ComplainActivity.class);
            startActivity(mintent);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showDialog1() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_setting);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void showDialog2() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_setting2);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final Button button = dialog.findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TableimageToString.class);
                dialog.dismiss();
                startActivity(intent);
                finish();
            }
        });
        dialog.show();
    }
}

