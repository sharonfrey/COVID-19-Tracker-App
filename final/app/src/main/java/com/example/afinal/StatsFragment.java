package com.example.afinal;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;


public class StatsFragment extends Fragment {

    public static final String TAG = "StatsFragment";


    private RequestQueue queue;
    private View view;
    private String region, url;
    private TextView txtName,txtTotal,txtActive, txtDeaths, txtRecovered, txtCritical, txtTested, txtDeathRatio, txtRecoveryRatio, txtDate;
    private JSONObject data, summary, spots, main;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stats, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtName = (TextView) view.findViewById(R.id.name);
        txtActive = (TextView) view.findViewById(R.id.active_cases);
        txtTotal = (TextView) view.findViewById(R.id.total_cases);
        txtDeaths = (TextView) view.findViewById(R.id.deaths);
        txtRecovered = (TextView) view.findViewById(R.id.recovered);
        txtCritical = (TextView) view.findViewById(R.id.critical);
        txtTested = (TextView) view.findViewById(R.id.tested);
        txtDeathRatio = (TextView) view.findViewById(R.id.death_ratio);
        txtRecoveryRatio = (TextView) view.findViewById(R.id.recovery_ratio);
        txtDate = (TextView) view.findViewById(R.id.date);
    }

    protected void displayReceivedData(String message)
    {

        region = message;
        Log.d(TAG, "Message recieved: " + region);

        if(region.toLowerCase().equals("hell")) {
            region = "bermuda";
        }

        url = "https://api.quarantine.country/api/v1/summary/region?region=" + region;

        queue = MySingleton.getInstance(this.getContext()).getRequestQueue();
        queue.start();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //get all data from api
                            data = response.getJSONObject("data");
                            //get update date for app
                            spots = data.getJSONObject("spots");
                            Iterator<String> keys = spots.keys();
                            String key = keys.next();
                            txtDate.setText(key);
                            //get name of region
                            main = spots.getJSONObject(key);
                            txtName.setText(main.get("name") + " Stats");
                            //COVID-19 stats
                            summary = data.getJSONObject("summary");
                            txtTotal.setText("Total Cases: " + summary.get("total_cases").toString());
                            txtActive.setText("Active Cases: " + summary.get("active_cases").toString());
                            txtDeaths.setText("Deaths: " + summary.get("deaths").toString());
                            txtRecovered.setText("Recovered: " + summary.get("recovered").toString());
                            txtCritical.setText("Critical: " + summary.get("critical").toString());
                            txtTested.setText("Tested: " + summary.get("tested").toString());
                            txtDeathRatio.setText("Death Ratio: " + summary.get("death_ratio").toString().substring(0,4));
                            txtRecoveryRatio.setText("Recovery Ratio: " + summary.get("recovery_ratio").toString().substring(0,4));


                        } catch (JSONException e) {
                            Log.d(TAG, "JSONException error: " + e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "VolleyError error: " + error);
                    }
                });
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

    }


}
