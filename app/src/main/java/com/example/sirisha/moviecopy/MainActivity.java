package com.example.sirisha.moviecopy;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    static final String api_key = BuildConfig.api;
    @BindView(R.id.movieslist)
    RecyclerView movieapp;
    String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + api_key;
    public static String contexte="popular",arrow="myKey";
    public String SCROLL_KEY="scrollKey";
    public static int scroll_value=1,scroll_val_fav=1;
    GridLayoutManager myFavLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (isonline()) {

            if (savedInstanceState != null) {
                Log.i("inElsenooIf",contexte);
                if (contexte.equals("popular"))
                {
                    new MyAsyncTask(this, movieapp)
                            .execute("https://api.themoviedb.org/3/movie/popular?api_key=" + api_key);

                }
                else if (contexte.equals("rating"))
                {
                    new MyAsyncTask(this, movieapp)
                            .execute("https://api.themoviedb.org/3/movie/top_rated?api_key=" + api_key);

                }
                else if (contexte.equals("favorites"))
                {
                    dbhelper();
                }
                else {
                    new MyAsyncTask(this, movieapp).execute("https://api.themoviedb.org/3/movie/popular?api_key=" + api_key);
                }
            }
            else
            {
                new MyAsyncTask(this, movieapp).execute("https://api.themoviedb.org/3/movie/popular?api_key=" + api_key);
            }
        } else {
            Toast.makeText(this, "NO NETwork Connection", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (contexte == "favorites"){
            dbhelper();
        }
    }
    protected boolean isonline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.adapter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String st;
            switch (item.getItemId()) {

                case R.id.popular:
                    if (isonline()) {
                    contexte="popular";
                    new MyAsyncTask(this, movieapp).execute("https://api.themoviedb.org/3/movie/popular?api_key=" + api_key);
                    break;}
                    else {
                        Toast.makeText(this, "NO Network Connection", Toast.LENGTH_SHORT).show();
                        break;
                    }
                case R.id.rating:
                    if (isonline()) {
                    contexte="rating";
                    new MyAsyncTask(this, movieapp).execute("https://api.themoviedb.org/3/movie/top_rated?api_key=" + api_key);
                    break;}
                    else {
                        Toast.makeText(this, "NO Network Connection", Toast.LENGTH_SHORT).show();
                        break;
                    }
                case R.id.favourites:
                    contexte="favorites";
                    dbhelper();
                    break;
                default:
                    return super.onOptionsItemSelected(item);
            }
        return true;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(arrow,contexte);

        scroll_value=MyAsyncTask.myLayoutManager.findFirstVisibleItemPosition();

        if(contexte=="favorites") {
            scroll_val_fav = myFavLayoutManager.findFirstVisibleItemPosition();
            outState.putInt("myFavScrollPos",scroll_val_fav);
        }
        outState.putInt(SCROLL_KEY,scroll_value);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        contexte=savedInstanceState.getString(arrow);
        scroll_value=savedInstanceState.getInt(SCROLL_KEY);
        scroll_val_fav=savedInstanceState.getInt("myFavScrollPos");
    }

    private void dbhelper() {
            Cursor moviedetails=getContentResolver().query(SqlLiteHelper.CONTENT_URI,null,null,null,null);
            ArrayList<Pojo> moviedbinfo=new ArrayList<>();
           if(moviedetails.getCount()>0)
            {
                if(moviedetails.moveToFirst()) {
                    do {
                        Pojo movieinformation = new Pojo(moviedetails.getString(1),moviedetails.getString(3),moviedetails.getString(4),moviedetails.getString(5),moviedetails.getString(7),moviedetails.getString(6),moviedetails.getString(2));
                        moviedbinfo.add(movieinformation);
                    } while (moviedetails.moveToNext());
                }
            }
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Return to Movie copy ?");
                builder.setTitle("No Favorites");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(isonline()) {
                            new MyAsyncTask(MainActivity.this, movieapp).execute("https://api.themoviedb.org/3/movie/popular?api_key=" + api_key);
                        }else {
                            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
                builder.show();
             }
        MyAdapter myAdapter=new MyAdapter(MainActivity.this,moviedbinfo);
        RecyclerView rv=findViewById(R.id.movieslist);
        myFavLayoutManager=new GridLayoutManager(this,2);
        rv.setLayoutManager(myFavLayoutManager);
        rv.setAdapter(myAdapter);
        rv.scrollToPosition(scroll_val_fav);

    }
}
