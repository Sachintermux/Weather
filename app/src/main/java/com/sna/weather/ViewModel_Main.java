package com.sna.weather;

import android.os.StrictMode;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class ViewModel_Main extends ViewModel {

    MutableLiveData<String> temperature = new MutableLiveData<>("80");
    MutableLiveData<String> cloudStatus = new MutableLiveData<>("Clear");
    MutableLiveData<String> location = new MutableLiveData<>("Austin, TX");

    private static String makeHttpRequest( URL url ) throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream( InputStream inputStream ) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public void getData( String lat, String longitude ) throws Exception {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + longitude + "&appid=182e894aa3e3b074a7a664c59e650aa7&units=imperial";

        URL url1 = new URL(url);
        String data = makeHttpRequest(url1);
        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonArray = jsonObject.getJSONArray("weather");
        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
        cloudStatus.setValue(jsonObject1.getString("main"));
        temperature.setValue(jsonObject.getJSONObject("main").getString("temp") + "°");
        location.setValue(jsonObject.getString("name") + ", " + jsonObject.getJSONObject("sys").getString("country"));

    }
}
