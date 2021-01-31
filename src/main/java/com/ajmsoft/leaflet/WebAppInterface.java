package com.ajmsoft.leaflet;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class WebAppInterface {
    Context mContext;

    StringBuilder sb=null;
    BufferedReader reader=null;
    String serverResponse="";
    TextView cityView,countyView,stateView,countryView;


    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast,String latlng) {
        Toast.makeText(mContext,"Location Changed", Toast.LENGTH_SHORT).show();
        cityView = (TextView) ((Activity)mContext).findViewById(R.id.city);
        countyView = (TextView) ((Activity)mContext).findViewById(R.id.county);
        //stateView = (TextView) ((Activity)mContext).findViewById(R.id.state);
        countryView = (TextView) ((Activity)mContext).findViewById(R.id.country);

        try {

            String latitude = latlng.substring(7,latlng.length()-1).split(",")[0];
            String longtude = latlng.substring(7,latlng.length()-1).split(",")[1];
            URL url = new URL("https://us1.locationiq.com/v1/reverse.php?key=pk.02804fb75750468390d32aa08e09f7fb&lat="+latitude+"&lon="+longtude+"&format=json");
            HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            connection.connect();
            int statusCode = connection.getResponseCode();
            if(statusCode==200){
                sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line =reader.readLine())!=null){
                    sb.append(line+ "\n");
                }

            }
            connection.disconnect();
            if(sb!=null) {
                serverResponse = sb.toString();
                JSONObject jsonObj = new JSONObject(serverResponse);
                JSONObject address = jsonObj.getJSONObject("address");
                cityView.setText(address.getString("city"));
                countyView.setText(address.getString("state"));
                countryView.setText(address.getString("country"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            cityView.setText("Undefined");
            countyView.setText("Undefined");
            //stateView.setText("Undefined");
            countryView.setText("Undefined");
        }


    }

}