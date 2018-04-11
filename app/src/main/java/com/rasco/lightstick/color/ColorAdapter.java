package com.rasco.lightstick.color;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import com.rasco.lightstick.base.Color;
import com.rasco.lightstick.controller.Controller;

import java.util.ArrayList;

public class ColorAdapter extends ArrayAdapter<Color> {

    Controller controller = new Controller(getContext());
    ArrayList<Color> colors;

    public ColorAdapter(@NonNull Context context, ArrayList<Color> colors) {
        super(context, 0, colors);
        this.colors =colors;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Color color = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_color, parent, false);
        }

        ImageButton deleteRow = (ImageButton) convertView.findViewById(R.id.deleteRow);
        deleteRow.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                removeItemFromList(color);
            }
        });

        convertView.setBackgroundColor(android.graphics.Color.parseColor(color.getColor_rbg()));
        return convertView;
    }

    public Color getColor(int position) {
        return getItem(position);
    }

    // Method for remove Single item from list
    protected void removeItemFromList(final Color color)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Alert!");
        builder.setMessage("Do you want delete this color?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        controller.deleteColor(color);
                        colors.remove(color);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        dialog.cancel();
                    }
                });
        AlertDialog  alertDialog = builder.create();
        alertDialog.show();

    }
}
