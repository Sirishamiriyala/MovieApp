package com.example.sirisha.moviecopy;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
public class SecondActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MoviePOJO>> {

    public static final int ldr_id=9;
    public static final int review_id=3;
    static final String api_key=BuildConfig.api;
    ArrayList<RetrospectPOJO> reviewList=new ArrayList<>();
    ArrayList<MoviePOJO> TrailerList=new ArrayList<>();

    String ReviewUrl="http://api.themoviedb.org/3/movie/";
    String trailerUrl="http://api.themoviedb.org/3/movie/";

    StringBuilder stringBuilder;

    public static RecyclerView recyclerView_videos;
    public static RecyclerView recyclerView_reviews;
    String str;
    int movi;
    SqlLiteHelper sqlLiteHelper;
    private ArrayList<String> moviedetails=new ArrayList<>();
    @BindView(R.id.title) TextView name;
    @BindView(R.id.release) TextView rele_date;
    @BindView(R.id.plot_des) TextView sysn;
    @BindView(R.id.rating) TextView vote_avg;
    @BindView(R.id.poster) ImageView poster;
    @BindView(R.id.favorites)
    ToggleButton favorites;
    @BindView(R.id.id_no_review_tv) TextView no_review_tv;
    @BindView(R.id.id_no_video_tv) TextView no_video_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        recyclerView_reviews=(RecyclerView)findViewById(R.id.review);
        recyclerView_videos=(RecyclerView)findViewById(R.id.Recyclerview);
        ButterKnife.bind(this);
        final String[] getDetails=getIntent().getStringArrayExtra("details_must_required");

        moviedetails=getIntent().getStringArrayListExtra("results");

