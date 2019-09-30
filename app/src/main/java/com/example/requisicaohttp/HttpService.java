package com.example.requisicaohttp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class HttpService extends AsyncTask<Void, Void, JSONObject> {

    JSONObject jObect = null;
    private final String cep;
    String json = "";

    public HttpService(String cep) {
        this.cep = cep;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {

        URL pesquisaCep = createURL("https://viacep.com.br/ws/" + this.cep + "/json/");
        StringBuilder resposta = new StringBuilder();


        try {
            URL url = new URL("http://ws.matheuscastiglioni.com.br/ws/cep/find/" + this.cep + "/json/");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.connect();


            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                resposta.append(scanner.next());
            }
            json = resposta.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Transforma a String de resposta em um JSonObject
        try {
            jObect = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObect;
    }


    private URL createURL(String endPoint) {
        String baseUrl = endPoint;
        try {
            String urlString = baseUrl;
            return new URL(urlString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}