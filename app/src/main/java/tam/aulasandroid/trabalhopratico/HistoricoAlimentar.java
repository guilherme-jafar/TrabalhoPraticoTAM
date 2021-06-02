package tam.aulasandroid.trabalhopratico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_alimentar);
        Format formatter2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        dataHistorico = findViewById(R.id.dataHistorico);
        dataHistorico.setText(formatter2.format(Calendar.getInstance().getTime()));


        formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Calendar cal = Calendar.getInstance();




        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 45);




        RegistroAlimentar h1 = new RegistroAlimentar(UUID.randomUUID().toString(),"1",true, cal.getTime(), "sdjkdfbvdfbvjkbjkdfbvbfdvbkjbfdbvbdkfvbkbvkjbfmfsdmf");

        listaHistorico = new ArrayList<RegistroAlimentar>();
        listaHistorico.add(h1);
        adapter = new HistoricoAdapter(this, listaHistorico);
        historioListView = (ListView) findViewById(R.id.lista_historico);
        historioListView.setAdapter(adapter);

        historioListView.setTextFilterEnabled(true);

        historioListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(HistoricoAlimentar.this, InformacaoHistoricoRefeicao.class);
                intent.putExtra("informacaoRefeicaoHistorico", listaHistorico.get(position));
                startActivity(intent);


            }
        });
    }
}