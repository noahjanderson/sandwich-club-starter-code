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

import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mOriginTv;
    private TextView mDescriptionTv;
    private TextView mAlsoKnownTv;
    private TextView mIngredientsTv;
    private TextView mOriginLabelTv;
    private TextView mDescriptionLabelTv;
    private TextView mAlsoKnownLabelTv;
    private TextView mIngredientsLabelTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mDescriptionTv = findViewById(R.id.description_tv);
        mOriginTv = findViewById(R.id.origin_tv);
        mIngredientsTv = findViewById(R.id.ingredients_tv);
        mAlsoKnownTv = findViewById(R.id.also_known_tv);
        mDescriptionLabelTv = findViewById(R.id.description_label_tv);
        mOriginLabelTv = findViewById(R.id.place_of_origin_label_tv);
        mIngredientsLabelTv = findViewById(R.id.ingredients_label_tv);
        mAlsoKnownLabelTv = findViewById(R.id.also_known_label_tv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher) //not my fav icon but hey
                .error(R.mipmap.ic_launcher)
                .into(ingredientsIv);
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        setAndShoworHideTV(mAlsoKnownTv, mAlsoKnownLabelTv, getStringFromArrayList(sandwich.getAlsoKnownAs()));
        setAndShoworHideTV(mIngredientsTv, mIngredientsLabelTv, getStringFromArrayList(sandwich.getIngredients()));
        setAndShoworHideTV(mDescriptionTv, mDescriptionLabelTv, sandwich.getDescription());
        setAndShoworHideTV(mOriginTv, mOriginLabelTv, sandwich.getPlaceOfOrigin());
    }

    private void setAndShoworHideTV(TextView tv, TextView tvLabel, String textToSet){
        if(textToSet.isEmpty()){
            tv.setText("No Data Found");
//            tv.setVisibility(View.INVISIBLE);
//            tvLabel.setVisibility(View.INVISIBLE);
        }
        else{
//            tv.setVisibility(View.VISIBLE);
//            tvLabel.setVisibility(View.VISIBLE);
            tv.setText(textToSet);
        }
    }

    //Thank you very much for pointing this out.
    private String getStringFromArrayList(List<String> arr){
        StringBuilder returnValue = new StringBuilder();
        for (int i = 0; i < arr.size(); i++) {
            returnValue.append((i==0) ? arr.get(i) : "\n" + arr.get(i));
        }
        return returnValue.toString();
    }


}
