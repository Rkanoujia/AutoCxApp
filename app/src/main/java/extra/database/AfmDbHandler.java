package extra.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by dell pc on 13-06-2016.
 */
public class AfmDbHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "driver_afm2020.db";
    private static final int DB_VERSION = 5;


    public AfmDbHandler(Context applicationcontext) {
        super(applicationcontext, DATABASE_NAME, null, DB_VERSION);
//        Log.d(LOGCAT,"Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseContent.CREATE_TRIP);
        db.execSQL(DataBaseContent.CREATE_TRIPDETAIL);
        db.execSQL(DataBaseContent.CREATE_NOTIFICATION);
        db.execSQL(DataBaseContent.CREATE_IMAGES);
        db.execSQL(DataBaseContent.CREATE_ITEMS);
        db.execSQL(DataBaseContent.CREATE_ORDER);

    }

    public void clearTrips() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DataBaseContent.TABLE_TRIP, null, null);
        db.delete(DataBaseContent.TABLE_TRIPDETAIL, null, null);
        db.delete(DataBaseContent.TABLE_ITEMS, null, null);
        db.delete(DataBaseContent.TABLE_ORDER, null, null);
//        db.delete(DataBaseContent.TABLE_NOTIFICATION, null, null);

        db.close();
    }

//    public void deletebolpod(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(DataBaseContent.TABLE_BOLPOD_IMAGE, null, null);
//        db.close();
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        if (newVersion > oldVersion) {
//            db.execSQL("ALTER TABLE foo ADD COLUMN new_column INTEGER DEFAULT 0");
//        }
        String query = "DROP TABLE IF EXISTS " + DataBaseContent.TABLE_TRIP;
        String query1 = "DROP TABLE IF EXISTS " + DataBaseContent.TABLE_TRIPDETAIL;
        String query2 = "DROP TABLE IF EXISTS " + DataBaseContent.TABLE_NOTIFICATION;
        String query3 = "DROP TABLE IF EXISTS " + DataBaseContent.TABLE_IMAGES;
        String query4 = "DROP TABLE IF EXISTS " + DataBaseContent.TABLE_ITEMS;
        String query5 = "DROP TABLE IF EXISTS " + DataBaseContent.TABLE_ORDER;

        db.execSQL(query);
        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);
        db.execSQL(query5);
        onCreate(db);
    }



}