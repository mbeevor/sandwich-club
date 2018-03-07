package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    Sandwich sandwich;
    public ImageView sandwichIv;
    public TextView descriptionLabelTv;
    public TextView descriptionTv;
    public TextView alsoKnownAsLabelTv;
    public TextView alsoKnownAsTv;
    public TextView ingredientsLabelTv;
    public TextView ingredientsTv;
    public TextView originLabelTv;
    public TextView originTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        sandwichIv = findViewById(R.id.image_iv);
        descriptionLabelTv = findViewById(R.id.description_label_tv);
        descriptionTv = findViewById(R.id.description_tv);
        alsoKnownAsLabelTv = findViewById(R.id.also_known_label_tv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        ingredientsLabelTv = findViewById(R.id.ingredients_label_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        originLabelTv = findViewById(R.id.origin_label_tv);
        originTv = findViewById(R.id.origin_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwichIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        // Set description using data from Json, hiding label and TextView if empty
        if (sandwich.getDescription().isEmpty()) {
            descriptionLabelTv.setVisibility(View.GONE);
            descriptionTv.setVisibility(View.GONE);
        } else {
            descriptionTv.setText(sandwich.getDescription());
        }

        // Set place of origin using data from Json, hiding label and TextView if empty
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            originLabelTv.setVisibility(View.GONE);
            originTv.setVisibility(View.GONE);
        } else {
            originTv.setText(sandwich.getPlaceOfOrigin());
        }

        /* Set "also known as" using data from Json, using TextUtils.join
        *to separate values with ","; hiding label and TextView if empty
         */
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownAsTv.setVisibility(View.GONE);
            alsoKnownAsLabelTv.setVisibility(View.GONE);
        } else {
            alsoKnownAsTv.setText(android.text.TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        }

        /* Set ingredients using data from Json, using TextUtils.join
        *to separate values with ","; hiding label and TextView if empty
         */
        if (sandwich.getIngredients().isEmpty()) {
            ingredientsTv.setVisibility(View.GONE);
            ingredientsLabelTv.setVisibility(View.GONE);
        } else {
            ingredientsTv.setText(android.text.TextUtils.join(", ", sandwich.getIngredients()));
        }

    }
}
