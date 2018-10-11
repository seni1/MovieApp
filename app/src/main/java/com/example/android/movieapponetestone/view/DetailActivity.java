package com.example.android.movieapponetestone.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.movieapponetestone.R;
import com.example.android.movieapponetestone.model.Popular;

import java.io.Serializable;

public class DetailActivity extends AppCompatActivity {

    public static final String KEY = "key";

    TextView title;
    TextView userRating;
    TextView releaseDate;
    TextView synopsis;
    ImageView posterImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.model_detail);

        init();
        getData();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init() {
        title = findViewById(R.id.tv_title);
        userRating = findViewById(R.id.user_rating);
        releaseDate = findViewById(R.id.release_date);
        synopsis = findViewById(R.id.synopsis);
        posterImage = findViewById(R.id.poster_image);

    }

    public static void newIntent(Context context, Popular popular) {

        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable(KEY, popular);
        intent.putExtras(bundle);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void getData() {
        Popular data = (Popular) getIntent().getSerializableExtra(KEY);
        title.setText(data.getTitle());
        releaseDate.setText(data.getReleaseDate());
        synopsis.setText(data.getOverview());
        userRating.setText(String.valueOf(data.getVoteAverage()));


        Glide.with(this).load("http://image.tmdb.org/t/p/w185" + data.getPosterPath()).into(posterImage);
    }


}
