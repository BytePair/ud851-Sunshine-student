/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.android.sunshine.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);

        // call loadWeatherData to perform the network request to get the weather
        loadWeatherData();
    }

    // create a method that will get the user's preferred location and execute your new AsyncTask and call it loadWeatherData
    private void loadWeatherData() {
        WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask();
        weatherAsyncTask.execute(NetworkUtils.buildUrl("New York"));
    }

    // inner class that extends AsyncTask<Params, Progress, Result> to perform network requests
    private class WeatherAsyncTask extends AsyncTask<URL, Void, String> {

        // override the doInBackground method to perform your network requests
        @Override
        protected String doInBackground(URL... params) {
            String result = null;
            try {
                result = NetworkUtils.getResponseFromHttpUrl(params[0]);
            } catch (IOException e) {
                Log.v(TAG, e.getMessage());
            }
            return result;
        }

        // override the onPostExecute method to display the results of the network request
        @Override
        protected void onPostExecute(String result) {
            mWeatherTextView.setText(result);
            super.onPostExecute(result);
        }
    }

}
