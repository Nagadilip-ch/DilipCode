package com.example.dilip.sampleproject;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dilip.sampleproject.adapter.CustomListAdapter;
import com.example.dilip.sampleproject.app.AppController;
import com.example.dilip.sampleproject.model.CountryDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener{

    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    //  json url
    private static final String url = "https://dl.dropboxusercontent.com/u/746330/facts.json";
    private ProgressDialog pDialog;
    private List<CountryDetails> detailsList = new ArrayList<CountryDetails>();
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CustomListAdapter adapter;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();

        listView = (ListView) findViewById(R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);


        adapter = new CustomListAdapter(this, detailsList);
        listView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        makeJsonRequest();
                                          }
                                }
        );



    }



    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {

        detailsList.clear();
        makeJsonRequest();
    }

    private void makeJsonRequest()
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());

                        // Parsing Json
                        try{
                            String mainTitle = response.getString("title");
                            actionBar.setTitle(mainTitle);

                            // json array
                            JSONArray rowsArry = response.getJSONArray("rows");
                            ArrayList<String> rows = new ArrayList<String>();
                            for (int j = 0; j < rowsArry.length(); j++) {

                                JSONObject obj = rowsArry.getJSONObject(j);
                                CountryDetails details = new CountryDetails();
                                details.setTitle(obj.getString("title"));
                                details.setThumbnailUrl(obj.getString("imageHref"));
                                details.setDescription(obj.getString("description"));

                                detailsList.add(details);


                            }
                        }catch (JSONException exp)
                        {
                            exp.printStackTrace();
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();

                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);

                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());

                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "json_obj");

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll("json_obj");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
