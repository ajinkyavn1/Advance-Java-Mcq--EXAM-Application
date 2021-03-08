package com.gpj.InformationTechnology;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vikasojha.quizbee.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class ResultActivity extends AppCompatActivity {
    TextView tv, tv2, tv3,tv4;
    Button btnRestart;
    int x;
    String Name,subject,cs;
    int enrollment;
    int obtainmarks;
    int wrong;
    private FirebaseFirestore db;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_result);
        tv = findViewById(R.id.tvres);
        tv2 = findViewById(R.id.tvres2);
        tv3 = findViewById(R.id.tvres3);
        tv4= findViewById(R.id.tvres4);

        enrollment=MainActivity2.En;
        Name=MainActivity2.NA;
        cs=MainActivity2.sclass;
        subject="Advance Java";
        wrong=QuestionsActivity.wrong;
        obtainmarks=QuestionsActivity.correct;
        btnRestart = findViewById(R.id.btnRestart);


        StringBuffer sb = new StringBuffer();
        sb.append("Correct answers: ").append(QuestionsActivity.correct).append("\n");
        StringBuffer sb2 = new StringBuffer();
        sb2.append("Wrong Answers: ").append(QuestionsActivity.wrong).append("\n");
        StringBuffer sb3 = new StringBuffer();
        sb3.append("Final Score: ").append(QuestionsActivity.correct).append("\n");
        StringBuffer sb4 = new StringBuffer();
        x=QuestionsActivity.correct+QuestionsActivity.wrong;
        sb4.append("Total Attempted : ").append(x).append("\n");
        tv.setText(sb);
        tv2.setText(sb2);
        tv3.setText(sb3);
        tv4.setText(sb4);
        result();

        drawChart();
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent in = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(in);

            }
        });
        }

         private void drawChart() {
        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);
        int x= QuestionsActivity.correct;
        int y = QuestionsActivity.wrong;
        ArrayList<PieEntry> yvalues = new ArrayList<>();
        yvalues.add(new PieEntry(x, "Right", 0));
        yvalues.add(new PieEntry(y, "Wrong", 1));

        PieDataSet dataSet = new PieDataSet(yvalues, getString(R.string.Exam_results));
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        Description description = new Description();
        description.setText(getString(R.string.pie_chart));
        pieChart.setDescription(description);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(38f);
        pieChart.setHoleRadius(30f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.RED);
        QuestionsActivity.correct=0;
        QuestionsActivity.wrong=0;

        }
        public  void result() {
            CollectionReference dbProducts = db.collection("Results");

            Product product = new Product(
                    Name,enrollment,obtainmarks,subject,wrong,cs
            );
            dbProducts.add(product)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ResultActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }

}
