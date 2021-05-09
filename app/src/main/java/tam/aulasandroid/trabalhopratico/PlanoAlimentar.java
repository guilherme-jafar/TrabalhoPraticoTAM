package tam.aulasandroid.trabalhopratico;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class PlanoAlimentar extends AppCompatActivity {
    private ArrayList<Refeicao> listaRefeicao = new ArrayList<>();
    ListView refeicaoListView;
    ListAdapter adapter = new RefeicaoAdapater(this, listaRefeicao);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plano_alimentar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PlanoAlimentar.this, NovaRefeicao.class);
                startActivityForResult(i, 1);
            }
        });


//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.HOUR_OF_DAY, 12);
//        cal.set(Calendar.MINUTE, 45);
//        listaRefeicao.add(new Refeicao( cal.getTime(), "Almoço", "xdfd fghfg sdgbdb sbfbuiewb uwebfuwbrf fubwhferuhf gbuihuiheshf bfbrbeuygbieuwf"));
//        cal.set(Calendar.HOUR_OF_DAY, 16);
//        cal.set(Calendar.MINUTE, 45);
//
//        listaRefeicao.add(new Refeicao(cal.getTime(), "Lanche", "xdfd"));
//
//        cal.set(Calendar.HOUR_OF_DAY, 20);
//        cal.set(Calendar.MINUTE, 45);
//
//
//        listaRefeicao.add(new Refeicao(cal.getTime(), "Jantar", "xdfd"));
//
//
//        cal.set(Calendar.HOUR_OF_DAY, 9);
//        cal.set(Calendar.MINUTE, 45);
//        listaRefeicao.add(new Refeicao(cal.getTime(), "Lanche", "xdfd"));
//
//        cal.set(Calendar.HOUR_OF_DAY, 13);
//        cal.set(Calendar.MINUTE, 45);
//        listaRefeicao.add(new Refeicao(cal.getTime(), "Almoço", "xdfd"));

        Collections.sort(listaRefeicao);


        refeicaoListView = (ListView) findViewById(R.id.lista_refeicoes);
        refeicaoListView.setAdapter(adapter);

        refeicaoListView.setTextFilterEnabled(true);

        refeicaoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(PlanoAlimentar.this, InformacaoRefeicao.class);
                intent.putExtra("informacaoRefeicao", listaRefeicao.get(position));
                startActivity(intent);


            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Refeicao refeicao = (Refeicao) data.getSerializableExtra("novaRefeicao");
                listaRefeicao.add(refeicao);
                Collections.sort(listaRefeicao);
                RefeicaoAdapater adapater = (RefeicaoAdapater) refeicaoListView.getAdapter();
                adapater.notifyDataSetChanged();
            }
        }
    }
}