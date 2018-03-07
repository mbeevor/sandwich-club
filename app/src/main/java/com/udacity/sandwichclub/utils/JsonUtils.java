package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // constants for key strings in JSON
    public static final String NAME = "name";
    public static final String MAIN_NAME = "mainName";
    public static final String ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String INGREDIENTS = "ingredients";
    public static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";

    public static Sandwich parseSandwichJson(String json) {

        JSONObject jsonObject;
        String mainName;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<>();

        try {

            //instantiate JSONObject
            jsonObject = new JSONObject(json);

            //Find and assign sandwich name
            JSONObject nameJsonObject = jsonObject.getJSONObject(NAME);
            mainName = nameJsonObject.optString(MAIN_NAME);

            // Assign remaining objects with values
            placeOfOrigin = jsonObject.optString(PLACE_OF_ORIGIN);
            description = jsonObject.optString(DESCRIPTION);
            image = jsonObject.optString(IMAGE);

            // Use JSONArray for also-known-as name
            JSONArray alsoKnownAsArray = nameJsonObject.getJSONArray(ALSO_KNOWN_AS);
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                if (alsoKnownAsArray != null) {
                    String alsoKnownAsString = alsoKnownAsArray.optString(i);
                    alsoKnownAs.add(alsoKnownAsString);
                }

            }

            // Use JSONArray for list of ingredients
            JSONArray ingredientsArray = jsonObject.getJSONArray(INGREDIENTS);
            for (int j = 0; j < ingredientsArray.length(); j++) {
                String ingredientsString = ingredientsArray.optString(j);
                ingredients.add(ingredientsString);
            }

            // return new sandwich using above values in constructor
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin,
                    description, image, ingredients);

            // catch exception, and print error to log
        } catch (final JSONException e) {
            Log.e("JsonUtils", "Error in Json", e);
            // return 'null' if try/catch fails
            return null;

        }

    }
}
