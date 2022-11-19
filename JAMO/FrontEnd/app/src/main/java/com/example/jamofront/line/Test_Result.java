package com.example.jamofront.line;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jamofront.Home_main;
import com.example.jamofront.Task.GetResultTask;
import com.example.jamofront.R;
import com.example.jamofront.Task.GetRoundTask;
import com.example.jamofront.myenhencActivity;
import com.example.jamofront.select_theme1;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Test_Result extends AppCompatActivity {
    String url ="http://3.35.123.120:5000/pretest-result";
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button button;
    public int word, read, listen ,speaking;
    private int round;
    private TextView resultText;

    //선 그래프
    private LineChart lineChart;
    private HorizontalBarChart barChart;
    private GetResultTask httpConn = GetResultTask.getInstance();
    private String url2 = "http://3.35.123.120:5000/pretest-round";
    final  String[] subjects = {"단어파악","읽고 이해하기","듣고 이해하기","말하기"};
    final  String[] bar_subjects = {"말하기", "듣고 이해하기", "읽고 이해하기", "단어파악"};
    private String resultword;
    private String resultread;
    private String resultlisten;
    private String resultspeak;
    private GetRoundTask httpConn3 = GetRoundTask.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_result);
        lineChart = (LineChart) findViewById(R.id.chart); //초기화
        barChart = (HorizontalBarChart) findViewById(R.id.chart2);
        button = findViewById(R.id.button);
        resultText = findViewById(R.id.textView2);
        getRound(url2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (round ==1)
                {
                    Intent intent = new Intent(getApplicationContext(), Home_main.class);
                    startActivity(intent);
                }
                else if (round >= 2)
                {
                    Intent intent = new Intent(getApplicationContext(), myenhencActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private ArrayList<Entry> LinenewResult(int word, int read, int listen, int speaking)
    {
        ArrayList<Entry> LinenewResult = new ArrayList<>();
        LinenewResult.add(new Entry(0,word));
        LinenewResult.add(new Entry(1,read));
        LinenewResult.add(new Entry(2,listen));
        LinenewResult.add(new Entry(3,speaking));
        return  LinenewResult;
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

    private List<BarEntry> MyBarResult(int word, int read, int listen, int speaking) {

        List<BarEntry> dataList = new ArrayList<>();
        dataList.add(new BarEntry(0, word)); //entry_chart1에 좌표 데이터를 담는다.
        dataList.add(new BarEntry(1, read));
        dataList.add(new BarEntry(2, listen));
        dataList.add(new BarEntry(3, speaking));

        return dataList;
    }

    private void makeBar()
    {

        barChart.getLegend().setEnabled(false);
        BarDataSet barDataSet = new BarDataSet(MyBarResult(speaking, listen, read, word), "내 진단검사 결과");

        barDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
        barDataSets.add(barDataSet);
        barDataSet.setValueFormatter(new DefaultValueFormatter(0));

        BarData barData = new BarData(barDataSets);
        barData.setBarWidth(0.5f);
        barData.setValueTextSize(15);
        barChart.setData(barData);
        barChart.invalidate();
        barChart.setTouchEnabled(false);
        barChart.setDragXEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setDragYEnabled(false);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(bar_subjects));
        barChart.getDescription().setEnabled(false);
        //barChart.setBackgroundResource(R.drawable.kidback2);
        //barChart.setBackgroundColor(Color.WHITE);

        XAxis xAxis1 = barChart.getXAxis();

        xAxis1.setDrawAxisLine(true);
        xAxis1.setDrawGridLines(false);
        xAxis1.setGranularity(1f);
        xAxis1.setTextSize(10);
        xAxis1.setGranularityEnabled(true);

        YAxis yAxis1 = barChart.getAxisLeft();
        yAxis1.setDrawLabels(false);
        yAxis1.setDrawAxisLine(true);
        yAxis1.setDrawGridLines(true);
        yAxis1.setGranularity(1f);
        yAxis1.setAxisMinimum(0f);
        yAxis1.setAxisMaximum(100f);


        YAxis rightAxis1 = barChart.getAxisRight();
        rightAxis1.setDrawLabels(true);
        rightAxis1.setDrawAxisLine(true);
        rightAxis1.setDrawGridLines(true);
        rightAxis1.setGranularity(1f);
        rightAxis1.setAxisMinimum(0f);
        rightAxis1.setAxisMaximum(100f);
        rightAxis1.setTextSize(10);
    }

    private void makeLine()
    {

        lineChart.getLegend().setEnabled(false);
        LineDataSet NewSet = new LineDataSet(LinenewResult(word, read, listen ,speaking), "최신검사");
        NewSet.setColor(Color.argb(255,255,255,0));
        NewSet.setValueTextColor(Color.BLACK);
        NewSet.setCircleColor(Color.argb(255,240,240,0));
        NewSet.setValueTextSize(15);
        NewSet.setLineWidth(3);


        LineDataSet Sample = new LineDataSet(SampleData(),"");
        Sample.setColor(Color.argb(0,255,255,255));
        Sample.setValueTextColor(Color.argb(0,255,255,255));
        Sample.setCircleColor(Color.argb(0,255,255,255));
        Sample.setDrawCircleHole(false);

        LineData Dataes = new LineData();
        Dataes.addDataSet(NewSet);
        Dataes.addDataSet(Sample);
        Dataes.setValueFormatter(new DefaultValueFormatter(0));
        lineChart.setData(Dataes);


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(subjects));

       /* xAxis.setSpaceMax(0.2f);
        xAxis.setSpaceMin(0.2f);

        */
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setValueFormatter(new DefaultValueFormatter(0));
        yAxis.setTextSize(15);

        YAxis yAisRight = lineChart.getAxisRight();
        yAisRight.setValueFormatter(new DefaultValueFormatter(0));
        yAisRight.setDrawLabels(false);

        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(false);
        lineChart.invalidate();


    }




    private void getResult(String url) {
        new Thread() {
            public void run() {
                httpConn.getResult(callback,url);

            }}.start();

    }
    private void getResult2(String url) {
        new Thread() {
            public void run() {
                httpConn.getResult2(callback,url);

            }}.start();

    }
    private void getResult3(String url) {
        new Thread() {
            public void run() {
                httpConn.getResult3(callback,url);

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
                word =jsonObject.getInt("word");
                read =jsonObject.getInt("reading");
                listen = jsonObject.getInt("listening");
                speaking = jsonObject.getInt("speaking");
                if(word > 90)
                    resultword = "양호";
                else if (word >= 70 && word <= 90)
                    resultword = "보통";
                else
                    resultword = "위험";

               if(read >= 75)
                   resultread = "양호";
               else if (read >=50)
                   resultread = "보통";
               else
                   resultread = "위험";

               if (listen >= 75)
                   resultlisten = "양호";
               else if(listen>=50)
                   resultlisten = "보통";
               else
                   resultlisten = "위험";

               if (speaking >= 75)
                   resultspeak = "양호";
               else if(speaking >=50)
                   resultspeak = "보통";
               else
                   resultspeak="위험";
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        makeLine();
                        makeBar();
                        resultText.setText("단어파악"+resultword+"      읽고 이해하기"+resultread+"\n듣고이해하기"+resultlisten+"       말하기"+resultspeak);
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
                        if (round == 1)
                            getResult(url);
                        else if (round == 2)
                            getResult2(url);
                        else if (round == 3)
                            getResult3(url);


                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
}








