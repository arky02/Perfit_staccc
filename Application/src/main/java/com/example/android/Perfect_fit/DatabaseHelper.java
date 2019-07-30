package com.example.android.Perfect_fit;

import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by csa on 3/1/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public static String DATABASE = "database.db";
    public static String TABLE ="mytable";
    public static String NAME ="name";
    public static String HEIGHT ="height"; //city = height
    String br;

    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //  br= "CREATE TABLE mytable(name TEXT,city TEXT,);";
        br = "CREATE TABLE "+TABLE+"("+NAME+ " Text,"+ HEIGHT + " Text);";
        db.execSQL(br);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE+" ;");
    }


    public void insertdata(String name,String height){
        System.out.print("Hello "+br);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();


        contentValues.put(NAME, name);
        contentValues.put(HEIGHT, height);
        db.insert(TABLE,null,contentValues);


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
            dataModel.setName(name);
            dataModel.setHeight(height);
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