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

public class FirstFragmentActivity extends Fragment {

    boolean check = false;
    changeButtonEvent listner;

    public interface changeButtonEvent{
        void onChangeButtonListener(int id);
    }

    public FirstFragmentActivity() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser) {
            if(check) {
                ((GlobalData) getActivity().getApplicationContext()).setCheckFragment(1);
            }
        } else {

        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        check = true;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_first, container, false);

        LinearLayout btn_shirt = layout.findViewById(R.id.btn_shirt);
        LinearLayout btn_pants = layout.findViewById(R.id.btn_pants);

        btn_shirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onChangeButtonListener(1);
            }
        });

        btn_pants.setOnClickListener(new View.OnClickListener() {
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
        listner = (changeButtonEvent) context;
    }
}
