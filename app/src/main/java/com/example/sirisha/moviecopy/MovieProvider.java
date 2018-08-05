package com.example.sirisha.moviecopy;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.net.URI;

import retrofit2.http.PUT;

/**
 * Created by sirisha on 31-05-2018.
 */

public class MovieProvider extends ContentProvider {
    public static final int CODE_INSERT=100;
    public static final int CODE_INSERT_ID=101;
    private static final UriMatcher sURiMatcher=buildUriMatcher();
    private SqlLiteHelper sqlhelper;
    public static UriMatcher buildUriMatcher(){
        UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
        String auth= SqlLiteHelper.AUTHORITY;
        matcher.addURI(auth, SqlLiteHelper.path_Tasks,CODE_INSERT);
        matcher.addURI(auth, SqlLiteHelper.path_Tasks+"/*",CODE_INSERT_ID);
        return matcher;
    }
    @Override
    public boolean onCreate() {
        sqlhelper=new SqlLiteHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sURiMatcher.match(uri)){
            case CODE_INSERT:{
                cursor = sqlhelper.getReadableDatabase().query(SqlLiteHelper.tablename,
                        null, null,null, null, null, null);
                break;
            }
            case CODE_INSERT_ID: {
                cursor = sqlhelper.getReadableDatabase().query(SqlLiteHelper.tablename,
                        null, selection, selectionArgs, null, null, null);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri"+ uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id;
        SQLiteDatabase databasehelper=sqlhelper.getWritableDatabase();
        switch (sURiMatcher.match(uri))
        {
            case CODE_INSERT:
                id=databasehelper.insert(SqlLiteHelper.tablename,null,values);
                break;
            default:throw new UnsupportedOperationException("uri not known"+uri);
        }
        Uri u=Uri.parse(""+id);
        return u;
    }

    @Override
    public int delete(Uri uri,String selection,String[] selectionArgs) {
        int rowdeleted = 0;
        SQLiteDatabase database=sqlhelper.getWritableDatabase();
        switch (sURiMatcher.match(uri))
        {
            case CODE_INSERT_ID:
                rowdeleted=database.delete(SqlLiteHelper.tablename,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
        if(rowdeleted!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowdeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
