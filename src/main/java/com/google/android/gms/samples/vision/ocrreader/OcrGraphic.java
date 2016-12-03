/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.samples.vision.ocrreader;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.samples.vision.ocrreader.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;

import java.util.List;

/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
public class OcrGraphic extends GraphicOverlay.Graphic {

    private int mId;

    private static final int TEXT_COLOR = Color.WHITE;

    private static Paint sRectPaint;
    private static Paint sTextPaint;
    private final TextBlock mText;
    Bitmap saturatedFat_welchs;
    Bitmap sodium_welchs;
    Bitmap calories_welchs;
    Bitmap sugars_welchs;
    Bitmap saturatedFat_luna;
    Bitmap sodium_luna;
    Bitmap calories_luna;
    Bitmap sugars_luna;
    Bitmap saturatedFat_nv;
    Bitmap sodium_nv;
    Bitmap calories_nv;
    Bitmap sugars_nv;

    OcrGraphic(GraphicOverlay overlay, TextBlock text) {
        super(overlay);

        mText = text;
        saturatedFat_welchs = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(overlay.getResources(),R.drawable.saturatedfat_welchs), 672, 198, true);
        sodium_welchs = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(overlay.getResources(),R.drawable.sodium_welchs), 672, 198, true);
        calories_welchs = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(overlay.getResources(),R.drawable.calories_welchs), 672, 198, true);
        sugars_welchs = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(overlay.getResources(),R.drawable.sugars_welchs), 672, 198, true);
        saturatedFat_luna = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(overlay.getResources(),R.drawable.saturatedfat_luna), 672, 198, true);
        sodium_luna = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(overlay.getResources(),R.drawable.sodium_luna), 672, 198, true);
        calories_luna = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(overlay.getResources(),R.drawable.calories_luna), 672, 198, true);
        sugars_luna = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(overlay.getResources(),R.drawable.sugars_luna), 672, 198, true);
        saturatedFat_nv = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(overlay.getResources(),R.drawable.saturatedfat_nv), 672, 198, true);
        sodium_nv = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(overlay.getResources(),R.drawable.sodium_nv), 672, 198, true);
        calories_nv = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(overlay.getResources(),R.drawable.calories_nv), 672, 198, true);
        sugars_nv = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(overlay.getResources(),R.drawable.sugars_nv), 672, 198, true);


        if (sRectPaint == null) {
            sRectPaint = new Paint();
            sRectPaint.setColor(TEXT_COLOR);
            sRectPaint.setStyle(Paint.Style.STROKE);
            sRectPaint.setStrokeWidth(4.0f);
        }

        if (sTextPaint == null) {
            sTextPaint = new Paint();
            sTextPaint.setColor(TEXT_COLOR);
            sTextPaint.setTextSize(54.0f);
        }
        // Redraw the overlay, as this graphic has been added.
        postInvalidate();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public TextBlock getTextBlock() {
        return mText;
    }

    /**
     * Checks whether a point is within the bounding box of this graphic.
     * The provided point should be relative to this graphic's containing overlay.
     * @param x An x parameter in the relative context of the canvas.
     * @param y A y parameter in the relative context of the canvas.
     * @return True if the provided point is contained within this graphic's bounding box.
     */
    public boolean contains(float x, float y) {
        TextBlock text = mText;
        if (text == null) {
            return false;
        }
        RectF rect = new RectF(text.getBoundingBox());
        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);
        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom) + 600;
        return (rect.left < x && rect.right > x && rect.top < y && rect.bottom > y);
    }

    /**
     * Draws the text block annotations for position, size, and raw value on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        TextDatabase database = new TextDatabase();
        TextBlock text = mText;
        if (text == null) {
            return;
        }
        sTextPaint.setTextSize(70);
        sTextPaint.setAntiAlias(true);
        sTextPaint.setFilterBitmap(true);
        sTextPaint.setDither(true);

        // Break the text into multiple lines and draw each one according to its own bounding box.
        List<? extends Text> textComponents = text.getComponents();
        for(Text currentText : textComponents) {
            float left = translateX(currentText.getBoundingBox().left);
            float bottom = translateY(currentText.getBoundingBox().bottom);
            if (database.display(currentText.getValue()) != null) {
                if (currentText.getValue().equals("FAMILY FARMER OWNED") || currentText.getValue().contains("OWNED") || currentText.getValue().equals("Welch's")){
                    canvas.drawText(database.display(currentText.getValue()), left, bottom, sTextPaint);
                    canvas.drawBitmap(saturatedFat_welchs, left, bottom, sTextPaint);
                    canvas.drawBitmap(sodium_welchs, left, bottom + 200, sTextPaint);
                    canvas.drawBitmap(calories_welchs, left, bottom + 400, sTextPaint);
                    canvas.drawBitmap(sugars_welchs, left, bottom + 600, sTextPaint);
                }
                if (currentText.getValue().equals("LUNA")) {
                    canvas.drawText(database.display(currentText.getValue()), left, bottom, sTextPaint);
                    canvas.drawBitmap(saturatedFat_luna, left, bottom, sTextPaint);
                    canvas.drawBitmap(sodium_luna, left, bottom + 200, sTextPaint);
                    canvas.drawBitmap(calories_luna, left, bottom + 400, sTextPaint);
                    canvas.drawBitmap(sugars_luna, left, bottom + 600, sTextPaint);
                }
                if (currentText.getValue().equals("CRUNCHY")) {
                    canvas.drawText(database.display(currentText.getValue()), left, bottom, sTextPaint);
                    canvas.drawBitmap(saturatedFat_nv, left, bottom, sTextPaint);
                    canvas.drawBitmap(sodium_nv, left, bottom + 200, sTextPaint);
                    canvas.drawBitmap(calories_nv, left, bottom + 400, sTextPaint);
                    canvas.drawBitmap(sugars_nv, left, bottom + 600, sTextPaint);
                }
            }
        }
    }
}

class TextDatabase {
    public static String display(String text) {
        if (text.equals("FAMILY FARMER OWNED")) {
            return "Welch's Fruit Snacks";
        }
        if (text.contains("OWNED")) {
            return "Welch's Fruit Snacks";
        }
        if (text.equals("Welch's")) {
            return "Welch's Fruit Snacks";
        }
        if (text.equals("Jeremy Ho")) {
            return "Wewlad";
        }
        if (text.contains("LUNA")) {
            return "Luna: Whole Nutrition Bar";
        }
        if (text.contains("CRUNCHY")) {
            return "Nature Valley Crunchy Bar";
        }
        return text;
    }
    public static boolean contains(String text) {
        if (text.equals("FAMILY FARMER OWNED")) {
            return true;
        }
        if (text.contains("OWNED")) {
            return true;
        }
        if (text.equals("Jeremy Ho")) {
            return true;
        }
        if (text.contains("LUNA")) {
            return true;
        }
        if (text.contains("CRUNCHY")) {
            return true;
        }
        return false;
    }
}
