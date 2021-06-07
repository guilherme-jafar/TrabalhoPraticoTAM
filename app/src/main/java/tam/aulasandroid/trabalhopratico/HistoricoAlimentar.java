package tam.aulasandroid.trabalhopratico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class HistoricoAlimentar extends AppCompatActivity {

    private ArrayList<RegistroAlimentar> listaHistorico = new ArrayList<>();
    ListView historioListView;
    private SimpleDateFormat formatter;
    Bundle b;
    ListAdapter adapter;
    String TAG = "Historio Alimentar";
    private TextView dataHistorico;
    RegistroAlimentar registroAlimentar;
    private String dataAtual;
    Format formatter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_alimentar);
        formatter2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        listaHistorico = new ArrayList<RegistroAlimentar>();
        dataHistorico = findViewById(R.id.dataHistorico);
        dataAtual = formatter2.format(Calendar.getInstance().getTime());



        formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Calendar cal = Calendar.getInstance();


        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 45);


        getAllhistorico();

//        RegistroAlimentar h1 = new RegistroAlimentar(UUID.randomUUID().toString(), "1", true, cal.getTime(), "sdjkdfbvdfbvjkbjkdfbvbfdvbkjbfdbvbdkfvbkbvkjbfmfsdmf");


//        listaHistorico.add(h1);


        if (adapter != null){
            historioListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(HistoricoAlimentar.this, InformacaoHistoricoRefeicao.class);
                    intent.putExtra("informacaoRefeicaoHistorico", listaHistorico.get(position));
                    startActivity(intent);


                }
            });
        }


    }

    public void getNextDay(View v) {


        Date dia = null;

        try {

            dia = new SimpleDateFormat("dd/MM/yyyy").parse(dataAtual);
            Calendar c = Calendar.getInstance();
            c.setTime(dia);
            c.add(Calendar.DATE, 1);
            dia = c.getTime();
            dataAtual = formatter2.format(dia);
            dataHistorico.setText(dataAtual);
            getAllhistorico();
        } catch (java.text.ParseException e) {
            Log.e(TAG, e.toString());
        }

    }

    public void getPreviusDay(View v) {

        Date dia = null;

        try {

            dia = new SimpleDateFormat("dd/MM/yyyy").parse(dataAtual);
            Calendar c = Calendar.getInstance();
            c.setTime(dia);
            c.add(Calendar.DATE, -1);
            dia = c.getTime();
            dataAtual = formatter2.format(dia);
            dataHistorico.setText(dataAtual);

            getAllhistorico();

        } catch (java.text.ParseException e) {
            Log.e(TAG, e.toString());
        }

    }

    public void getAllhistorico() {

        Log.e(TAG, dataAtual);
        listaHistorico.clear();
        Log.e(TAG, listaHistorico.toString());
        Uri uriAll = Uri.parse("content://tam.aulasandroid.trabalhopratico.refeicao/historico");

        String selection = "dia=?";

        String[] selectionArgs = new String[]{dataAtual};

        Cursor curRes = managedQuery(uriAll, null, selection, selectionArgs, null);

        if (curRes != null) {
            if (curRes.getCount() == 0) {
                Log.e(TAG, "Vazio");
            }else{
                curRes.moveToFirst();
                while (!curRes.isAfterLast()) {
                    Log.e(TAG, curRes.getString(0) + "  -  " + curRes.getString(1) + " - " + curRes.getString(2) + "  -  " + curRes.getString(3) + " - " + curRes.getString(4) + " - " + curRes.getString(5) + "\n");

                    Date hora = null;
                    Date dia = null;

                    try {

                        if(curRes.getString(3)!=null) {
                            hora = new SimpleDateFormat("HH:mm").parse(curRes.getString(3));
                        }
                        dia = new SimpleDateFormat("dd/MM/yyyy").parse(curRes.getString(4));

                    } catch (java.text.ParseException e) {
                        Log.e(TAG, e.toString());
                    }
                    registroAlimentar = new RegistroAlimentar(curRes.getString(0), curRes.getString(1), (curRes.getString(2).equalsIgnoreCase("1")), hora, curRes.getString(5));
                    registroAlimentar.setData(dia);
                    getRefeicao(curRes.getString(1));

                    listaHistorico.add(registroAlimentar);
                    Log.e(TAG, listaHistorico.toString());


                    curRes.moveToNext();
                }
            }




        }

        adapter = new HistoricoAdapter(this, listaHistorico);
        historioListView = (ListView) findViewById(R.id.lista_historico);
        historioListView.setAdapter(adapter);
        historioListView.setTextFilterEnabled(true);
        dataHistorico.setText(dataAtual);

    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState");

        // saves a course into the bundle as a Serializable
        outState.putString("dataAtual", dataAtual);
    }

    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        Log.e(TAG, "onRestoreInstanceState");
        dataAtual = outState.getString("dataAtual");

        getAllhistorico();

        // retrieves the course from the bundle

    }

    public void getRefeicao(String idRefeicao) {
        Uri uriAll = Uri.parse("content://tam.aulasandroid.trabalhopratico.refeicao/refeicao");
        String[] projection = new String[]{"hora", "refeicao"};
        String selection = "id=?";
        String[] selectionArgs = new String[]{idRefeicao};

        Cursor curRes = managedQuery(uriAll, projection, selection, selectionArgs, null);

        Log.e(TAG, idRefeicao);
        Log.e(TAG, String.valueOf(curRes.getCount()));

        if (curRes.getCount() >= 1) {
            curRes.moveToFirst();
            Log.e(TAG, curRes.getString(0));
            Log.e(TAG, curRes.getString(1));
            registroAlimentar.setHoraRefeicao(curRes.getString(0));
            registroAlimentar.setNomeRefeicao(curRes.getString(1));


        }


    }
}