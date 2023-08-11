package com.example.apicall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    TextView points,gpa,name,id,crs,intrn;
    CardView menu;
    ScrollView scrollView;
    Button options;
    List<String> internshipTitles = new ArrayList<>();
    List<String> courseTitles = new ArrayList<>();
    List<String> internshipDurations = new ArrayList<>();
    List<String> courseDurations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         points = (TextView)findViewById(R.id.hpoints);
         gpa = (TextView) findViewById(R.id.gpa);
          name = (TextView) findViewById(R.id.name);
          id = (TextView) findViewById(R.id._id);
         scrollView = (ScrollView)findViewById(R.id.scroll);
        crs = (TextView) findViewById(R.id.courses);
        intrn = (TextView) findViewById(R.id.inter);
        //options = (Button) findViewById(R.id.options);
        menu = (CardView) findViewById(R.id.menu);




          Intent fromlogin = getIntent();
         String usermail =  fromlogin.getStringExtra("mail");

        String baseurl = "https://vm-g2ap.onrender.com/student/";
         String url  = baseurl + usermail;
         String url1 = url + "/internships";
        String url2 = url + "/courses";
       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {
               try {
                  String ugpa = response.getString("gpa");
                   gpa.setText("GPA : "+ugpa);
                   String uname = response.getString("name");
                   String Id = response.getString("_id");
                   String uscore = response.getString("score");


                   name.setText(uname);
                   id.setText("reg_Id : "+Id);
                   points.setText(uscore);



               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

               name.setText("error");
           }
       });
        JsonArrayRequest internshipsRequest = new JsonArrayRequest(
                Request.Method.GET,
                url1,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject internship = response.getJSONObject(i);
                                String title = internship.getString("title");
                                String time = internship.getString("duration");
                                internshipTitles.add(title);
                                internshipDurations.add(time);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        StringBuilder internshipText = new StringBuilder();
                        for (int i = 0; i < internshipTitles.size(); i++) {
                            internshipText.append("Title : "+internshipTitles.get(i)).append(" \n").append("Duration : "+internshipDurations.get(i)+" month/months").append("\n\n");
                        }
                        intrn.setText(internshipText.toString());
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                }
        );

        JsonArrayRequest coursesRequest = new JsonArrayRequest(
                Request.Method.GET,
                url2,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject course = response.getJSONObject(i);
                                String title = course.getString("title");
                                courseTitles.add(title);
                                String time = course.getString("duration");
                                courseDurations.add(time);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Update the course title TextView
                        StringBuilder courseText = new StringBuilder();
                        for (int i = 0; i < courseTitles.size(); i++) {
                            courseText.append("Title : "+courseTitles.get(i)).append(" \n").append("Duration : "+courseDurations.get(i)+" hr").append("\n\n");
                        }
                        crs.setText(courseText.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(internshipsRequest);
        requestQueue.add(jsonObjectRequest);
        requestQueue.add(coursesRequest);


    }
}