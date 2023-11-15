package com.example.json_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test extends AppCompatActivity {
    List<QuoteModel> list = new ArrayList<>();

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ReadJSON().execute("https://dummyjson.com/quotes");

    }

    private class ReadJSON extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            recyclerView = findViewById(R.id.rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(Test.this));
            recyclerView.setHasFixedSize(true);
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while((line=bufferedReader.readLine())!=null){
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("quotes");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject objectQuote = array.getJSONObject(i);
                    int id = objectQuote.getInt("id");
                    String quote = objectQuote.getString("quote");
                    String author = objectQuote.getString("author");
                    QuoteModel quoteModel = new QuoteModel(id,quote,author);
                    list.add(quoteModel);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            MyAdapter adapter = new MyAdapter(Test.this,list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}