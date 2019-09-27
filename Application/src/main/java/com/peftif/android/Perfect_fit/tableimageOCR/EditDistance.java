package com.peftif.android.Perfect_fit.tableimageOCR;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.peftif.android.Perfect_fit.FinalActivity;
import com.peftif.android.Perfect_fit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditDistance extends AppCompatActivity {

    String[] list1;
    String[][] finalList, realFinalList;
    int min = 100000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdistance);


        HashMap<String, String> hashmap_UP = new HashMap<>();
        hashmap_UP.put("shoulder", "어깨넓이,어깨너비,어깨폭,어깨,어깨단면");//5
        hashmap_UP.put("chest", "가슴폭,가슴,가슴단면");//3
        hashmap_UP.put("arm", "소매,소매기장,소매길이,소매길이(어깨포함),소매장,팔길이,팔");//7
        hashmap_UP.put("wrist", "손목단면,손목,손목길이,소매단면");//3
        hashmap_UP.put("wristdouble", "손목둘레");
        hashmap_UP.put("horizonarmdouble", "팔통둘레");//20
        hashmap_UP.put("verticallength", "총기장,총장,옷길이,총길이,기장"); //5
        hashmap_UP.put("bottomlength", "밑단,밑단폭,밑단길이,밑단너비,밑단단면"); //5
        hashmap_UP.put("chestdouble", "가슴둘레");
        hashmap_UP.put("armhole", "암홀단면,암홀");
        hashmap_UP.put("armholedouble", "암홀둘레");



        HashMap<String, String> hashmap_DOWN = new HashMap<>();
        hashmap_DOWN.put("waist", "허리(밴딩),밴딩,허리,허리너비,밴딩길이,허리길이,허리단면,밴딩단면");
        hashmap_DOWN.put("waistdouble", "허리둘레,밴딩둘레");
        hashmap_DOWN.put("hip", "엉덩이,엉덩이단면,엉덩이길이");
        hashmap_DOWN.put("hipdouble", "엉덩이둘레");
        hashmap_DOWN.put("thigh", "허벅지,허벅지단면");
        hashmap_DOWN.put("thighdouble", "허벅지둘레");
        hashmap_DOWN.put("mitwe", "밑위,밑위길이,밑위단면");
        hashmap_DOWN.put("bottomlength", "밑단,밑단단면,밑단길이");
        hashmap_DOWN.put("bottomlengthdouble", "밑단둘레");
        hashmap_DOWN.put("verticallength", "총기장,총장,옷길이,총길이,기장");

        //이미지 디코딩을 위한 초기화

        String OCRresult = getIntent().getStringExtra("OCRresult");
        Log.e("Editdistance_OCRresult", OCRresult);
        // 텍스트 유사도 분석을 위한 split

        int listLength = OCRresult.split("\n").length;
        list1 = new String[listLength];

        list1 = OCRresult.split("\n");
        Log.d("list1[0]", list1[0]);
        Log.d("list[1]", list1[1]);
        //0 : 어깨, 1 : 가슴단면, 3 : 소매길이, 4 : 암홀단면, 5 : 총길이

        for (int i = 0; i < 5; i++) {
            Log.e("chekc data1", ""+split(list1[1])[i]);
        }

        Intent intent = new Intent(EditDistance.this, FinalActivity.class);
        intent.putExtra("shoulder", Integer.parseInt(split(list1[1])[0]));
        intent.putExtra("arm", Integer.parseInt(split(list1[1])[3]));

        startActivity(intent);
        finish();

//        finalList = new String[listLength][];
//        realFinalList = new String[listLength][];
//
//        for (int i = 0; i <= list1.length - 1; i++) {
//            finalList[i] = new String[list1[i].split(" ").length];
//            realFinalList[i] = new String[list1[i].split(" ").length];
//        }
//
//        for (int i = 0; i <= list1.length - 1; i++) {
//            finalList[i] = list1[i].split(" ");
//            if (i > 0) {
//                realFinalList[i] = list1[i].split(" ");
//            }
//        }
//
//
//        applyFinalListJASO(hashmap_UP);
    }

    public String[] split(String result) {
        String returnData[] = result.split(" ");
        return returnData;
    }

    public void applyFinalListJASO(HashMap<String, String> hashMap) {
        List<String> values = new ArrayList(hashMap.values());
        List<String> keys = new ArrayList(hashMap.keySet());
        // 초기화 - min = 10000

        String findKey = "";
        // 1. finalList[0] =  이깨단먼 가슴단떤 소매갈이 임졸단면 소매단면 춤갈이
        for (int j = 0; j < finalList[0].length; j++) {
            for (int k = 0; k < values.size(); k++) {
                String[] arrItem = values.get(k).split(",");
                for (int i = 0; i < arrItem.length; i++) {
                    int levendis = levenshteinDistance(finalList[0][j], arrItem[i]);
                    if (levendis < min) {
                        min = levendis;
                        findKey = keys.get(k);
                    }
                }
            }
            realFinalList[0][j] = findKey;
        }

        ArrayList<String> arrayListShoulder = new ArrayList<>();
        ArrayList<String> arrayListarm= new ArrayList<>();
        ArrayList<String> arrayListvertical= new ArrayList<>();
        for(int j = 0; j < realFinalList[0].length; j++){
            if(realFinalList[0][j].equals("shoulder")){
                arrayListShoulder.add(realFinalList[1][j]);
            }
            if(realFinalList[0][j].equals("arm")){
                arrayListarm.add(realFinalList[1][j]);
            }
            if(realFinalList[0][j].equals("vertical")){
                arrayListvertical.add(realFinalList[1][j]);
            }
        }
        Intent mintent= new Intent(getApplicationContext(), FinalActivity.class);
        mintent.putStringArrayListExtra("arrayListShoulder",arrayListShoulder);
        mintent.putStringArrayListExtra("arrayListArm",arrayListarm);
        mintent.putStringArrayListExtra("arrayListVertical",arrayListvertical);
        startActivity(mintent);
    }

    public static Object getKey(HashMap<String, String> m, Object value) {
        for (Object o : m.keySet()) {
            if (m.get(o).equals(value)) {
                return o;
            }
        }
        return null;
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
//                Log.d("editdistance", costMatrix[i][j] + "\t");
            }
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
