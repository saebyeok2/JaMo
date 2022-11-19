package com.example.jamofront;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jamofront.Task.GetResultTask;
import com.example.jamofront.Task.GetRoundTask;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class myenhencActivity extends AppCompatActivity {

    //선 그래프
    String url ="http://3.35.123.120:5000/pretest-result";
    private int round;
    private RadarChart radarChart;
    private LineChart lineChart;
    public final String[] labels = {"단어파악","읽고이해하기","듣고이해하기","말하기"};
    public int Lastwordtest,LastRead,LastListen,LastSpeak,NewWord,NewRead,NewListen,NewSpeak;
    private Button button;
    private String url2 = "http://3.35.123.120:5000/pretest-round";
    private GetRoundTask httpConn3 = GetRoundTask.getInstance();
    private GetResultTask httpConn = GetResultTask.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myenhence);
        lineChart = (LineChart) findViewById(R.id.lineChart); //초기화
        radarChart = (RadarChart) findViewById(R.id.radarChart);
        button = findViewById(R.id.button);
        makeRadar();
        makeLine();
        getRound(url2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home_main.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });



    }
    private ArrayList<RadarEntry> oldResult()
    {
        ArrayList<RadarEntry> oldResult = new ArrayList<>();
        oldResult.add(new RadarEntry(Lastwordtest));
        oldResult.add(new RadarEntry(LastRead));
        oldResult.add(new RadarEntry(LastListen));
        oldResult.add(new RadarEntry(LastSpeak));

        return oldResult;
    }
    private ArrayList<RadarEntry> newResult()
    {
        ArrayList<RadarEntry> newResult = new ArrayList<>();
        newResult.add(new RadarEntry(NewWord));
        newResult.add(new RadarEntry(NewRead));
        newResult.add(new RadarEntry(NewListen));
        newResult.add(new RadarEntry(NewSpeak));
        return newResult;
    }
    private ArrayList<Entry> LinenewResult()
    {
        ArrayList<Entry> LinenewResult = new ArrayList<>();
        LinenewResult.add(new Entry(0,NewWord));
        LinenewResult.add(new Entry(1,NewRead));
        LinenewResult.add(new Entry(2,NewListen));
        LinenewResult.add(new Entry(3,NewSpeak));
        return  LinenewResult;
    }
    private ArrayList<Entry> LineoldResult()
    {
        ArrayList<Entry> LineoldResult = new ArrayList<>();
        LineoldResult.add(new Entry(0,Lastwordtest));
        LineoldResult.add(new Entry(1,LastRead));
        LineoldResult.add(new Entry(2,LastListen));
        LineoldResult.add(new Entry(3,LastSpeak));
        return LineoldResult;
    }
    private ArrayList<Entry> SampleData()
    {
        ArrayList<Entry> Sample = new ArrayList<>();
        Sample.add(new Entry(0,9));
        Sample.add(new Entry(1,40));
        Sample.add(new Entry(2,60));
        Sample.add(new Entry(3,95));
        return Sample;
    }



    private void makeLine()
    {
        LineDataSet NewSet = new LineDataSet(LinenewResult(), "최신검사");
        NewSet.setColor(Color.argb(255,255,255,0));
        NewSet.setValueTextColor(Color.BLACK);
        NewSet.setCircleColor(Color.argb(255,240,240,0));
        NewSet.setValueTextSize(15);
        NewSet.setLineWidth(3);

        LineDataSet OldSet = new LineDataSet(LineoldResult(), "이전검사");
        OldSet.setColor(Color.argb(255,125,255,125));
        OldSet.setValueTextColor(Color.argb(255,70,70,70));
        OldSet.setValueTextSize(15);
        OldSet.setLineWidth(3);

        LineDataSet Sample = new LineDataSet(SampleData(),"");
        Sample.setColor(Color.argb(0,255,255,255));
        Sample.setValueTextColor(Color.argb(0,255,255,255));
        Sample.setCircleColor(Color.argb(0,255,255,255));
        Sample.setDrawCircleHole(false);

        LineData Dataes = new LineData();
        Dataes.addDataSet(OldSet);
        Dataes.addDataSet(NewSet);
        Dataes.addDataSet(Sample);
        Dataes.setValueFormatter(new DefaultValueFormatter(0));
        lineChart.setData(Dataes);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

       /* xAxis.setSpaceMax(0.2f);
        xAxis.setSpaceMin(0.2f);

        */
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setValueFormatter(new DefaultValueFormatter(0));
        yAxis.setTextSize(15);

        YAxis yAisRight = lineChart.getAxisRight();
        yAisRight.setValueFormatter(new DefaultValueFormatter(0));
        yAisRight.setDrawLabels(false);

        lineChart.setTouchEnabled(false);
        lineChart.invalidate();


    }

    private void makeRadar()
    {
        RadarDataSet OldSet = new RadarDataSet(oldResult(), "이전검사");
        OldSet.setValueTextColor(Color.argb(255,255,255,255));
        OldSet.setColor(Color.argb(255,150,255,150));
        OldSet.setFillColor(Color.argb(255,150,255,150));
        OldSet.setDrawFilled(true);
        OldSet.setLineWidth(3);

        RadarDataSet NewSet = new RadarDataSet(newResult(),"최신검사");
        NewSet.setValueTextColor(Color.argb(255,255,255,255));
        NewSet.setColor(Color.argb(255,255,255,50));
        NewSet.setHighlightCircleOuterRadius(10);
        NewSet.setLineWidth(3);
        NewSet.setDrawFilled(true);
        NewSet.setFillColor(Color.argb(255,255,255,50));

        RadarData Dataes = new RadarData();
        Dataes.addDataSet(OldSet);
        Dataes.addDataSet(NewSet);
        Dataes.setValueFormatter(new DefaultValueFormatter(0));
        radarChart.setData(Dataes);




        XAxis RxAxis = radarChart.getXAxis();
        RxAxis.setAxisMaximum(100.0f);
        RxAxis.setAxisMinimum(0);
        RxAxis.setTextSize(15);
        RxAxis.setValueFormatter(new DefaultValueFormatter(0));
        RxAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        YAxis RyAxis = radarChart.getYAxis();
        RyAxis.setAxisMaximum(90.0f);
        RyAxis.setAxisMinimum(0);
        RyAxis.setTextColor(Color.argb(150,0,0,0));
        RyAxis.setTextSize(13);
        radarChart.setTouchEnabled(false);
        radarChart.invalidate();
    }

    private void getenhence1(String url) {
        new Thread() {
            public void run() {
                httpConn.getenhence1(callback,url);

            }}.start();

    }
    private void getenhence2(String url) {
        new Thread() {
            public void run() {
                httpConn.getenhence2(callback,url);

            }}.start();

    }

    private okhttp3.Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("//===========//", "=======44444===========");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            try{
                JSONObject jsonObject = new JSONObject(response.body().string());
                Lastwordtest =jsonObject.getInt("oldword");
                LastRead =jsonObject.getInt("oldreading");
                LastListen = jsonObject.getInt("oldlistening");
                LastSpeak = jsonObject.getInt("oldspeaking");
                NewWord =jsonObject.getInt("word");
                NewRead =jsonObject.getInt("reading");
                NewListen = jsonObject.getInt("listening");
                NewSpeak = jsonObject.getInt("speaking");


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        makeLine();
                        makeRadar();
                    }
                });

            } catch(JSONException e)
            {
                e.printStackTrace();
            }
        }
    };


    private void getRound(String url) {
        new Thread() {
            public void run() {
                httpConn3.getround(callback3, url);
            }
        }.start();

    }

    private final okhttp3.Callback callback3 = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("//===========//", "=======44444===========");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            try {
                JSONObject jsonObject = new JSONObject(response.body().string());
                round = jsonObject.getInt("round");
                Log.d("round",""+round);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (round == 2)
                            getenhence1(url);
                        else if (round == 3)
                            getenhence2(url);



                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
}
