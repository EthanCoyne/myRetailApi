package com.target.case_study.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class to be extended by services wishing to query APIs
 *
 */
@Service
public class BaseApiService {
    String url;

    /**
     * Classes extending the BaseApiService can use this method to set the base URL of a given API it will use.
     * @param url URL of the intended API to query
     */
    void setBaseUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @param url URL of the intended API to query
     * @return String result of query
     * @throws IOException IOException connection failure
     */
    String getPayload(final String url) throws IOException {
        HttpURLConnection connection = null;

        try {
            URL connectionUrl = new URL(url);

            connection = (HttpURLConnection) connectionUrl.openConnection();

            StringBuilder stringBuilder = new StringBuilder();

            BufferedReader bufferedReader = setBufferedReader(connection);

            if (connection.getResponseCode() == 200) {
                String responseLine;
                while ((responseLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(responseLine);
                    stringBuilder.append("\n");
                }
            }

            return stringBuilder.toString();

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    /**
     * Reads text from a character-input stream
     *
     * @param connection HttpURLConnection
     * @return BufferedReader
     */
    private static BufferedReader setBufferedReader(HttpURLConnection connection) {
        BufferedReader bufferedReader = null;

        try {
            if (connection.getResponseCode() == 200) {
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
            }
            else if (connection.getErrorStream() != null) {
                InputStreamReader errorStreamReader = new InputStreamReader(connection.getErrorStream());
                bufferedReader = new BufferedReader(errorStreamReader);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return bufferedReader;
    }
}
