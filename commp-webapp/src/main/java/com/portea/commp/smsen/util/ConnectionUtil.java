package com.portea.commp.smsen.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ConnectionUtil {


    public static String sendGet(String url) throws Exception{

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        InputStream inputStream = con.getInputStream();


        BufferedReader in = new BufferedReader(
                new InputStreamReader(inputStream));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }

    public static String encode(String message) throws UnsupportedEncodingException {
        return URLEncoder.encode(message, "UTF-8");
    }

    /**
     * Returns a Connection holder with a buffered reader and a connection made to a given URL.
     * If the response code is 200 OK, buffered reader is created from the connection input stream otherwise
     * it is set to null. Connection is still maintained so that the response code can be read later.
     *
     */
    public static ConnectionHolder getConnectionReader(String url) throws Exception{
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        InputStream inputStream;
        BufferedReader bufferedReader = null;

        try {
            if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = con.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            }
        }
        catch (IOException e) {
            System.err.print("Error: "+e.getMessage());
        }

        return new ConnectionHolder(con, bufferedReader);
    }

}
