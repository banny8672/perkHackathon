package com.example.bansi.perkhackathon.WebServiceManager;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.bansi.perkhackathon.SingltonClass.AppController;
import com.example.bansi.perkhackathon.Models.Question;
import com.example.bansi.perkhackathon.CallBackInterFace.ResultCallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bansi on 20-Dec-15.
 */
public class WebService {

    private Context mContext;
    String TAG = "WebService";
    public WebService(Context mContext){

        this.mContext = mContext;
    }


    public void getQuestion(final ResultCallBack<List<Question>> resultCallBackListner){

        String url = "http://52.77.213.129/demo.json";

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "bhargav :: " + response.toString());
                        List<Question> questions = null;
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.putOpt("data",response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            questions = new Gson().fromJson(jsonObject.getString("data"),new TypeToken<ArrayList<Question>>(){
                            }.getType());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        resultCallBackListner.onResultCallBack(questions, null);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                resultCallBackListner.onResultCallBack(null, error.getMessage());

            }
        });

        AppController.getInstance().addToRequestQueue(req);
    }
}
