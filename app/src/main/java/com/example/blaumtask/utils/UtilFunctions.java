package com.example.blaumtask.utils;

import android.util.Log;

import java.util.Random;

public class UtilFunctions {
    public static long createRandomInteger(int aStart, long aEnd, Random aRandom){
        if ( aStart > aEnd ) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = aEnd - (long)aStart + 1;
        Log.d("Range" ," range>>>>>>>>>>>"+range);
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * aRandom.nextDouble());
        Log.d("Franction","fraction>>>>>>>>>>>>>>>>>>>>"+fraction);
        long randomNumber =  fraction + (long)aStart;
        Log.d("Generated : "," " + randomNumber);

        return randomNumber;
    }
}
