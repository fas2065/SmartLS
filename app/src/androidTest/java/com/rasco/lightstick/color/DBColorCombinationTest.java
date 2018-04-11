package com.rasco.lightstick.color;

import android.support.test.runner.AndroidJUnit4;

import com.rasco.lightstick.base.Color;
import com.rasco.lightstick.db.ColorCombinationDB;
import com.rasco.lightstick.base.ColorCombination;
import com.rasco.lightstick.db.DBHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DBColorCombinationTest {

    private ColorCombinationDB database;

    @Before
    public void setUp() throws Exception {
        getTargetContext().deleteDatabase(DBHelper.DATABASE_NAME);
        database = new ColorCombinationDB(getTargetContext());
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void shouldAddUpdateDeleteColorCombinations() throws Exception {
        database.addColorCombination(createColorCombs("Esteghlal"));

        List<ColorCombination> colorCombinationList = database.getAllColorCombinationLite();
        assertThat(colorCombinationList.size(), is(1));
        ColorCombination colorCombination = colorCombinationList.get(0);
        assertTrue(colorCombination.getName().equals("Esteghlal"));

//        colorCombination = database.getColorCombinationByName("Esteghlal");

        colorCombination.setName("Colors2");

        database.updateColorCombination(colorCombination);
        colorCombinationList = database.getAllColorCombinationLite();
        assertThat(colorCombinationList.size(), is(1));
        colorCombination = colorCombinationList.get(0);
        assertTrue(colorCombination.getName().equals("Colors2"));


        database.deleteColorCombination(colorCombination);
        colorCombinationList = database.getAllColorCombinationLite();
        assertThat(colorCombinationList.size(), is(0));


    }

    @Test
    public void shouldAddUpdateDeleteColors() throws Exception {
        database.addColorCombination(createColorCombs("Esteghlal"));
        database.addColorCombination(createColorCombs("Perspolis"));

        List<ColorCombination> colorCombinationList = database.getAllColorCombinationLite();
        assertThat(colorCombinationList.size(), is(2));
        ColorCombination colorCombination = colorCombinationList.get(0);
        database.addColor(createColor(colorCombination, "#009688"));
        database.addColor(createColor(colorCombination, "#ff9688"));

        ArrayList<Color> colorList = database.getAllCcColors(colorCombination.get_id());
        assertTrue(colorList.size() == 2);
        assertTrue(colorList.get(0).getColor_rbg().equals("#009688"));
        assertTrue(colorList.get(1).getColor_rbg().equals("#ff9688"));

        Color c = colorList.get(1);
        c.setColor_rbg("#ff9699");
        database.updateColor(c);

        colorList = database.getAllCcColors(colorCombination.get_id());
        assertTrue(colorList.size() == 2);
        assertTrue(colorList.get(0).getColor_rbg().equals("#009688"));
        assertTrue(colorList.get(1).getColor_rbg().equals("#ff9699"));

        database.deleteColorCombination(colorCombination);
        colorCombinationList = database.getAllColorCombinationLite();
        assertThat(colorCombinationList.size(), is(1));

        colorList = database.getAllCcColors(colorCombination.get_id());

        assertTrue(colorList.size() == 0);


    }


    private ColorCombination createColorCombs(String name) {
        ColorCombination colorCombination = new ColorCombination();
        colorCombination.setName(name);

        return colorCombination;
    }

    private Color createColor(ColorCombination cc, String color_rgb) {
        Color color1 = new Color();
        color1.setCc_id(cc.get_id());
        color1.setColor_rbg(color_rgb);
        return color1;
    }
}
