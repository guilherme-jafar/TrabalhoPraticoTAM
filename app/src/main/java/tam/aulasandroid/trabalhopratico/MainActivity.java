package tam.aulasandroid.trabalhopratico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

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
    //private contentProvider contentProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cal = Calendar.getInstance();
        hora = findViewById(R.id.textClockTime);




        formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        backGroundAlimentacao = findViewById(R.id.backGroundAlimentacao);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 45);
        listaRefeicao.add(new Refeicao(UUID.randomUUID().toString(), cal.getTime(), "Almoço", "xdfd fghfg sdgbdb sbfbuiewb uwebfuwbrf fubwhferuhf gbuihuiheshf bfbrbeuygbieuwf"));
        cal.set(Calendar.HOUR_OF_DAY, 16);
        cal.set(Calendar.MINUTE, 45);

        listaRefeicao.add(new Refeicao(UUID.randomUUID().toString(),cal.getTime(), "Lanche", "xdfd"));

        cal.set(Calendar.HOUR_OF_DAY, 15);
        cal.set(Calendar.MINUTE, 1);


        listaRefeicao.add(new Refeicao(UUID.randomUUID().toString(),cal.getTime(), "Jantar", "xdfd"));


        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 45);
        listaRefeicao.add(new Refeicao(UUID.randomUUID().toString(),cal.getTime(), "Lanche", "xdfd"));

        cal.set(Calendar.HOUR_OF_DAY, 18);
        cal.set(Calendar.MINUTE, 45);
        listaRefeicao.add(new Refeicao(UUID.randomUUID().toString(),cal.getTime(), "Almoço", "xdfd"));
        Collections.sort(listaRefeicao);

        nomeRefeicoa = findViewById(R.id.nomeRefeicao);
        horaRefeicao = findViewById(R.id.horaRefeicao);



        nomeRefeicoa.setText(listaRefeicao.get(numRefeicao).getRefeicao());
        horaRefeicao.setText(formatter.format(listaRefeicao.get(numRefeicao).getHora()) );


        RefeicaoDBAdapter  refeicaoDBAdapter= new RefeicaoDBAdapter(this);

        refeicaoDBAdapter.open();


        Log.e(TAG,refeicaoDBAdapter.getAllRefeicoes().toString());

        Cursor cursor = refeicaoDBAdapter.getAllRefeicoes();


        if(cursor!=null){
            if(cursor.getCount()==0){
                Log.e(TAG,"The table is empty");

                return;
            }
            StringBuilder sb = new StringBuilder();

            cursor.moveToFirst();
            while(!cursor.isAfterLast()){

                Log.e(TAG,"Not empry");
                sb.append(cursor.getString(1) + "  -  " + cursor.getString(2) + "\n" );
                Log.e(TAG,sb.toString());
                cursor.moveToNext();
            }


        }

        refeicaoDBAdapter.close();

        new ThreadVerificaTime().start();

    }


    public void planoAlimentarView(View v){

        Intent i = new Intent(this, PlanoAlimentar.class);
        i.putExtra("listaRefeicao", listaRefeicao);
        startActivityForResult(i,1);

    }

    public void historicoAlimentarView(View v){

        Intent i = new Intent(this, HistoricoAlimentar.class);

        startActivity(i);

    }

    public void mudarRefeicao(View v){


        Intent i = new Intent(this, registoRefeicao.class);
        //System.out.println(listaRefeicao.get(numRefeicao));
        i.putExtra("Refeicao", listaRefeicao.get(numRefeicao));
        startActivityForResult(i,2);



    }

    public void change(){

        numRefeicao++;
        if (numRefeicao >= listaRefeicao.size()){
            numRefeicao = 0;
        }
        nomeRefeicoa.setText(listaRefeicao.get(numRefeicao).getRefeicao());
       horaRefeicao.setText(formatter.format(listaRefeicao.get(numRefeicao).getHora()) );
    }
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");

        // saves a course into the bundle as a Serializable
        outState.putInt("numRefeicao", numRefeicao);
    }

    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);

        numRefeicao =  outState.getInt("numRefeicao");
        Log.e(TAG, "onRestoreInstanceState: " + numRefeicao);
        nomeRefeicoa.setText(listaRefeicao.get(numRefeicao).getRefeicao());
        horaRefeicao.setText(formatter.format(listaRefeicao.get(numRefeicao).getHora()) );
        // retrieves the course from the bundle

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                if (data != null){

                    listaRefeicao = (ArrayList<Refeicao>) data.getExtras().getSerializable("listaRefeicaoBack");
                    Collections.sort(listaRefeicao);
                    nomeRefeicoa.setText(listaRefeicao.get(numRefeicao).getRefeicao());
                    horaRefeicao.setText(formatter.format(listaRefeicao.get(numRefeicao).getHora()) );
                }


            }
        }
        if(requestCode==2){

            if (data != null) {
                if (data.getStringExtra("estado").equalsIgnoreCase("salvar")) {
                    change();
                }
            }


        }


    }


    class ThreadVerificaTime extends Thread{
        int counter;

        //ThreadVerificaTime(int _counter){ counter = _counter;}

        public void run(){
            countThreads++;
            while (true){
                try {


                    Date dateAtual = new Date();

                    if (listaRefeicao.get(numRefeicao).getHora().getTime() - dateAtual.getTime()   >= 15*60*1000){


                        backGroundAlimentacao.post(new Runnable(){

                            @Override
                            public void run() {
                                backGroundAlimentacao.setBackgroundTintList(MainActivity.this.getResources().getColorStateList(R.color.green));

                            }

                        });
                    }else if (listaRefeicao.get(numRefeicao).getHora().compareTo(dateAtual)  < 0){


                        backGroundAlimentacao.post(new Runnable(){

                            @Override
                            public void run() {
                                backGroundAlimentacao.setBackgroundTintList(MainActivity.this.getResources().getColorStateList(R.color.red));

                            }

                        });
                    }else  if (dateAtual.getTime() - listaRefeicao.get(numRefeicao).getHora().getTime() <= 15*60*1000){

                        backGroundAlimentacao.post(new Runnable(){

                            @Override
                            public void run() {
                                backGroundAlimentacao.setBackgroundTintList(MainActivity.this.getResources().getColorStateList(R.color.yelow));

                            }

                        });
                    }

                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    Log.e(TAG, e.toString());
                }
            }
        }
    }




}