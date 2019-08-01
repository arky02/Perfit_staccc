package com.example.android.Perfect_fit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HumanSkeleton {

    public static class Point {
        public double x = 0;
        public double y = 0;
    }

    double pose[][] = new double[18][2];
    double dot[] = new double[6];

    private Point nose = null;
    private Point neck = null;
    private Point rightshoulder = null;
    private Point rightelbow = null;
    private Point rightwrist = null;
    private Point leftshoulder = null;
    private Point leftelbow = null;
    private Point leftwrist = null;
    private Point righthip = null;
    private Point rightknee = null;
    private Point rightankle = null;
    private Point lefthip = null;
    private Point leftknee = null;
    private Point leftankle = null;
    private Point righteye = null;
    private Point leftteye = null;
    private Point rightear = null;
    private Point leftear = null;

    // 0 : 코, 1 : 목, 2 : 오른쪽 어깨, 3: 오른쪽 팔굼치, 4 : 오른쪽 손목, 5 : 왼쪽 어깨
    // 6 : 왼쪽 팔굼치, 7 : 왼쪽 손목, 8 : 오른쪽 엉덩이, 9 : 오른쪽 무릎, 10 : 오른쪽 발목
    //11 : 왼쪽 엉덩이, 12 : 왼쪽 무릎, 13 : 왼쪽 발목, 14 : 오른쪽 눈, 15 : 왼쪽 눈
    //16 : 오른쪽 귀, 17 : 왼쪽 귀

    public HumanSkeleton(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonArray = jsonObject.getJSONArray("predictions");
        JSONObject jsonObject1 = jsonArray.getJSONObject(0);

        for (int i = 0; i <18 ; i++) {
            JSONObject jsonObject2 = jsonObject1.getJSONObject(Integer.toString(i));
            pose[i][0] = jsonObject2.getDouble("x");
            pose[i][1] = jsonObject2.getDouble("y");
        }

        nose = new Point();
        nose.x = pose[0][0];
        nose.y = pose[0][1];
        neck = new Point();
        neck.x = pose[1][0];
        neck.y = pose[1][1];
        rightshoulder = new Point();
        rightshoulder.x = pose[2][0];
        rightshoulder.y = pose[2][1];
        rightelbow = new Point();
        rightelbow.x = pose[3][0];
        rightelbow.y = pose[3][1];
        rightwrist = new Point();
        rightwrist.x = pose[4][0];
        rightwrist.y = pose[4][1];
        leftshoulder = new Point();
        leftshoulder.x = pose[5][0];
        leftshoulder.y = pose[5][1];
        leftelbow = new Point();
        leftelbow.x = pose[6][0];
        leftelbow.y = pose[6][1];
        leftwrist = new Point();
        leftwrist.x = pose[7][0];
        leftwrist.y = pose[7][1];
        righthip = new Point();
        righthip.x = pose[8][0];
        righthip.y = pose[8][1];
        rightknee = new Point();
        rightknee.x = pose[9][0];
        rightknee.y = pose[9][1];
        rightankle = new Point();
        rightankle.x = pose[10][0];
        rightankle.y = pose[10][1];
        lefthip = new Point();
        lefthip.x = pose[11][0];
        lefthip.y = pose[11][1];
        leftknee = new Point();
        leftknee.x = pose[12][0];
        leftknee.y = pose[12][1];
        leftankle = new Point();
        leftankle.x = pose[13][0];
        leftankle.y = pose[13][1];
        righteye = new Point();
        righteye.x = pose[14][0];
        righteye.y = pose[14][1];
        leftteye = new Point();
        leftteye.x = pose[15][0];
        leftteye.y = pose[15][1];
        rightear = new Point();
        rightear.x = pose[16][0];
        rightear.y = pose[16][1];
        leftear = new Point();
        leftear.x = pose[17][0];
        leftear.y = pose[17][1];

        if(leftshoulder == null && rightshoulder != null) {
            leftshoulder = getPoint(rightshoulder);
        }
        if(rightshoulder == null && leftshoulder != null) {
            rightshoulder = getPoint(leftshoulder);
        }
        if(leftelbow == null && rightelbow != null) {
            leftelbow = getPoint(rightelbow);
        }
        if(rightelbow == null && leftelbow != null) {
            rightelbow = getPoint(leftelbow);
        }
        if(lefthip == null && righthip != null) {
            lefthip = getPoint(righthip);
        }
        if(righthip == null && lefthip != null) {
            righthip = getPoint(lefthip);
        }
        if(leftankle == null && rightelbow != null) {
            leftelbow = getPoint(rightelbow);
        }
        if(rightankle == null && leftankle != null) {
            rightankle = getPoint(leftankle);
        }
        if(leftwrist == null && rightwrist != null) {
            leftwrist = getPoint(rightwrist);
        }
        if(rightknee == null && leftknee != null) {
            rightknee = getPoint(leftknee);
        }
        if(leftknee == null && rightknee != null) {
            leftknee = getPoint(rightknee);
        }
    }

    //1. (왼쪽 어깨, 오른쪽어깨) 등 대칭점을 찾는다
    //2. 목과 대칭점을 연결해서 가상의 선 생성
    //3. null 일경우 선대칭

    public Point getAve(Point a, Point b) {
        //점의 중간점을 찾는 함수
        Point Ave = new Point();
        Ave.x = (a.x + b.x)/2;
        Ave.y = (a.y + b.y)/2;
        return Ave;
    }

    public Point getSymmetry() {
        Point symmetry = new Point();

        if(rightknee != null && leftknee != null) {
            symmetry = getAve(rightknee, leftknee);
        }
        else if(rightankle != null && leftankle != null) {
            symmetry = getAve(rightankle, leftankle);
        }
        else if(righthip != null && lefthip != null) {
            symmetry = getAve(righthip, lefthip);
        }
        else if(rightshoulder != null && leftshoulder != null) {
            symmetry = getAve(rightshoulder, leftshoulder);
        }
        else if(righteye != null && leftteye != null) {
            symmetry = getAve(righteye, leftteye);
        }
        return symmetry;
    }

    public Point getPoint(Point a) {
        //a점의 대칭점을 찾아주는 함수
        getLine(neck, getSymmetry(), a);
        return getRoot(dot[0], dot[1], dot[2], dot[3], dot[4], dot[5]);
    }

    public Point getRoot(double X1, double Y1, double Z1, double X2, double Y2, double Z2) {
        //행렬을 통해 대칭점을 찾아주는 함수
        Point point = new Point();
        point.x = ((Z1 * Y2) + ((-1) * Y1 * Z2)) / ((X1 * Y2) - (Y1 * X2));
        point.y = (((-1) * Z1 * X2) + (Z2 * X1)) / ((X1 * Y2) - (Y1 * X2));
        return point;
    }

    public void getLine(Point a, Point b, Point c) {
        //getRoot 함수를 돌리기위한 점들을 찾는 함수
        dot[0] = b.x - a.x;
        dot[1] = b.y - a.y;
        dot[2] = (b.y * c.y) - (a.y * c.y) + (b.x * c.x) - (a.x * c.x);
        dot[3] = (a.y - b.y) / (b.x - a.x);
        dot[4] = 1;
        dot[5] = ((((b.y * c.x) - (a.y * c.x) - (2 * a.x * b.y) + (2 * a.x * a.y)) / (b.x - a.x))) + (2 * a.y) - c.y;
    }

    public Point getNose() {
        return nose;
    }

    public Point getNeck() {
        return neck;
    }

    public Point getRightshoulder() {
        return rightshoulder;
    }

    public Point getRightelbow() {
        return rightelbow;
    }

    public Point getRightwrist() {
        return rightwrist;
    }

    public Point getLeftshoulder() {
        return leftshoulder;
    }

    public Point getLeftelbow() {
        return leftelbow;
    }

    public Point getLeftwrist() {
        return leftwrist;
    }

    public Point getRighthip() {
        return righthip;
    }

    public Point getRightknee() {
        return rightknee;
    }

    public Point getRightankle() {
        return rightankle;
    }

    public Point getLefthip() {
        return lefthip;
    }

    public Point getLeftknee() {
        return leftknee;
    }

    public Point getLeftankle() {
        return leftankle;
    }

    public Point getRighteye() {
        return righteye;
    }

    public Point getLeftteye() {
        return leftteye;
    }

    public Point getRightear() {
        return rightear;
    }

    public Point getLeftear() {
        return leftear;
    }
}
