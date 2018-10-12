package com.example.android.movieapponetestone.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movieapponetestone.R;
import com.example.android.movieapponetestone.adapter.ModelAdapter;
import com.example.android.movieapponetestone.api.App;
import com.example.android.movieapponetestone.model.Popular;
import com.example.android.movieapponetestone.model.Result;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList <Popular> popularArrayList;

    public static final String KEY = "5571cb8edc0c8203b51ddfb985abd954";

    private RecyclerView recyclerView;
    private ModelAdapter adapter;
    private TextView emptyView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.model_list);

        recyclerView = findViewById(R.id.rv_model);

        popularArrayList = new ArrayList<>();

        adapter = new ModelAdapter(popularArrayList, getApplicationContext());

        emptyView = findViewById(R.id.empty_view);

        int numberOfColumns = 2;

        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        recyclerView.setAdapter(adapter);

        getMovies(App.getApi().getPopularMovies(KEY), "Sorry, ... Internet to view the most popular movies.");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.movie_menu, menu);
        return true;
    }

    private void emptyView() {
        if (popularArrayList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.popular:
                getMovies(App.getApi().getPopularMovies(KEY), "Sorry, ... Internet to view the most popular movies.");
                return true;
            case R.id.top_rated:
                getMovies(App.getApi().getTopRatedMovies(KEY), "Sorry, ... Internet to view the most top rated movies.");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void getMovies(Call<Result> resultCall, final String message) {

       resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                popularArrayList.clear();
                adapter.setData(response.body().getResults());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                emptyView();

            }


        });

    }



}
