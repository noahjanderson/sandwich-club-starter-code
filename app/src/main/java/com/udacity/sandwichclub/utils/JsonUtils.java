package com.udacity.sandwichclub.utils;

import android.util.JsonReader;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich returnValue = null;
        try {
            JSONObject object = new JSONObject(json);
            JSONObject nameObj = object.getJSONObject("name");
            String mainName = nameObj.getString("mainName");
            String placeOfOrigin = object.getString("placeOfOrigin");
            String description = object.getString("description");
            String image = object.getString("image");
            List<String> alsoKnownAsArray = getArrayListFromJsonArray(nameObj.getJSONArray("alsoKnownAs"));
            List<String> ingredientsArray = getArrayListFromJsonArray(object.getJSONArray("ingredients"));
            returnValue = new Sandwich(mainName,alsoKnownAsArray,placeOfOrigin,description,image,ingredientsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    private static List<String> getArrayListFromJsonArray(JSONArray arr){
        List<String> arrList = new ArrayList<>();
        try {
        for (int i = 0; i < arr.length(); i++){

                arrList.add(arr.getString(i));
            }
        } catch (JSONException e) {
                e.printStackTrace();
        }
        return arrList;
    }

}
