package tam.aulasandroid.trabalhopratico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
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

        new ThreadVerificaTime().start();

    }


    public void planoAlimentarView(View v){

        Intent i = new Intent(this, PlanoAlimentar.class);
        i.putExtra("listaRefeicao", listaRefeicao);
        startActivityForResult(i,1);

    }

    public void mudarRefeicao(View v){

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
                    Log.e(TAG,String.valueOf(numRefeicao));
                    Log.e(TAG,listaRefeicao.toString());
                    Collections.sort(listaRefeicao);
                    nomeRefeicoa.setText(listaRefeicao.get(numRefeicao).getRefeicao());
                    horaRefeicao.setText(formatter.format(listaRefeicao.get(numRefeicao).getHora()) );
                    Log.e(TAG,listaRefeicao.get(numRefeicao).toString() );
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

//                    Log.e(TAG+ " 1 : ", formatter.format(listaRefeicao.get(numRefeicao).getHora()));
//                    Log.e(TAG+ " 2 : ", formatter.format(dateAtual.getTime()));
//                    Log.e(TAG+ " 3 : ", String.valueOf(dateAtual.getTime() - listaRefeicao.get(numRefeicao).getHora().getTime() <= 15*60*1000) );
//                    Log.e(TAG+ " 4 : ", String.valueOf(listaRefeicao.get(numRefeicao).getHora().getTime() - dateAtual.getTime() ));
//                    Log.e(TAG+ " 5 : ", String.valueOf(15*60*1000) );
//                    Log.e(TAG, "Thread: " + numRefeicao);
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