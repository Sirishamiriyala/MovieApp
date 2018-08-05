package com.example.sirisha.moviecopy;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

/**
 * Created by sirisha on 15-05-2018.
 */



class MyAsyncTask extends AsyncTask<String,Void,String> {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    Context context;
    ArrayList<Pojo> data= new ArrayList<>();
    public static GridLayoutManager myLayoutManager;

    public MyAsyncTask(MainActivity mainActivity, RecyclerView movieapp) {

        this.context=mainActivity;
        this.recyclerView=movieapp;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog= new ProgressDialog(context);
        progressDialog.setTitle("Movie App....");
        progressDialog.setMessage("wait for a while....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {


        String url=strings[0];
        StringBuilder output=new StringBuilder();
        try {
            URL buildUrl =new URL(url);
            HttpURLConnection connection=(HttpURLConnection)buildUrl.openConnection();
            InputStream inputStream=connection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while (line!=null)
            {
                line=bufferedReader.readLine();
                output.append(line);
            }
            return output.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        String poster2,poster,title,analysis,rel_date,lang,mtitle;
        String vote_count,vote_avg,Ident,popularity;
        boolean video,adult;
        try{
            JSONObject jsonObject =new JSONObject(s);
            JSONArray jsonArray =jsonObject.getJSONArray("results");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jb=jsonArray.getJSONObject(i);
                Log.i("jsonobject",jb.toString());
                title=jb.getString("title");
                poster="https://image.tmdb.org/t/p/w500"+jb.getString("poster_path");
                poster2="https://image.tmdb.org/t/p/w500"+jb.getString("backdrop_path");
                analysis=jb.getString("overview");
                rel_date=jb.getString("release_date");
                vote_avg=jb.getString("vote_average");
                vote_count=jb.getString("vote_count");
                popularity=jb.getString("popularity");
                video=jb.getBoolean("video");
                Ident=jb.getString("id");
                adult=jb.getBoolean("adult");
                mtitle=jb.getString("original_title");
                lang=jb.getString("original_language");

                Pojo p=new Pojo(poster2,poster,title,analysis,rel_date,vote_avg,vote_count,popularity,video,Ident,adult,mtitle,lang);
                data.add(p);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        myLayoutManager=new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(myLayoutManager);
        recyclerView.setAdapter(new MyAdapter(context,data));
        recyclerView.scrollToPosition(MainActivity.scroll_value);
    }

}