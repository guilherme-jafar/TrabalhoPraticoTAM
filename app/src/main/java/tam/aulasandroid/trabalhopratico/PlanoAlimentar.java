package tam.aulasandroid.trabalhopratico;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
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
import java.util.Iterator;

public class PlanoAlimentar extends AppCompatActivity {
    private ArrayList<Refeicao>  listaRefeicao = new ArrayList<>();
    ListView refeicaoListView;

    Bundle b;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_plano_alimentar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        b = intent.getBundleExtra("bundle");



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


        if (b!=null) {
            listaRefeicao = (ArrayList<Refeicao>) b.getSerializable("plano");
            Collections.sort(listaRefeicao);
            Intent resInt = new Intent();
            Bundle b = new Bundle();
            b.putSerializable("plano", listaRefeicao);
            resInt.putExtra("bundle", b);
            setResult(RESULT_OK, resInt);
        }
            adapter = new RefeicaoAdapater(this, listaRefeicao);
            refeicaoListView = (ListView) findViewById(R.id.lista_refeicoes);
            refeicaoListView.setAdapter(adapter);

            refeicaoListView.setTextFilterEnabled(true);

            refeicaoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(PlanoAlimentar.this, InformacaoRefeicao.class);
                    intent.putExtra("informacaoRefeicao", listaRefeicao.get(position));
                    startActivityForResult(intent, 2);


                }
            });


        }
        public void back (View v){
            Intent resInt = new Intent();
            Bundle b = new Bundle();
            b.putSerializable("plano", listaRefeicao);
            resInt.putExtra("bundle", b);
            setResult(RESULT_OK, resInt);
            finish();

        }



        public void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                    Refeicao refeicao = (Refeicao) data.getSerializableExtra("novaRefeicao");
                    listaRefeicao.add(refeicao);
                    Collections.sort(listaRefeicao);
                    RefeicaoAdapater adapater = (RefeicaoAdapater) refeicaoListView.getAdapter();
                    adapater.notifyDataSetChanged();
                    Intent resInt = new Intent();
                    Bundle b = new Bundle();
                    b.putSerializable("plano", listaRefeicao);
                    resInt.putExtra("bundle", b);
                    setResult(RESULT_OK, resInt);
                }
            }
            else if (requestCode == 2){
                if (resultCode == RESULT_OK) {
                    System.out.println("ijfidg");
                    if (data.getStringExtra("tipo").equalsIgnoreCase("remover")){
                        Refeicao refeicao = (Refeicao) data.getSerializableExtra("EliminarRefeicao");
                        System.out.println(refeicao.getRefeicao());
                        for (Iterator<Refeicao> iterator = listaRefeicao.iterator(); iterator.hasNext();) {
                            Refeicao obj= iterator.next();
                            if (obj.getId().equalsIgnoreCase(refeicao.getId())) {
                                // Remove the current element from the iterator and the list.
                                iterator.remove();
                            }
                        }

                        Collections.sort(listaRefeicao);
                        System.out.println(listaRefeicao);
                        RefeicaoAdapater adapater = (RefeicaoAdapater) refeicaoListView.getAdapter();
                        adapater.notifyDataSetChanged();
                        Intent resInt = new Intent();
                        Bundle b = new Bundle();
                        b.putSerializable("plano", listaRefeicao);
                        resInt.putExtra("bundle", b);
                        setResult(RESULT_OK, resInt);
                    }
                }
            }
        }
    }
