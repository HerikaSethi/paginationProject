package com.example.paginationprojectjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.paginationprojectjava.model.MainData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    //array list created holding our maindata
    ArrayList<MainData> dataArrayList = new ArrayList<>();
    //initializing our adapter
    PicSumAdapter adapter;
    int page = 1, limit =10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nestedScrollView = findViewById(R.id.nestedScrollView);
        recyclerView = findViewById(R.id.Rcv);
        progressBar = findViewById(R.id.progressBar);

        //initializing the adapter
        adapter = new PicSumAdapter(MainActivity.this,dataArrayList);
        //setting the layout manager for our recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        
        getdata(page, limit);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight())
                {
                    //when we have reaced the last position then
                    //increase the page size
                    page++;
                    //and show the progress bar till loading takes plce
                    progressBar.setVisibility(View.VISIBLE);
                    //then again calling the method
                    getdata(page,limit);
                }
            }
        });


    }

    private void getdata(int page, int limit) {
        //retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://picsum.photos/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        //interface
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<String> call = apiInterface.STRING_CALL(page, limit);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body()!=null){
                    //if i m getting the response and body of response is not null
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        //now parsing the jsonarray
                        parse(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("check", "onFailure: Unable to fetch response");
            }
        });
    }

    private void parse(JSONArray jsonArray) {
        for (int i=0;i<jsonArray.length();i++){
            try {
                //initializing the json object
                JSONObject object = jsonArray.getJSONObject(i);
                MainData data =new MainData();
                //now setting the image (download_url link in picsum data)
                data.setImage(object.getString("download_url"));
                data.setName(object.getString("author"));
                //adding this data to my arraylist
                dataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter = new PicSumAdapter(MainActivity.this,dataArrayList);
            recyclerView.setAdapter(adapter);
        }
    }
}