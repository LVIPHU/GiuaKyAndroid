package com.example.giuakyandroid.custom_textview;

import android.content.Context;
import android.graphics.Typeface;

public class Utils {
    private static Typeface AlexBrush;

    public static Typeface getAlexBrushTypeface(Context context) {
        if(AlexBrush == null){
            AlexBrush = Typeface.createFromAsset(context.getAssets(),"fonts/AlexBrush-Regular.ttf");
        }
        return AlexBrush;
    }

}
