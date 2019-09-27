package com.peftif.android.Perfect_fit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class SecondFragmentActivity extends Fragment {

    FirstFragmentActivity.changeButtonEvent listner;

    public interface changeButtonEvent{
        void onChangeButtonListener2(int id);
    }

    public SecondFragmentActivity() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser) {
            ((GlobalData) getActivity().getApplicationContext()).setCheckFragment(2);
        } else {

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_second, container, false);

        LinearLayout btn_dress = layout.findViewById(R.id.btn_dress);
        LinearLayout btn_skirt = layout.findViewById(R.id.btn_skirt);

        btn_dress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onChangeButtonListener(2);
            }
        });

        btn_skirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onChangeButtonListener(2);
            }
        });

        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listner = (FirstFragmentActivity.changeButtonEvent) context;
    }
}
