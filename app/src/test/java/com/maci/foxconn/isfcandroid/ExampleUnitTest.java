package com.maci.foxconn.isfcandroid;

import org.junit.Test;

import java.text.DecimalFormat;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void doubleformat(){
        String d = new DecimalFormat(".00").format(5.366546d );
        String d1 =  new DecimalFormat(".00").format(Double.parseDouble("100") + (Math.random() * 1000));
    }
}