package com.peftif.android.Perfect_fit.ModelData;

import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

import java.util.ArrayList;
        import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static String DATABASE = "database.db";
    public static String TABLE ="mytable";
    public static String NAME ="name";
    public static String ARM ="arm";
    public static String LEG="leg";
    public static String SHOULDER="shoulder";
    public static String HEIGHT ="height";
    String br;

    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        br = "CREATE TABLE "+TABLE+"("+NAME+ " Text,"+ HEIGHT + " Text, "+ ARM + " Text,"+ LEG + " Text,"+ SHOULDER + " Text);";
        db.execSQL(br);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE+" ;");
    }


    public void insertdata(String name,String height,Double arm,Double leg,Double shoulder){
        System.out.print("Hello "+br);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(NAME, name);
        contentValues.put(HEIGHT, height);
        contentValues.put(ARM,arm);
        contentValues.put(LEG, leg);
        contentValues.put(SHOULDER, shoulder);
        db.insert(TABLE,null,contentValues);
        db.close();


    }

    public List<Data_model> getdata(){
        // DataModel dataModel = new DataModel();
        List<Data_model> data=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE+" ;",null);
        StringBuffer stringBuffer = new StringBuffer();
        Data_model dataModel = null;
        while (cursor.moveToNext()) {
            dataModel= new Data_model();
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String height= cursor.getString(cursor.getColumnIndexOrThrow("height"));
            Double arm = cursor.getDouble(cursor.getColumnIndexOrThrow("arm"));
            Double leg = cursor.getDouble(cursor.getColumnIndexOrThrow("leg"));
            Double shoulder = cursor.getDouble(cursor.getColumnIndexOrThrow("shoulder"));

            dataModel.setName(name);
            dataModel.setHeight(height);
            dataModel.setShoulder(shoulder);
            dataModel.setLeg(leg);
            dataModel.setArm(arm);
            stringBuffer.append(dataModel);
            // stringBuffer.append(dataModel);
            data.add(dataModel);
        }

        for (Data_model mo:data ) {

            Log.i("Hellomo",""+mo.getHeight());
        }

        //

        return data;
    }



}