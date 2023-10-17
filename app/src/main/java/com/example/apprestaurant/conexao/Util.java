package com.example.apprestaurant.conexao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Util {
    /**
     * método de conversão de um objeto byte[] (array de bytes) em Bitmap
     * @param foto
     * @return
     */
    public static Bitmap converterByteToBipmap(byte[] foto) {
        Bitmap bmp = null;
        Bitmap bitmapReduzido = null;
        byte[] x = foto;

        try {
            bmp = BitmapFactory.decodeByteArray(x, 0, x.length);

            bitmapReduzido = Bitmap.createScaledBitmap(bmp, 1080, 1000, true);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmapReduzido;
    }

    // https://www.it-swarm.dev/pt/android/compartilhar-imagem-e-texto-atraves-do-whatsapp-ou-facebook/1046274258/

    public static @Nullable
    Uri getUriFromFile(Context context, @Nullable File file) {
        if (file == null)
            return null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                return FileProvider.getUriForFile(context, "com.my.package.fileprovider", file);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return Uri.fromFile(file);
        }
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public static Uri getLocalBitmapUri(Context context, ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();

            bmpUri = getUriFromFile(context, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public static Uri getBytesUri(Context context, byte[] foto) {
        Bitmap bmp = null;
        Bitmap bitmapReduzido = null;
        byte[] x = foto;

        try {
            bmp = BitmapFactory.decodeByteArray(x, 0, x.length);

            bitmapReduzido = Bitmap.createScaledBitmap(bmp, 1080, 1000, true);


        } catch (Exception e) {
            e.printStackTrace();
        }

        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bitmapReduzido.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();

            bmpUri = getUriFromFile(context, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public static void temInternetWiFi(Context context){
        ConnectivityManager cm =
                (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected==true){
            boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            if(isWiFi == false){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Wi-Fi Indisponível!");
                alertDialogBuilder
                        .setMessage("Este aplicativo necessita de conexão com a rede local!")
                        .setCancelable(true);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Conexão Indisponível!");
            alertDialogBuilder
                    .setMessage("Este aplicativo necessita de conexão com a rede local!")
                    .setCancelable(true);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

}
