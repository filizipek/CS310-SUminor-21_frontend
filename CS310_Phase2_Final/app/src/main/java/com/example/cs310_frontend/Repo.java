package com.example.cs310_frontend;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Repo {
    private Context ctx;

    public Repo(Context context) {
        this.ctx = context;
    }
    public void searchMajor(ExecutorService srv, Handler uiHandler, String major) {
        srv.execute(() -> {
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/CourseAppCS310/courses/search?program=" + major);
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/JSON");

                //int responseCode = conn.getResponseCode();
                //if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedInputStream reader = new BufferedInputStream(conn.getInputStream());
                StringBuilder buffer = new StringBuilder();
                int chr;
                while ((chr = reader.read()) != -1) {
                    buffer.append((char) chr);
                }

                Log.d("Repo", "Response: " + buffer.toString()); // Add this log

                JSONArray arr = new JSONArray(buffer.toString());
                List<OnlyList> data = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject currentObj = arr.getJSONObject(i);
                    data.add(new OnlyList(currentObj.getString("program"), currentObj.getString("type"),
                            currentObj.getString("code")));
                }

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);
                //} else {
                //  Log.e("Repo", "HTTP Error: " + responseCode);
                //}
            } catch (Exception e) {
                Log.e("Repo", "Error fetching data", e);
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        });
    }

    public void searchMinor(ExecutorService srv, Handler uiHandler, String minor){
        srv.execute(() -> {
            try {
                URL url = new URL("http://10.0.2.2:8080/CourseAppCS310/courses/search?program=" + minor);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/JSON");

                JSONObject objToSend = new JSONObject();
                objToSend.put("minor", minor);


                BufferedInputStream reader = new BufferedInputStream(conn.getInputStream());
                StringBuilder buffer = new StringBuilder();
                int chr = 0;
                while ((chr = reader.read()) != -1) {

                    buffer.append((char) chr);
                }

                JSONArray arr = new JSONArray(buffer.toString());
                List<OnlyList> data = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {

                    JSONObject currentObj = arr.getJSONObject(i);

                    data.add(new OnlyList(currentObj.getString("program"), currentObj.getString("type"),
                            currentObj.getString("code")));

                }

                Message msg2 = new Message();
                msg2.obj = data;
                uiHandler.sendMessage(msg2);

            } catch (ProtocolException e) {
                throw new RuntimeException(e);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void getCommonCourses(ExecutorService srv, Handler uiHandler, String major, String minor) {
        srv.execute(() -> {
            try {
                List<OnlyList> majorCourses = fetchCourses("http://10.0.2.2:8080/CourseAppCS310/courses/search?program=" + major);

                List<OnlyList> minorCourses = fetchCourses("http://10.0.2.2:8080/CourseAppCS310/courses/search?program=" + minor);

                List<OnlyList> commonCourses = findCommonCourses(majorCourses, minorCourses);

                if (commonCourses.isEmpty()) {
                    runOnUiThread(() -> Toast.makeText(ctx, major + " and " + minor + " have no courses in common!", Toast.LENGTH_SHORT).show());
                    return;
                }

                Message msg = uiHandler.obtainMessage();
                msg.obj = commonCourses;
                uiHandler.sendMessage(msg);

            } catch (Exception e) {
                Log.e("Repo", "Error fetching common courses", e);
            }
        });
    }

    private void runOnUiThread(Runnable action) {
        new Handler(ctx.getMainLooper()).post(action);
    }

    private List<OnlyList> fetchCourses(String urlString) throws IOException, JSONException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/JSON");

        BufferedInputStream reader = new BufferedInputStream(conn.getInputStream());
        StringBuilder buffer = new StringBuilder();
        int chr;
        while ((chr = reader.read()) != -1) {
            buffer.append((char) chr);
        }

        JSONArray arr = new JSONArray(buffer.toString());
        List<OnlyList> data = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject currentObj = arr.getJSONObject(i);
            data.add(new OnlyList(currentObj.getString("program"), currentObj.getString("type"),
                    currentObj.getString("code")));
        }

        return data;
    }

    private List<OnlyList> findCommonCourses(List<OnlyList> majorCourses, List<OnlyList> minorCourses) {
        List<OnlyList> commonCourses = new ArrayList<>();
        for (OnlyList majorCourse : majorCourses) {
            for (OnlyList minorCourse : minorCourses) {
                if (majorCourse.getCode().equals(minorCourse.getCode()) && !majorCourse.getType().equals("Free")) {
                    commonCourses.add(majorCourse);
                    break;
                }
            }
        }
        return commonCourses;
    }
}