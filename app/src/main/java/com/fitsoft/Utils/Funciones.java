package com.fitsoft.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;

import java.util.List;

public class Funciones {

    public static AlertDialog.Builder makeAlertPositive(Context context, String Titulo, String Mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (Titulo != null)
            builder.setTitle(Titulo);
        if (Mensaje != null)
            builder.setMessage(Mensaje);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder;
    }

    public static AlertDialog.Builder calendarAlert(Context context, View alertLayout) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(alertLayout)
                .setCancelable(true);

        return builder;
    }
}
