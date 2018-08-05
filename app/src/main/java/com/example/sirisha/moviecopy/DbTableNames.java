package com.example.sirisha.moviecopy;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by sirisha on 31-05-2018.
 */

public class DbTableNames  {
    public static final String  AUTHORITE = "com.example.sirisha.moviecopy";
    public static final Uri BASE_CONTENT_URI= Uri.parse("content://"+AUTHORITE);
    public static final String PATH_TASK = "favourites";

    public static final class TableData implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASK).build();

        public static final String TABLE_NAME="favourites";
        public static final String COLUMN_MOVIE_ID="mid";
        public static final String COLUMN_MOVIE_TITLE="Title";
        public static final String COLUMN_MOVIE_IMAGE="Image";
        public static final String COLUMN_MOVIE_RATE="Rating";
        public static final String COLUMN_MOVIE_RD="Release";
    }
}
