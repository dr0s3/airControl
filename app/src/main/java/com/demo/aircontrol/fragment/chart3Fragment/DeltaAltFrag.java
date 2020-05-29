package com.demo.aircontrol.fragment.chart3Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.demo.aircontrol.DroneData;
import com.demo.aircontrol.R;
import com.demo.aircontrol.util.ui.ChartsMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class DeltaAltFrag extends Fragment {

    private DroneData droneData;
    private Context context;

    @NonNull
    public static Fragment newInstance() {
        return new DeltaAltFrag();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_delta_chart, container, false);
        droneData = DroneData.getInstance();

        LineChart chart = v.findViewById(R.id.line_chart);
        TextView avgText = v.findViewById(R.id.text2);
        TextView maxText = v.findViewById(R.id.text4);
        TextView minText = v.findViewById(R.id.text6);
        TextView varianceText = v.findViewById(R.id.text8);

        List<Entry> entries = new ArrayList<Entry>();

        //get data
        ArrayList<Double> data = droneData.getGpsAlt();

        //set Bottom Text
        avgText.setText(DroneData.calcAvg(data).toString());
        maxText.setText(DroneData.calcMax(data).toString());
        minText.setText(DroneData.calcMin(data).toString());
        varianceText.setText(DroneData.calcVariance(data).toString());

        // turn data into Entry objects
        int i = 0;
        for (double d : data) {
            entries.add(new Entry(i, (float) (d)));
            i++;
        }

        // add entries to dataset
        LineDataSet dataSet = new LineDataSet(entries, "高度");
        dataSet.setCircleColor(Color.BLACK);
        dataSet.setCircleRadius(1f);

        LineData lineData = new LineData(dataSet);

        //设置 MarkerView
        ChartsMarkerView mv = new ChartsMarkerView(context, R.layout.custom_marker_view, 3);
        mv.setChartView(chart);
        chart.setMarker(mv);
        //Change Yaxis
        //chart.getAxisLeft().setValueFormatter(new ValueOffsetFormatter(droneData.latAnchor));
        //删除右侧轴线
        chart.getAxisRight().setEnabled(false);

        //设置图表描述
        chart.getDescription().setText("高度变化曲线");
//        dataSet.setColor(...);
//        dataSet.setValueTextColor(...);

        chart.setData(lineData);
        chart.invalidate();
        return v;
    }

}
