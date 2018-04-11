package com.rasco.lightstick.colorcombination;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rasco.lightstick.color.R;
import com.rasco.lightstick.base.ColorCombination;

import java.util.ArrayList;

public class ColorCombinationAdapter extends ArrayAdapter<ColorCombination> {

    public ColorCombinationAdapter(Context context, ArrayList<ColorCombination> colors) {
        super(context, 0, colors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ColorCombination colorCombination = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_color_combo, parent, false);
        }
        // Lookup view for data population
        TextView ccName = (TextView) convertView.findViewById(R.id.cc_name);
        // Populate the data into the template view using the data object
        //programId.setText(program.get_id());
        ccName.setText(colorCombination.getName());
        // Return the completed view to render on screen
        return convertView;
    }

    public ColorCombination getColorCombos(int position) {
        return getItem(position);
    }
}
