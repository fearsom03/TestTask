package com.example.mtravel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private ListView listViewLastNews;
    private RecyclerView recyclerViewTour, recyclerViewObjects;
    private TourAdapter tourAdapter;
    private ObjectsAdapter objectsAdapter;
    private NewsAdapter listNewsAdapter;
    private RecyclerView.LayoutManager layoutManager,layoutManager1;
    private JSONArray jsonArray;
    private int countObjects;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewLastNews = findViewById(R.id.listViewLastNews);
        listViewLastNews.setFastScrollAlwaysVisible(true);
        recyclerViewTour =findViewById(R.id.recicleFirst);
        recyclerViewObjects=findViewById(R.id.recicleSecond);
        String urlTour="http://gotravelto.kz/api/v1/tour?region_id=17&as_json=1";
        String objectsJSON="http://gotravelto.kz/api/v1/object?region_id=17&as_json=1&limit=20";
        String lastNewsJSON = "http://gotravelto.kz/api/v1/news?region_id=17&as_json=1&limit=30";
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewTour.setHasFixedSize(true);
        recyclerViewTour.setLayoutManager(layoutManager);
        recyclerViewObjects.setHasFixedSize(true);
        recyclerViewObjects.setLayoutManager(layoutManager1);
        searching(urlTour);
        displayTours(countObjects, jsonArray);
        System.out.println("TOUR FINISH");
        searching(objectsJSON);
        displayObjects(countObjects,jsonArray);
        System.out.println("OBJECTS FINISH");
        searching(lastNewsJSON);
       // displayNews(countObjects,jsonArray);
    }




        private void displayObjects(int countObjects,JSONArray result){
            objectsAdapter = new ObjectsAdapter(countObjects,result);
            recyclerViewObjects.setAdapter(objectsAdapter);
        }
        private void displayTours(int countObjects,JSONArray result){
            tourAdapter = new TourAdapter(countObjects,result);
            recyclerViewTour.setAdapter(tourAdapter);
        }
        private void displayNews(int countObjects ,JSONArray result){
            listNewsAdapter = new NewsAdapter(this,result);
            System.out.println("KUUKA");
            System.out.println(result);
            System.out.println(listNewsAdapter.toString());
            listViewLastNews.setAdapter(listNewsAdapter);
        }


        private void searching(String url){
            System.out.println("Start of searching");
            final RequestQueue queue = Volley.newRequestQueue(this);
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, response.toString());
                    try {
                        System.err.println("Middle of searching");
                        jsonArray = response.getJSONArray("results");
                        System.out.println(jsonArray);
                        countObjects=response.getInt("count");
                        System.out.println(countObjects);
                        displayObjects(countObjects,jsonArray);
                        displayTours(countObjects,jsonArray);
                        displayNews(countObjects,jsonArray);

                    } catch (JSONException e) {
                        System.out.println("ERROR");
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("ERROR 1");
                    error.printStackTrace();
                }
            });
            System.out.println("END of searching");
            queue.add(request);
        }



    public static String decoderUnicode(String uniText){
        byte[] bytes = uniText.getBytes(StandardCharsets.UTF_8);
        String regularText = new String(bytes, StandardCharsets.UTF_8);
        return regularText;
    }

    /*private void searching(String urlTour) {
        System.out.println("Start of SEARCHING");
        StringRequest stringRequest = new StringRequest(Request.Method.GET,urlTour,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray result = new JSONArray(response);
                            for (int i = 0; i < result.length(); i++)
                            {
                                JSONObject c = result.getJSONObject(i);
                                 countObjects = c.getInt("count");
                                 tour = c.getJSONObject("result");
                                System.out.println("Middle of SEARCHING");
                            }
                        } catch (JSONException e) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("ERRORR 1");
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);        System.out.println("END of SEARCHING");
    }*/
}
