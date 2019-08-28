package com.example.android.Perfect_fit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
///**
// * 편집거리를 구하기 위한 알고리즘으로 levenshtein distance 알고리즘을 사용합니다.
// * - 한글의 다차원 특성상 자소분리를 통해 거리를 측정합니다.
// *
// * @param word1
// * @param word2
// * @return
// */


public class EditDistance extends AppCompatActivity {

    TextView tv,tv2;
    ArrayList<String> listsize = null;
    ArrayList<String> list = null;
    String[] list1;
    String[][] finalList;
    int min = 100000;
    ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdistance);


        HashMap<String, String> hashmap_UP = new HashMap<>();
        hashmap_UP.put("shoulder", "어깨넓이,어깨너비,어깨폭,어깨,어깨단면");
        hashmap_UP.put("chest", "가슴폭,가슴,가슴단면");
        hashmap_UP.put("arm", "소매,소매기장,소매길이,소매장,팔길이,소매단면,팔");
        hashmap_UP.put("wrist", "손목단면,손목,손목길이");
        hashmap_UP.put("wristdouble", "손목둘레");
        hashmap_UP.put("horizonarmdouble", "팔통둘레");
        hashmap_UP.put("verticallength", "총기장,총장,옷길이,총길이,기장");
        hashmap_UP.put("bottomlength", "밑단,밑단폭,밑단길이,밑단너비,밑단단면");
        hashmap_UP.put("chestdouble", "가슴둘레");
        hashmap_UP.put("armhole", "암홀단면,암홀");
        hashmap_UP.put("armholedouble", "암홀둘레");


        List<String> values = new ArrayList(hashmap_UP.values());


//        HashMap<String,String> hashmap_DOWN = new HashMap<>();
//        hashmap_DOWN.put("waist","허리(밴딩),밴딩,허리,허리너비,밴딩길이,허리길이,허리단면,밴딩단면");
//        hashmap_DOWN.put("waistdouble","허리둘레,밴딩둘레");
//        hashmap_DOWN.put("hip","엉덩이,엉덩이단면,엉덩이길이");
//        hashmap_DOWN.put("hipdouble", "엉덩이둘레");
//        hashmap_DOWN.put("thigh", "허벅지,허벅지단면");
//        hashmap_DOWN.put("thighdouble", "허벅지둘레");
//        hashmap_DOWN.put("mitwe", "밑위,밑위길이,밑위단면");
//        hashmap_DOWN.put("bottomlength","밑단,밑단단면,밑단길이");
//        hashmap_DOWN.put("bottomlengthdouble","밑단둘레");
//        hashmap_DOWN.put("verticallength", "총기장,총장,옷길이,총길이,기장");

        //이미지 디코딩을 위한 초기화
        String OCRresult = getIntent().getStringExtra("OCRresult");
        Log.d("OCRresult", OCRresult);
        // 텍스트 유사도 분석을 위한 split

        list1 = OCRresult.split("\n");

        for (int i = 0; i <= list1.length - 1; i++) {
            finalList[i] = list1[i].split(" ");
        }

        for (int i = 0; i <= finalList[0].length - 1; i++) {
            for (int k = 0; k <= values.size(); k++) {
                int levendis = levenshteinDistance(finalList[0][i], values.get(k));
                if (levendis < min) {
                    min = levendis;
                }
            }
            finalList[0][i] = Objects.requireNonNull(getKey(hashmap_UP, values.get(min))).toString();
        }

                for(int y = 0; y<=  finalList[0].length; y++ ){
                    Log.e("finallist"+y, finalList[0][y]);
                }

//
//
//            for (int j = 0; j <= finalList2.size() - 1; j++) {
//                for (int i = 0; i <= finalList[j].length - 1; j++) {
//                    for (int k = 0; k <= values.size(); k++) {
//                        int levendis = levenshteinDistance(finalList[j][i], values.get(k));
//                        if (levendis < min) {
//                            min = levendis;
//                            finalList[j][i] = values.get(k);
//                        }
//                    }
//                }
//            }


//        int max, min;
//        int i;
//
//        max = arr[0];
//        min = arr[0];
//
//        for (i = 0; i < SIZE; i++) {
//            if (arr[i] > max) {
//                max = arr[i];
//            }
//            if (arr[i] < min) {
//                min = arr[i];
//            }
//        }
//
//        printf("max = %d\n", max); // max = 8
//        printf("min = %d\n", min); // min = 1
//
//        return 0;
//


//        ArrayList<String> finalList1 = null;
//        ArrayList<ArrayList<String>> finalList2 = null;

            //레벤스타인 거리 구하기


            tv = findViewById(R.id.tv);
            tv2 = findViewById(R.id.tv2);

//            tv.setText(finalList[0][0]);
//            tv2.setText(String.valueOf(b));

        }


    public static Object getKey(HashMap<String, String> m, Object value) {
        for(Object o: m.keySet()) {
            if(m.get(o).equals(value)) {
                return o; }
        } return null;
    }


    public int levenshteinDistance(String word1, String word2) {

        //tv.setText(word1 + " | " + word2 + " = ");

        word1 = JasoTokenizer.split(word1);
        word2 = JasoTokenizer.split(word2);

        int word1_len = word1.length() + 1;
        int word2_len = word2.length() + 1;
        int cost = 0;

        if (word1.equals(word2)) {
            return 0;
        }

        int[][] costMatrix = initCostMatrix(word1_len, word2_len);

        for (int j = 1; j < word2_len; j++) {
            for (int i = 1; i < word1_len; i++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    cost = 0;
                } else {
                    cost = 1;
                }
                costMatrix[i][j] = minimum(costMatrix[i - 1][j] + 1,
                        costMatrix[i][j - 1] + 1,
                        costMatrix[i - 1][j - 1] + cost);
            }
        }

         displayCostMatrix(costMatrix);

        return costMatrix[word1_len - 1][word2_len - 1];
    }

    private void displayCostMatrix(int[][] costMatrix) {

        for (int i = 0; i < costMatrix.length; i++) {
            for (int j = 0; j < costMatrix[i].length; j++) {
                Log.d("editdistance",costMatrix[i][j] + "\t");
            }
            Log.d("editdistance","newline");
        }
    }

    private int[][] initCostMatrix(int word1_len, int word2_len) {

        int[][] costMatrix = new int[word1_len][word2_len];

        for (int i = 1; i < word1_len; i++) {
            costMatrix[i][0] = i;
        }

        for (int i = 1; i < word2_len; i++) {
            costMatrix[0][i] = i;
        }

        return costMatrix;
    }

    private int minimum(int arg1, int arg2, int arg3) {

        int minimum = 100000;

        if (arg1 == arg2 && arg2 == arg3) {
            minimum = arg1;
        }

        if (arg1 < arg2 || arg1 < arg3) {
            minimum = arg1;
        }

        if (arg2 < arg1 || arg2 < arg3) {
            minimum = arg2;
        }

        if (arg3 < arg1 || arg3 < arg2) {
            minimum = arg3;
        }

        return minimum;
    }




}
