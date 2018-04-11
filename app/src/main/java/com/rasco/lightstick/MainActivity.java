package com.rasco.lightstick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rasco.lightstick.base.ColorCombination;
import com.rasco.lightstick.color.R;
import com.rasco.lightstick.colorcombination.ColorCombinationAdapter;
import com.rasco.lightstick.colorcombination.ColorCombinationDetailActivity;
import com.rasco.lightstick.controller.Controller;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ColorCombinationAdapter colorComboAdapter;

    ArrayList<ColorCombination> allColorCombos;
    Controller controller = new Controller(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allColorCombos = controller.getAllColorCombos();
        colorComboAdapter = new ColorCombinationAdapter(this, allColorCombos);
        ListView colorCombinationList = (ListView) findViewById(R.id.colorcombination_list);
        colorCombinationList.setAdapter(colorComboAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(),ColorCombinationDetailActivity.class);
            startActivity(intent);
            }
        });

        colorCombinationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ColorCombination ccclicked = colorComboAdapter.getColorCombos(position);

                Intent intent = new Intent(getApplicationContext(),ColorCombinationDetailActivity.class);

                String colorCombinationId = ccclicked.get_id() + "";
                intent.putExtra("id", colorCombinationId);
                //based on item add info to intent
                startActivity(intent);
            }
        });

    }
}
