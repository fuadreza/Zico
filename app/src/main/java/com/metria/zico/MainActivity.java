package com.metria.zico;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

// Nama class java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    pembuatan variabel
    private TextView teksAngka;
    private TextView teksTotal;
    private ConstraintLayout tombolHitung, tombolReset, tombolGetar, tombolWalpaper, background;
    private int hitung = 0;
    private int total = 0;
    private int i = 0;
    private boolean getar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        memberi nilai pada variable total
        total = getTotal(this);

        teksAngka = findViewById(R.id.jumlah_hitung);
        teksTotal = findViewById(R.id.total_zikir);
        tombolHitung = findViewById(R.id.btn_count);
        tombolReset = findViewById(R.id.tombol_reset);
        tombolWalpaper = findViewById(R.id.tombol_wallpaper);
        tombolGetar = findViewById(R.id.tombol_getar);
        background = findViewById(R.id.background);

        tombolHitung.setOnClickListener(this);
        tombolReset.setOnClickListener(this);
        tombolWalpaper.setOnClickListener(this);
        tombolGetar.setOnClickListener(this);

//        memberi teks pada tampi;an
        teksAngka.setText(String.valueOf(hitung));
        teksTotal.setText(String.valueOf(total));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_count:
//                tombol untuk menambah jumlah hitungan
                hitung++;
                teksAngka.setText(String.valueOf(hitung));
                simpanTotal(this);

                if (getar){
                    Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    if (hitung % 33 == 0) {
                        vibe.vibrate(1000);
                    } else {
                        vibe.vibrate(100);
                    }
                }

                break;
            case R.id.tombol_reset:
//                tombol reset untuk mereset hitungan
                hitung = 0;
                teksAngka.setText(String.valueOf(hitung));
                break;
            case R.id.tombol_wallpaper:
                //mengganti wallpaper
                if (i == 0) {
                    background.setBackground(ContextCompat.getDrawable(this, R.drawable.zico2));
                } else if (i == 1) {
                    background.setBackground(ContextCompat.getDrawable(this, R.drawable.zico3));
                } else if (i == 2) {
                    background.setBackground(ContextCompat.getDrawable(this, R.drawable.zico4));
                } else if (i == 3) {
                    background.setBackground(ContextCompat.getDrawable(this, R.drawable.zico1));
                    i = 0;
                }
                i++;
                break;
            case R.id.tombol_getar:
//                mengaktifkan atau menonaktifkan getar
                if (getar == true) {
                    Toast.makeText(this, "Getar mati", Toast.LENGTH_SHORT).show();
                    getar = false;
                } else {
                    Toast.makeText(this, "Getar hidup", Toast.LENGTH_SHORT).show();
                    getar = true;
                }
                break;
        }

    }

//    fungsi menyimpan total hitungan zikir
    private void simpanTotal(Context context) {
        SharedPreferences data = context.getSharedPreferences("Zico", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        int totalnya = getTotal(context);

        totalnya++;

        teksTotal.setText(String.valueOf(totalnya));

        editor.putInt("totalZikir", totalnya);
        editor.apply();
    }

//    fungsi mengambil total hitungan zikir
    private int getTotal(Context context) {
        SharedPreferences data = context.getSharedPreferences("Zico", Context.MODE_PRIVATE);
        return data.getInt("totalZikir", 0);
    }

}