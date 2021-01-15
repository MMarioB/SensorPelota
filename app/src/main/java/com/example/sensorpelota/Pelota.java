package com.example.sensorpelota;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class Pelota extends View implements SensorEventListener {
    Paint pincel = new Paint();
    int alto;
    int ancho;
    int tamanio = 40;
    int borde = 12;
    float ejex = 0;
    float ejey = 0;
    float ejez1 = 0;
    float ejez = 0;
    String x;
    String y;
    String z;

    public Pelota(Context interfaz) {
        super(interfaz);
        SensorManager smAdministrador = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor snsRotacion = smAdministrador.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        smAdministrador.registerListener(this, snsRotacion, SensorManager.SENSOR_DELAY_FASTEST);
        Display pantalla = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        ancho = pantalla.getWidth();
        alto = pantalla.getHeight();

    }

    @Override
    public void onSensorChanged(SensorEvent cambio) {
        ejex = cambio.values[0];
        x = Float.toString(ejex);
        if (ejex < (tamanio + borde)) {
            ejex = (tamanio + borde);
        } else if (ejex > (ancho - (tamanio + borde))) {
            ejex = ancho - (tamanio + borde);
        }
        ejey += cambio.values[1];
        y = Float.toString(ejey);
        if (ejey < (tamanio + borde)) {
            ejey = (tamanio + borde);
        } else if (ejey > alto - tamanio - 170) {
            ejey = alto - tamanio - 170;
        }
        ejez = cambio.values[2];
        z = String.format("%.2f", ejez);
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onDraw(Canvas lienzo) {
        pincel.setColor(Color.RED);
        lienzo.drawCircle(ejex, ejey, ejez + tamanio, pincel);
        pincel.setColor(Color.WHITE);
        pincel.setTextSize(25);
        lienzo.drawText("Mario", ejex - 35, ejey + 3, pincel);
    }
}
