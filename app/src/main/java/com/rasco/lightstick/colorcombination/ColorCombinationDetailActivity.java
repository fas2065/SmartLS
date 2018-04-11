package com.rasco.lightstick.colorcombination;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.MenuInflater;
import android.widget.ViewSwitcher;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import com.rasco.lightstick.MainActivity;
import com.rasco.lightstick.base.Color;
import com.rasco.lightstick.color.ColorAdapter;
import com.rasco.lightstick.color.R;
import com.rasco.lightstick.base.ColorCombination;
import com.rasco.lightstick.controller.Controller;
import com.rasco.lightstick.play.PlayLightStick;

import java.util.ArrayList;

public class ColorCombinationDetailActivity extends AppCompatActivity {

    EditText ccNameEdit;
    TextView ccNameView;
    FloatingActionButton addColor;
    FloatingActionButton play;
    ViewSwitcher switcher;

    MenuItem editMenuItem;
    MenuItem saveMenuItem;
    MenuItem deleteMenuItem;
    State ccState;

    Controller controller = new Controller(this);
    ColorCombination colorCombination =null;
    ArrayList<Color> colorsList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colorcombination_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ccNameEdit = (EditText) findViewById(R.id.ccNameEdit);
        ccNameView = (TextView) findViewById(R.id.ccNameView);
        addColor = (FloatingActionButton) findViewById(R.id.addColor);
        play = (FloatingActionButton) findViewById(R.id.play);
        switcher = (ViewSwitcher) findViewById(R.id.cc_name_switcher);

        Intent intent = getIntent();
        intent.getStringExtra("id");
        if (null != intent.getStringExtra("id")){
            int id = Integer.valueOf(intent.getStringExtra("id"));
            colorCombination = controller.getColorCombo(id);
            colorsList = colorCombination.getColors();
            uiViewColorCombination(colorCombination);
        } else {
            colorCombination  = new ColorCombination();
            uiNewColorCombination(colorCombination);
        }
        addColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addColorToNewColorCombo();
            }
        });
    }

    private void uiNewColorCombination(ColorCombination colorCombination) {
        ccNameView.setVisibility(View.GONE);
        ccNameEdit.setVisibility(View.VISIBLE);
        ccNameEdit.setText("");
        addColor.setVisibility(View.VISIBLE);
        play.setVisibility(View.GONE);
        ccState = State.NEW;
    }

    private void uiEditColorCombination() {
        ccNameEdit.setVisibility(View.GONE);
        ccNameView.setVisibility(View.VISIBLE);
        ccNameView.setText(colorCombination.getName());
        play.setVisibility(View.GONE);
        addColor.setVisibility(View.VISIBLE);
        addColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addColorToNewColorCombo();
            }
        });
        ccState = State.EDIT;
        invalidateOptionsMenu();
        showColors(colorCombination);

    }

    private void uiViewColorCombination(ColorCombination colorCombination) {
        ccNameEdit.setVisibility(View.GONE);
        ccNameView.setVisibility(View.VISIBLE);
        ccNameView.setText(colorCombination.getName());
        addColor.setVisibility(View.GONE);
        showColors(colorCombination);
        ccState = State.VIEW;
        if (colorsList.size() > 0) {
            play.setVisibility(View.VISIBLE);
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    play();
                }
            });
        } else {
            play.setVisibility(View.GONE);
        }
    }

    private void addColorToCC(int selectedColor) {
        Color color = new Color();
        color.setCc_id(colorCombination.get_id());
        color.setColor_rbg("#" + Integer.toHexString(selectedColor));
        controller.addColor(color);
        uiEditColorCombination();
    }

    private void showColors(ColorCombination colorCombination) {
        colorsList = controller.getAllCcColors(colorCombination.get_id());
        ColorAdapter colorAdapter = new ColorAdapter(this, colorsList);
        ListView colors = (ListView) findViewById(R.id.color_list);
        colors.setAdapter(colorAdapter);
    }

    private void play() {
        Intent intent = new Intent(getApplicationContext(),PlayLightStick.class);
        intent.putStringArrayListExtra("colors", getColorsArrayString(colorsList));
        startActivity(intent);
    }

    private ArrayList<String> getColorsArrayString(ArrayList<Color> colors){
        ArrayList<String> colorStrings = new ArrayList<String>();
        for (Color color: colors) {
            colorStrings.add(color.getColor_rbg());
        }
        return colorStrings;
    }

    private void deleteColorCombination() {
        controller.deleteColorCombo(colorCombination);

        backToMainActivity();
    }

    private void backToMainActivity() {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainIntent);
    }

    private void saveColorCombination() {
        colorCombination = new ColorCombination(ccNameEdit.getText().toString().trim());
        colorCombination = controller.addColorCombo(colorCombination);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.app_bar_save) {
            // if state new save go back
            if (ccState == State.NEW) {
                saveColorCombination();
            }
            backToMainActivity();
        } else if (itemId == R.id.app_bar_edit) {
            uiEditColorCombination();
        } else if (itemId == R.id.app_bar_delete) {
            alertDelete();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.colorcombination_menu, menu);
        editMenuItem = menu.findItem(R.id.app_bar_edit);
        saveMenuItem = menu.findItem(R.id.app_bar_save);
        deleteMenuItem = menu.findItem(R.id.app_bar_delete);

        switch(ccState){
            case EDIT:
                editMenuItem.setVisible(false);
                deleteMenuItem.setVisible(true);
                saveMenuItem.setVisible(true);
                break;
            case NEW:
                editMenuItem.setVisible(false);
                deleteMenuItem.setVisible(false);
                saveMenuItem.setVisible(true);
                break;
            case VIEW:
                saveMenuItem.setVisible(false);
                deleteMenuItem.setVisible(true);
                editMenuItem.setVisible(true);
                break;
            default:
                saveMenuItem.setVisible(false);
                break;
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void addColorToNewColorCombo() {
        if (ccState == State.NEW) {
            saveColorCombination();
            uiEditColorCombination();
            pickColor();
        } else if (ccState == State.EDIT) {
            pickColor();
        }
    }

    private void pickColor() {
        ColorPickerDialogBuilder
                .with(this)
                .setTitle("Choose color")
                .initialColor(0xffffff)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(4)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        addColorToCC(selectedColor);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    private void alertDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Deleting ColorCombo")
                .setMessage("Do you want to delete the " + colorCombination.getName() + "?")
                .setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do nothing
                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteColorCombination();
            }
        }).show();
    }
    public enum State {
        EDIT, VIEW, NEW
    }
}
