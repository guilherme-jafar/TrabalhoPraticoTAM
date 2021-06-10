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

import java.text.Format;
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
    private ArrayList<Refeicao> listaRefeicaoMain = new ArrayList<>();
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
        getRefeicaoPorFazer();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cal.set(Calendar.HOUR_OF_DAY, 18);
        cal.set(Calendar.MINUTE, 45);
//        listaRefeicao.add(new Refeicao(UUID.randomUUID().toString(),cal.getTime(), "Almoço", "xdfd"));
//
        nomeRefeicoa = findViewById(R.id.nomeRefeicao);
        horaRefeicao = findViewById(R.id.horaRefeicao);


        if (listaRefeicaoMain.size() >= 1) {
            Collections.sort(listaRefeicaoMain);
            nomeRefeicoa.setText(listaRefeicaoMain.get(0).getRefeicao());
            horaRefeicao.setText(formatter.format(listaRefeicaoMain.get(0).getHora()));
            Log.e("Passou aqui MAIN - ", listaRefeicaoMain.get(0).toString());

            new ThreadVerificaTime().start();
        } else if (listaRefeicaoMain.size() == 0 && listaRefeicao.size() == 0){
            nomeRefeicoa.setText("Não Tem Refeição");
            horaRefeicao.setText("");
        }else if (listaRefeicaoMain.size() == 0){
            nomeRefeicoa.setText("Já realizou todas as refeições");
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
        listaRefeicao.clear();
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
              //  Log.e(TAG, refeicao.toString());
                listaRefeicao.add(refeicao);



                curRes.moveToNext();
            }
            Log.e(TAG, listaRefeicao.toString());

        }




    }

    public void getRefeicaoPorFazer() {
        listaRefeicaoMain.clear();
        RegistroAlimentar registroAlimentar;
        String dataAtual;
        ArrayList<RegistroAlimentar> listaHistorico = new ArrayList<>();
        Format formatter2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dataAtual = formatter2.format(Calendar.getInstance().getTime());
        Uri uriAll = Uri.parse("content://tam.aulasandroid.trabalhopratico.refeicao/historico");

        String selection = "refeicaoMain";

        String[] selectionArgs = new String[]{dataAtual};
        Log.e(TAG, dataAtual);
        Cursor curRes = managedQuery(uriAll, null, selection, selectionArgs, null);

        if (curRes != null) {
            if (curRes.getCount() == 0) {
                Log.e(TAG, "Vazio");
            }else{
                curRes.moveToFirst();
                while (!curRes.isAfterLast()) {
                    Log.e(TAG, curRes.getString(0) + "  -  " + curRes.getString(1) + " - " + curRes.getString(2) + "  -  " + curRes.getString(3) + "\n");
//                sb.append(curRes.getString(1) + "  -  " + curRes.getString(2) + "\n" );

                    Date date = null;
                    Log.e(TAG, curRes.getString(1)  +" "+ dataAtual);
                    try {

                        date = new SimpleDateFormat("HH:mm dd/MM/yyyy").parse(curRes.getString(1) +" "+ dataAtual);
                    } catch (java.text.ParseException e) {
                        Log.e(TAG, e.toString());
                    }
                    Refeicao refeicao = new Refeicao(curRes.getString(0), date, curRes.getString(2), curRes.getString(3));
                    //  Log.e(TAG, refeicao.toString());
                    listaRefeicaoMain.add(refeicao);
                    //Log.e(TAG, listaRefeicao.toString());


                    curRes.moveToNext();
                }
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
            Toast.makeText(getApplicationContext(), "Nenhuma refeição está agendada", Toast.LENGTH_LONG).show();
        } else {

            Intent i = new Intent(this, registoRefeicao.class);
            //System.out.println(listaRefeicao.get(numRefeicao));
            i.putExtra("Refeicao", listaRefeicaoMain.get(0));
            startActivityForResult(i, 2);
        }


    }

    public void settings(View v) {
        Intent i = new Intent(this, SettingsActivity.class);
        i.putExtra("listaRefeicao", listaRefeicao);
        startActivityForResult(i, 3);
    }

    public void change() {

        getAllRefeicao();
        getRefeicaoPorFazer();

        if (listaRefeicaoMain.size() >= 1) {
            Collections.sort(listaRefeicaoMain);
            nomeRefeicoa.setText(listaRefeicaoMain.get(0).getRefeicao());
            horaRefeicao.setText(formatter.format(listaRefeicaoMain.get(0).getHora()));
            Log.e("Passou aqui Back - ", listaRefeicaoMain.get(0).toString());

        } else if (listaRefeicaoMain.size() == 0 && listaRefeicao.size() == 0){
            nomeRefeicoa.setText("Não Tem Refeição");
            horaRefeicao.setText("");
        }else if (listaRefeicaoMain.size() == 0){
            nomeRefeicoa.setText("Já realizou todas as refeições");
            horaRefeicao.setText("");
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");

        // saves a course into the bundle as a Serializable
//        outState.putInt("numRefeicao", numRefeicao);
    }

    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);

//        numRefeicao = outState.getInt("numRefeicao");
//        Log.e(TAG, "onRestoreInstanceState: " + numRefeicao);
//        nomeRefeicoa.setText(listaRefeicao.get(numRefeicao).getRefeicao());
//        horaRefeicao.setText(formatter.format(listaRefeicao.get(numRefeicao).getHora()));
        // retrieves the course from the bundle


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                if (data != null) {

//                    listaRefeicao = (ArrayList<Refeicao>) data.getExtras().getSerializable("listaRefeicaoBack");
//                    getRefeicaoPorFazer();
//                    Collections.sort(listaRefeicaoMain);
//                    nomeRefeicoa.setText(listaRefeicaoMain.get(0).getRefeicao());
//                    horaRefeicao.setText(formatter.format(listaRefeicaoMain.get(0).getHora()));

                    getAllRefeicao();
                    getRefeicaoPorFazer();

                    if (listaRefeicaoMain.size() >= 1) {
                        Collections.sort(listaRefeicaoMain);
                        nomeRefeicoa.setText(listaRefeicaoMain.get(0).getRefeicao());
                        horaRefeicao.setText(formatter.format(listaRefeicaoMain.get(0).getHora()));
                        Log.e("Passou aqui Back - ", listaRefeicaoMain.get(0).toString());

                    } else if (listaRefeicaoMain.size() == 0 && listaRefeicao.size() == 0){
                        nomeRefeicoa.setText("Não Tem Refeição");
                        horaRefeicao.setText("");
                    }else if (listaRefeicaoMain.size() == 0){
                        nomeRefeicoa.setText("Já realizou todas as refeições");
                        horaRefeicao.setText("");
                    }
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
//                    Log.e(TAG,  String.valueOf(buffer));
//                    Log.e(TAG,  String.valueOf(listaRefeicaoMain.get(0).getHora().getTime() - dateAtual.getTime() >= buffer * 60 * 1000) );
//                    Log.e(TAG, String.valueOf(listaRefeicaoMain.get(0).getHora().getTime() - dateAtual.getTime() <= buffer * 60 * 1000));
//                    Log.e(TAG, String.valueOf(listaRefeicaoMain.get(0).getHora().compareTo(dateAtual)));
//                    Log.e(TAG, String.valueOf(buffer * 60 * 1000));
//                    Log.e(TAG, String.valueOf(listaRefeicaoMain.get(0).getHora().getTime()));
//                    Log.e(TAG, String.valueOf(listaRefeicaoMain.get(0).getHora()));
                    if (listaRefeicaoMain.size() != 0){
                        if (listaRefeicaoMain.get(0).getHora().getTime() - dateAtual.getTime() >= buffer * 60 * 1000) {
//                            Log.e(TAG,  "1");

                            backGroundAlimentacao.post(new Runnable() {

                                @Override
                                public void run() {
                                    backGroundAlimentacao.setBackgroundTintList(MainActivity.this.getResources().getColorStateList(R.color.green));

                                }

                            });
                        } else if (listaRefeicaoMain.get(0).getHora().compareTo(dateAtual) < 0) {
//                            Log.e(TAG,  "2");

                            backGroundAlimentacao.post(new Runnable() {

                                @Override
                                public void run() {
                                    backGroundAlimentacao.setBackgroundTintList(MainActivity.this.getResources().getColorStateList(R.color.red));

                                }

                            });
                        } else if (dateAtual.getTime() - listaRefeicaoMain.get(0).getHora().getTime() <= buffer * 60 * 1000) {
//                            Log.e(TAG,  "3");
                            backGroundAlimentacao.post(new Runnable() {

                                @Override
                                public void run() {
                                    backGroundAlimentacao.setBackgroundTintList(MainActivity.this.getResources().getColorStateList(R.color.yelow));

                                }

                            });
                        }
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