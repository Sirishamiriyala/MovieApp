package com.example.sirisha.moviecopy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by sirisha on 31-05-2018.
 */

public class SqlLiteHelper extends SQLiteOpenHelper {
    public static final String AUTHORITY = "com.example.sirisha.moviecopy";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String path_Tasks = "MovieDetails";
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(path_Tasks).build();
    public static final String Databasename = "MovieDatabase";
    public static final String tablename = "MovieDetails";
    public static final int version = 1;
    public static final String id = "id";
    public static final String mid = "movieid";
    public static final String mtitle = "movieoriginaltitle";
    public static final String mbackdroppath = "backdroppath";
    public static final String mposterpath = "posterpath";
    public static final String mrating = "rating";
    public static final String mreleasedate = "releasedate";
    public static final String moverview = "overview";
    public Context c;

    public SqlLiteHelper(Context context) {
        super(context, Databasename, null, version);
        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String query = "CREATE TABLE " + tablename + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT," + mid + " INTEGER, " + mtitle + " TEXT," +
                mbackdroppath + " TEXT," + mposterpath + " TEXT," + mrating + " INTEGER," + mreleasedate + " TEXT," +
                moverview + " TEXT );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tablename);
        onCreate(db);
    }

}