package com.example.issac.marvel;

import android.app.Activity;
import android.app.ListActivity;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.issac.marvel.adapters.ituneArrayAdapter;
import com.example.issac.marvel.adapters.superheroeArrayAdapter;
import com.example.issac.marvel.pojo.itune;
import com.example.issac.marvel.pojo.superheroe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private superheroeArrayAdapter superheroeArrayAdapter;
    private RequestQueue mQueue;
    private Integer offset = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lista);
        //superheroeArrayAdapter = new superheroeArrayAdapter(this,R.layout.itunes, new ArrayList<superheroe>());

        arrayAdapter = new ArrayAdapter<String>(this,
                  android.R.layout.simple_list_item_1, new ArrayList<String>());

        listView.setAdapter(arrayAdapter);
        //new MarvelJson(superheroeArrayAdapter).execute();
        mQueue = VolleySingleton.getInstance(this).getRequestQueue();
        jsonMarvel(getMarvelString(offset.toString()), arrayAdapter);



    }



    public void next (View view){
        offset+=100;
        jsonMarvel(getMarvelString(offset.toString()), arrayAdapter);
    }
    public void previous (View view){
        offset-=100;
        jsonMarvel(getMarvelString(offset.toString()), arrayAdapter);
    }



    private final String LOG_TAG = "MARVEL";

    private static char[] HEXCodes = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    private void jsonMarvel(String url, final ArrayAdapter<String> adapter){
        adapter.clear();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    JSONArray jsonArray = data.getJSONArray("results");
                    for (int i=0 ; i<jsonArray.length() ; i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        adapter.add(jsonObject.getString("name"));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);
    }

    private String getMarvelString(String offset){
        String ts = Long.toString(System.currentTimeMillis() / 1000);
        String apikey = "1681a9eefcf8fbf43de66c59727718da";
        String hash = md5(ts + "ede49375699321e3736436b53011574333433f40" + "1681a9eefcf8fbf43de66c59727718da");
        ArrayList<superheroe> arrayList = new ArrayList<>();


            /*
                Conexión con el getway de marvel
            */
        final String CHARACTER_BASE_URL =
                "http://gateway.marvel.com/v1/public/characters";
            /*
                Configuración de la petición
            */
        String characterJsonStr = null;
        final String TIMESTAMP = "ts";
        final String API_KEY = "apikey";
        final String HASH = "hash";
        final String ORDER = "orderBy";

        Uri builtUri;
        builtUri = Uri.parse(CHARACTER_BASE_URL+"?").buildUpon()
                .appendQueryParameter(TIMESTAMP, ts)
                .appendQueryParameter(API_KEY, apikey)
                .appendQueryParameter(HASH, hash)
                .appendQueryParameter(ORDER, "name")
                .appendQueryParameter("limit", "100")
                .appendQueryParameter("offset", offset)
                .build();

            /*
                Ejecución de la conexión
            */
            return builtUri.toString();
    }



    /*

    public class MarvelJson extends AsyncTask< String, Integer,ArrayList<superheroe>>{
        private superheroeArrayAdapter adapter;

        public MarvelJson(superheroeArrayAdapter adapter){
            this.adapter = adapter;
        }
        @Override
        protected ArrayList<superheroe> doInBackground(String... urls) {



            String ts = Long.toString(System.currentTimeMillis() / 1000);
            String apikey = "1681a9eefcf8fbf43de66c59727718da";
            String hash = md5(ts + "ede49375699321e3736436b53011574333433f40" + "1681a9eefcf8fbf43de66c59727718da");
            ArrayList<superheroe> arrayList = new ArrayList<>();



            final String CHARACTER_BASE_URL =
                    "http://gateway.marvel.com/v1/public/characters";

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String characterJsonStr = null;
            final String TIMESTAMP = "ts";
            final String API_KEY = "apikey";
            final String HASH = "hash";
            final String ORDER = "orderBy";

            Uri builtUri;
            builtUri = Uri.parse(CHARACTER_BASE_URL+"?").buildUpon()
                    .appendQueryParameter(TIMESTAMP, ts)
                    .appendQueryParameter(API_KEY, apikey)
                    .appendQueryParameter(HASH, hash)
                    .appendQueryParameter(ORDER, "name")
                    .build();

            try {


                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                Log.d(LOG_TAG, "Response: " + urlConnection.getResponseCode() + " - " + urlConnection.getResponseMessage());

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                characterJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }



                try {

                    JSONObject jsonObject = new JSONObject(characterJsonStr);
                    String h=jsonObject.getString("data");
                    jsonObject = new JSONObject(h);
                    JSONArray jsArray =   jsonObject.getJSONArray("results");

                    for (int i = 0; i<jsArray.length(); i++){
                        JSONObject dato = jsArray.getJSONObject(i);
                        superheroe superheroe = new superheroe();
                        superheroe.name = dato.getString("name");
                        superheroe.type = dato.getString("type");
                        arrayList.add(superheroe);
                    }
                } catch (JSONException e) {
                e.printStackTrace();
                }
                return arrayList;










        }*/
/*
        @Override
        protected void onPostExecute(ArrayList<superheroe> strings) {
            adapter.clear();
            adapter.addAll(strings);
            adapter.notifyDataSetChanged();
        }
    }
*/

    /*
        Investiga y reporta qué es md5:
        Es una función criptopgráfica de una sola dirección que permite el ingreso
        de una cadena de longitud indefinida y rergesa una llave de longitud fija
        para poder descifrar del otro lado la cadena que se manda

    */
    public static String md5(String s) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            String hash = new String(hexEncode(digest.digest()));
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String hexEncode(byte[] bytes) {
        char[] result = new char[bytes.length*2];
        int b;
        for (int i = 0, j = 0; i < bytes.length; i++) {
            b = bytes[i] & 0xff;
            result[j++] = HEXCodes[b >> 4];
            result[j++] = HEXCodes[b & 0xf];
        }
        return new String(result);
    }


}
