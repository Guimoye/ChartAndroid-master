package com.example.sumit.chartandroid;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Barras extends AppCompatActivity {
    private ProgressDialog pd;

    ArrayList<BarDataSet> yAxis;
    ArrayList<BarEntry> yValues;
    ArrayList<String> xAxis1;
    BarEntry values ;
    BarChart chart;
    String name[]= new String[3];
    String score[]=new String[3];
    BarDataSet barDataSet1;

    BarData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barra);
        pd = new ProgressDialog(Barras.this);
        pd.setMessage("loading");

        name[0]= "Guimoye";
        name[1]= "Javier";
        name[2]= "Montilla";


        score[0]= "2.3";
        score[1]= "40.3";
        score[2]= "22.3";



       // Log.d("array",Arrays.toString(fullData));
        chart = (BarChart) findViewById(R.id.chart);
       // load_data_from_server();
        cargarDatos();
    }

    public void load_data_from_server() {
        pd.show();
        String url = "http://192.168.1.14/freebieslearning/chart.php";
        xAxis1 = new ArrayList<>();
        yAxis = null;
        yValues = new ArrayList<>();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("string",response);

                        try {

                            JSONArray jsonarray = new JSONArray(response);

                            for(int i=0; i < jsonarray.length(); i++) {

                                JSONObject jsonobject = jsonarray.getJSONObject(i);


                                String score = jsonobject.getString("score").trim();
                                String name = jsonobject.getString("name").trim();

                                xAxis1.add(name);

                                values = new BarEntry(Float.valueOf(score),i);
                                yValues.add(values);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();


                        }





                        BarDataSet barDataSet1 = new BarDataSet(yValues, "Goals LaLiga 16/17");
                        barDataSet1.setColor(Color.rgb(0, 82, 159));



                        yAxis = new ArrayList<>();
                        yAxis.add(barDataSet1);
                        String names[]= xAxis1.toArray(new String[xAxis1.size()]);
                        data = new BarData(names,yAxis);
                        chart.setData(data);
                        chart.setDescription("");
                        chart.animateXY(2000, 2000);
                        chart.invalidate();
                        pd.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error != null){

                            Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
                            pd.hide();
                        }
                    }
                }

        );

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    public void cargarDatos() {
        pd.show();
        xAxis1 = new ArrayList<>();
        yAxis = null;
        yValues = new ArrayList<>();


        for(int i=0; i < name.length; i++) {

            xAxis1.add(name[i]);
            values = new BarEntry(Float.valueOf(score[i]),i);
            yValues.add(values);
            barDataSet1 = new BarDataSet(yValues, name[i]+" = "+i);
            barDataSet1.setColor(Color.rgb(0, 82, 159*i));
            yAxis           = new ArrayList<>();
            yAxis.add(barDataSet1);
        }


        String names[]  = xAxis1.toArray(new String[xAxis1.size()]);
        data            = new BarData(names,yAxis);
        chart.setData(data);
        chart.setDescription("");
        chart.animateXY(2000, 2000);
        chart.invalidate();
        pd.hide();

    }


}
