package com.peftif.android.Perfect_fit.PoseEstimation;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HumanSkeleton implements Parcelable {

    public static class Point implements Parcelable {
        public double x = 0;
        public double y = 0;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public Point(Parcel parcel) {
            this.x = parcel.readDouble();
            this.y = parcel.readDouble();
        }

        // create Parcelable
        public static final Parcelable.Creator<Point> CREATOR = new Parcelable.Creator<Point>() {
            @Override
            public Point createFromParcel(Parcel parcel) {
                return new Point(parcel);
            }
            @Override
            public Point[] newArray(int size) {
                return new Point[size];
            }
        };
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(this.x);
            dest.writeDouble(this.y);
        }

        @Override
        public int describeContents() {
            return 0;
        }


        @NonNull
        @Override
        public String toString() {
            return "("+x+", "+y+")";
        }
    }

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
    private Point top = null;
    private boolean isOk = true;

    public HumanSkeleton(Parcel parcel) {
        this.neck = parcel.readParcelable(Point.class.getClassLoader());
        this.rightshoulder = parcel.readParcelable(Point.class.getClassLoader());
        this.rightelbow= parcel.readParcelable(Point.class.getClassLoader());
        this.rightwrist= parcel.readParcelable(Point.class.getClassLoader());
        this.leftshoulder= parcel.readParcelable(Point.class.getClassLoader());
        this.leftelbow= parcel.readParcelable(Point.class.getClassLoader());
        this.leftwrist= parcel.readParcelable(Point.class.getClassLoader());
        this.righthip= parcel.readParcelable(Point.class.getClassLoader());
        this.rightknee= parcel.readParcelable(Point.class.getClassLoader());
        this.rightankle= parcel.readParcelable(Point.class.getClassLoader());
        this.lefthip= parcel.readParcelable(Point.class.getClassLoader());
        this.leftknee= parcel.readParcelable(Point.class.getClassLoader());
        this.leftankle= parcel.readParcelable(Point.class.getClassLoader());
        this.righteye= parcel.readParcelable(Point.class.getClassLoader());
        this.leftteye= parcel.readParcelable(Point.class.getClassLoader());
        this.rightear= parcel.readParcelable(Point.class.getClassLoader());
        this.leftear= parcel.readParcelable(Point.class.getClassLoader());
        this.top = parcel.readParcelable(Point.class.getClassLoader());
    }

    public static final Parcelable.Creator<HumanSkeleton> CREATOR = new Parcelable.Creator<HumanSkeleton>() {
        @Override
        public HumanSkeleton createFromParcel(Parcel parcel) {
            return new HumanSkeleton(parcel);
        }
        @Override
        public HumanSkeleton[] newArray(int size) {
            return new HumanSkeleton[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.neck, 0);
        dest.writeParcelable(this.rightshoulder, 0);
        dest.writeParcelable(this.rightelbow, 0);
        dest.writeParcelable(this.rightwrist, 0);
        dest.writeParcelable(this.leftshoulder, 0);
        dest.writeParcelable(this.leftelbow, 0);
        dest.writeParcelable(this.leftwrist, 0);
        dest.writeParcelable(this.righthip, 0);
        dest.writeParcelable(this.rightknee, 0);
        dest.writeParcelable(this.rightankle, 0);
        dest.writeParcelable(this.lefthip, 0);
        dest.writeParcelable(this.leftknee, 0);
        dest.writeParcelable(this.leftankle, 0);
        dest.writeParcelable(this.righteye, 0);
        dest.writeParcelable(this.leftteye, 0);
        dest.writeParcelable(this.rightear, 0);
        dest.writeParcelable(this.leftear, 0);
        dest.writeParcelable(this.top, 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    // 0 : 코, 1 : 목, 2 : 오른쪽 어깨, 3: 오른쪽 팔굼치, 4 : 오른쪽 손목, 5 : 왼쪽 어깨
    // 6 : 왼쪽 팔굼치, 7 : 왼쪽 손목, 8 : 오른쪽 엉덩이, 9 : 오른쪽 무릎, 10 : 오른쪽 발목
    //11 : 왼쪽 엉덩이, 12 : 왼쪽 무릎, 13 : 왼쪽 발목, 14 : 오른쪽 눈, 15 : 왼쪽 눈
    //16 : 오른쪽 귀, 17 : 왼쪽 귀

    private static Point parseFromJSON(JSONObject arr, int idx) throws JSONException {
        if(arr.has(""+idx)) {
            JSONObject node = arr.getJSONObject(""+idx);
            return new Point(node.getDouble("x"), node.getDouble("y"));
        }
        return null;
    }

    public HumanSkeleton(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonArray = jsonObject.getJSONArray("predictions");
        JSONObject jsonObject1 = jsonArray.getJSONObject(0);

        nose = parseFromJSON(jsonObject1, 0);
        neck = parseFromJSON(jsonObject1, 1);
        rightshoulder = parseFromJSON(jsonObject1, 2);
        rightelbow = parseFromJSON(jsonObject1, 3);
        rightwrist = parseFromJSON(jsonObject1, 4);
        leftshoulder = parseFromJSON(jsonObject1, 5);
        leftelbow = parseFromJSON(jsonObject1, 6);
        leftwrist = parseFromJSON(jsonObject1, 7);
        righthip = parseFromJSON(jsonObject1, 8);
        rightknee = parseFromJSON(jsonObject1, 9);
        rightankle = parseFromJSON(jsonObject1, 10);
        lefthip = parseFromJSON(jsonObject1, 11);
        leftknee = parseFromJSON(jsonObject1, 12);
        leftankle = parseFromJSON(jsonObject1, 13);
        righteye = parseFromJSON(jsonObject1, 14);
        leftteye = parseFromJSON(jsonObject1, 15);
        rightear = parseFromJSON(jsonObject1, 16);
        leftear = parseFromJSON(jsonObject1, 17);

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
        if(leftankle == null && rightankle != null) {
            leftankle = getPoint(rightankle);
        }
        if(rightankle == null && leftankle != null) {
            rightankle = getPoint(leftankle);
        }
        if(leftwrist == null && rightwrist != null) {
            leftwrist = getPoint(rightwrist);
        }
        if(rightwrist == null && leftwrist != null) {
            rightwrist = getPoint(leftwrist);
        }
        if(rightknee == null && leftknee != null) {
            rightknee = getPoint(leftknee);
        }
        if(leftknee == null && rightknee != null) {
            leftknee = getPoint(rightknee);
        }

        if(nose == null || (rightwrist == null && leftwrist == null) || (rightknee == null && leftknee == null) || neck == null || (rightankle == null && leftankle == null)
         || (righthip == null && lefthip == null) || (rightelbow == null && leftelbow == null) || (rightshoulder == null && leftshoulder == null)) {
            isOk = false;
            Log.e("error check1", "ok");
        }
        else {
            top = new Point(nose.x, nose.y + nose.y - neck.y);
            isOk = true;
            Log.e("error check2", "ok");
        }
    }

    //1. (왼쪽 어깨, 오른쪽어깨) 등 대칭점을 찾는다
    //2. 목과 대칭점을 연결해서 가상의 선 생성
    //3. null 일경우 선대칭

    public Point getAve(Point a, Point b) {
        //점의 중간점을 찾는 함수
        Point Ave = new Point(0 , 0);
        Ave.x = (a.x + b.x)/2;
        Ave.y = (a.y + b.y)/2;
        return Ave;
    }

    public Point getSymmetry() {
        Point symmetry = new Point(0, 0);

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
        Point point = new Point(0, 0);
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

    public Point getTop() {
       return top;
    }

    public boolean isOk() {
        return this.isOk;
    }
}
