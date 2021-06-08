package tam.aulasandroid.trabalhopratico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;


import android.content.ContentValues;
import android.content.Intent;

import android.content.SharedPreferences;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Calendar cal;
    TextClock hora;
    private ArrayList<Refeicao> listaRefeicao = new ArrayList<>();
    private TextView nomeRefeicoa, horaRefeicao;
    private String TAG = "Main Activity";
    private SimpleDateFormat formatter;
    private LinearLayout backGroundAlimentacao;
    private int countThreads = 0;
    int numRefeicao = 0;
    int buffer;
    private TextView name;
    SharedPreferences pref;

    //private contentProvider contentProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        setContentView(R.layout.activity_main);
        cal = Calendar.getInstance();
        hora = findViewById(R.id.textClockTime);

        name = findViewById(R.id.textView9);
        name.setText(pref.getString("nome", "Escolha "));
        buffer = Integer.parseInt(pref.getString("replay", "15"));

        formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        backGroundAlimentacao = findViewById(R.id.backGroundAlimentacao);

        getAllRefeicao();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cal.set(Calendar.HOUR_OF_DAY, 18);
        cal.set(Calendar.MINUTE, 45);
//        listaRefeicao.add(new Refeicao(UUID.randomUUID().toString(),cal.getTime(), "Almoço", "xdfd"));
//
        nomeRefeicoa = findViewById(R.id.nomeRefeicao);
        horaRefeicao = findViewById(R.id.horaRefeicao);


        if (listaRefeicao.size() >= 1) {
            nomeRefeicoa.setText(listaRefeicao.get(numRefeicao).getRefeicao());
            horaRefeicao.setText(formatter.format(listaRefeicao.get(numRefeicao).getHora()));
            Collections.sort(listaRefeicao);
            new ThreadVerificaTime().start();
        } else {
            nomeRefeicoa.setText("Não Tem Refeição");
            horaRefeicao.setText("");
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        View v = getCurrentFocus();
        if (id == R.id.menu) {
            settings(v);
        }

        return super.onOptionsItemSelected(item);
    }

    public void getAllRefeicao() {
        Uri uriAll = Uri.parse("content://tam.aulasandroid.trabalhopratico.refeicao/refeicao");

        Cursor curRes = managedQuery(uriAll, null, null, null, null);

        if (curRes != null) {
            if (curRes.getCount() == 0) {
                Log.e(TAG, "Vazio");
                return;
            }
            StringBuilder sb = new StringBuilder();

            curRes.moveToFirst();
            while (!curRes.isAfterLast()) {
                Log.e(TAG, curRes.getString(0) + "  -  " + curRes.getString(1) + " - " + curRes.getString(2) + "  -  " + curRes.getString(3) + "\n");
//                sb.append(curRes.getString(1) + "  -  " + curRes.getString(2) + "\n" );

                Date date = null;

                try {
                    date = new SimpleDateFormat("HH:mm").parse(curRes.getString(1));
                } catch (java.text.ParseException e) {
                    Log.e(TAG, e.toString());
                }
                Refeicao refeicao = new Refeicao(curRes.getString(0), date, curRes.getString(2), curRes.getString(3));
                Log.e(TAG, refeicao.toString());
                listaRefeicao.add(refeicao);
                Log.e(TAG, listaRefeicao.toString());


                curRes.moveToNext();
            }


        }

    }


    public void planoAlimentarView(View v) {

        Intent i = new Intent(this, PlanoAlimentar.class);
        i.putExtra("listaRefeicao", listaRefeicao);
        startActivityForResult(i, 1);

    }

    public void historicoAlimentarView(View v) {

        Intent i = new Intent(this, HistoricoAlimentar.class);

        startActivity(i);

    }

    public void mudarRefeicao(View v) {

        if (horaRefeicao.getText().equals("")) {
            Toast.makeText(getApplicationContext(), "Nenhuma refeicao esta agendada", Toast.LENGTH_LONG).show();
        } else {

            Intent i = new Intent(this, registoRefeicao.class);
            //System.out.println(listaRefeicao.get(numRefeicao));
            i.putExtra("Refeicao", listaRefeicao.get(numRefeicao));
            startActivityForResult(i, 2);
        }


    }

    public void settings(View v) {
        Intent i = new Intent(this, SettingsActivity.class);
        i.putExtra("listaRefeicao", listaRefeicao);
        startActivityForResult(i, 3);
    }

    public void change() {

        if (listaRefeicao.size() < 1) {
            nomeRefeicoa.setText("Não Tem Refeição");
            horaRefeicao.setText("");
        } else {
            numRefeicao++;
            if (numRefeicao >= listaRefeicao.size()) {
                numRefeicao = 0;
            }
            nomeRefeicoa.setText(listaRefeicao.get(numRefeicao).getRefeicao());
            horaRefeicao.setText(formatter.format(listaRefeicao.get(numRefeicao).getHora()));
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");

        // saves a course into the bundle as a Serializable
        outState.putInt("numRefeicao", numRefeicao);
    }

    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);

        numRefeicao = outState.getInt("numRefeicao");
        Log.e(TAG, "onRestoreInstanceState: " + numRefeicao);
        nomeRefeicoa.setText(listaRefeicao.get(numRefeicao).getRefeicao());
        horaRefeicao.setText(formatter.format(listaRefeicao.get(numRefeicao).getHora()));
        // retrieves the course from the bundle

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                if (data != null) {

                    listaRefeicao = (ArrayList<Refeicao>) data.getExtras().getSerializable("listaRefeicaoBack");
                    Collections.sort(listaRefeicao);
                    nomeRefeicoa.setText(listaRefeicao.get(numRefeicao).getRefeicao());
                    horaRefeicao.setText(formatter.format(listaRefeicao.get(numRefeicao).getHora()));
                }


            }
        }
        if (requestCode == 2) {

            if (data != null) {
                if (data.getStringExtra("estado").equalsIgnoreCase("salvar")) {
                    change();
                }
            }


        }
        name.setText(pref.getString("nome", "Joao Silva"));
        buffer = Integer.parseInt(pref.getString("replay", "15"));


    }


    class ThreadVerificaTime extends Thread {
        int counter;

        //ThreadVerificaTime(int _counter){ counter = _counter;}

        public void run() {
            name.setText(pref.getString("nome", "Joao Silva"));

            countThreads++;
            while (true) {
                try {


                    Date dateAtual = new Date();

                    if (listaRefeicao.get(numRefeicao).getHora().getTime() - dateAtual.getTime() >= buffer * 60 * 1000) {


                        backGroundAlimentacao.post(new Runnable() {

                            @Override
                            public void run() {
                                backGroundAlimentacao.setBackgroundTintList(MainActivity.this.getResources().getColorStateList(R.color.green));

                            }

                        });
                    } else if (listaRefeicao.get(numRefeicao).getHora().compareTo(dateAtual) < 0) {


                        backGroundAlimentacao.post(new Runnable() {

                            @Override
                            public void run() {
                                backGroundAlimentacao.setBackgroundTintList(MainActivity.this.getResources().getColorStateList(R.color.red));

                            }

                        });
                    } else if (dateAtual.getTime() - listaRefeicao.get(numRefeicao).getHora().getTime() <= buffer * 60 * 1000) {

                        backGroundAlimentacao.post(new Runnable() {

                            @Override
                            public void run() {
                                backGroundAlimentacao.setBackgroundTintList(MainActivity.this.getResources().getColorStateList(R.color.yelow));

                            }

                        });
                    }
                    buffer = Integer.parseInt(pref.getString("reply", "15"));

                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    Log.e(TAG, e.toString());
                }
            }
        }
    }


}