        Picasso.with(this).load(getDetails[2]).placeholder(R.mipmap.ic_launcher_round).into(poster);
        Picasso.with(this).load(getDetails[5]).placeholder(R.mipmap.ic_launcher_round).into(poster);
        //moviedetails=getIntent().getStringArrayListExtra("results");
        sqlLiteHelper=new SqlLiteHelper(this);
        Log.i("image","title");
        rele_date.setText(getDetails[1]);
        name.setText(getDetails[0]);
        sysn.setText(getDetails[4]);
        vote_avg.setText(getDetails[7]);
        str=getDetails[9];
        movi = Integer.parseInt(getDetails[9].toString());
        Log.i("movie", String.valueOf(movi));
        Cursor cursor=getContentResolver().query(Uri.parse(SqlLiteHelper.CONTENT_URI+"/*"),null,SqlLiteHelper.mid+" LIKE ?",new String[]{getDetails[9]},null);
      //  f = sqlLiteHelper.isFav(movi);
        if (cursor.getCount()>0){
            favorites.setChecked(true);
        }else
            favorites.setChecked(false);
        favorites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Cursor cursor=getContentResolver().query(Uri.parse(SqlLiteHelper.CONTENT_URI+"/*"),null,SqlLiteHelper.mid+" LIKE ?",new String[]{getDetails[9]},null);
                if(cursor.getCount()>=1)
                    getContentResolver().delete(Uri.parse(SqlLiteHelper.CONTENT_URI+"/*"),SqlLiteHelper.mtitle + " LIKE ?",new String[]{getDetails[0]});
                else
                {
                    ContentValues moviedetailinfovalues=new ContentValues();
                    moviedetailinfovalues.put(SqlLiteHelper.mid,getDetails[9]);
                    moviedetailinfovalues.put(SqlLiteHelper.mtitle,getDetails[0]);
                    moviedetailinfovalues.put(SqlLiteHelper.mbackdroppath,getDetails[5]);
                    moviedetailinfovalues.put(SqlLiteHelper.mposterpath,getDetails[2]);
                    moviedetailinfovalues.put(SqlLiteHelper.mrating,getDetails[7]);
                    moviedetailinfovalues.put(SqlLiteHelper.mreleasedate,getDetails[1]);
                    moviedetailinfovalues.put(SqlLiteHelper.moverview,getDetails[4]);
                    getContentResolver().insert(Uri.parse(SqlLiteHelper.CONTENT_URI+""),moviedetailinfovalues);
                }
            }
        });
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            no_review_tv.setVisibility(View.INVISIBLE);
            no_video_tv.setVisibility(View.INVISIBLE);
            getLoaderManager().restartLoader(ldr_id,null,this);
            getLoaderManager().restartLoader(review_id,null,this);
        } else {
            no_review_tv.setVisibility(View.VISIBLE);
            no_video_tv.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public  Loader<ArrayList<MoviePOJO>> onCreateLoader(final int id, Bundle args) {
        switch (id){
            case ldr_id:
                return new AsyncTaskLoader<ArrayList<MoviePOJO>>(this) {
                    @Override
                    public ArrayList<MoviePOJO> loadInBackground() {
                        StringBuilder stringBuilder=new StringBuilder();
                        Uri uri=Uri.parse(trailerUrl).buildUpon().appendPath(str).
                                appendPath("videos").appendQueryParameter("api_key",api_key).build();
                        Log.i("background",uri.toString());
                        URL url=null;
                        try {
                            url=new URL(uri.toString());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        try {
                            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                            httpURLConnection.setRequestMethod("GET");
                            InputStream inputStream=httpURLConnection.getInputStream();
                            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                            String st="";
                            while ((st=bufferedReader.readLine())!=null){
                                stringBuilder.append(st).append('\n');
                                inputStream.close();
                            }
                        } catch (IOException e) {
                            Log.i("connection excep",e.toString());
                        }if(stringBuilder!=null){
                            try {
                                JSONObject object=new JSONObject(stringBuilder.toString());
                                JSONArray jsonArray=object.getJSONArray("results");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String key=jsonObject.getString("name");
                                    String type=jsonObject.optString("type");
                                    MoviePOJO moviePOJO =new MoviePOJO(key,type);
                                    TrailerList.add(moviePOJO);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        return TrailerList;
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        forceLoad();
                    }
                };
            case review_id:
                return new AsyncTaskLoader<ArrayList<MoviePOJO>>(this) {
                    @Override
                    public ArrayList<MoviePOJO> loadInBackground() {
                        stringBuilder=new StringBuilder();
                        Uri u=Uri.parse(ReviewUrl).buildUpon().
                                appendPath(str).appendPath("reviews")
                                .appendQueryParameter("api_key", api_key).build();
                        Log.d("Review-doInBackground",u.toString());
                        URL url=null;
                        try {
                            url=new URL(u.toString());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        try {
                            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                            urlConnection.setRequestMethod("GET");
                            InputStream inputStream=urlConnection.getInputStream();
                            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
                            String string="";
                            while ((string=bufferedReader.readLine())!=null){
                                stringBuilder.append(string).append('\n');
                                inputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(stringBuilder!=null){
                            try {
                                JSONObject jsonObject=new JSONObject(stringBuilder.toString());
                                JSONArray jsonArray=jsonObject.getJSONArray("results");
                                for (int i=0;i < jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                   // String URL=object.getString("url");
                                    String Writer=object.getString("author");
                                   // String id=object.getString("id");
                                    String dec=object.getString("content");
                                    RetrospectPOJO retrospectPOJO=new RetrospectPOJO(Writer,dec);
                                    reviewList.add(retrospectPOJO);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.i("exceptions", e.toString());
                            }
                        }
                        return null;
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        forceLoad();
                    }
                };
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MoviePOJO>> loader, ArrayList<MoviePOJO> data) {
        switch (loader.getId()){
            case ldr_id:
                recyclerView_videos.setLayoutManager(new LinearLayoutManager(SecondActivity.this));
                recyclerView_videos.setAdapter(new MovieAdapter(SecondActivity.this,TrailerList));
                break;
            case  review_id:
                recyclerView_reviews.setLayoutManager(new LinearLayoutManager(SecondActivity.this));
                recyclerView_reviews.setAdapter(new RetrospectAdapter(SecondActivity.this,reviewList));
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MoviePOJO>> loader) {
    }
}
