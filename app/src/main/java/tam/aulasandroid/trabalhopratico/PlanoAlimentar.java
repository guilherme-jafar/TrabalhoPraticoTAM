package tam.aulasandroid.trabalhopratico;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
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
import java.util.UUID;

public class PlanoAlimentar extends AppCompatActivity {
    private ArrayList<Refeicao> listaRefeicao = new ArrayList<>();
    ListView refeicaoListView;
    Bundle b;
    ListAdapter adapter;
    String TAG = "Plano Alimentar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
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

//
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.HOUR_OF_DAY, 12);
//        cal.set(Calendar.MINUTE, 45);
//        listaRefeicao.add(new Refeicao(UUID.randomUUID().toString(), cal.getTime(), "Almoço", "xdfd fghfg sdgbdb sbfbuiewb uwebfuwbrf fubwhferuhf gbuihuiheshf bfbrbeuygbieuwf"));
//        cal.set(Calendar.HOUR_OF_DAY, 16);
//        cal.set(Calendar.MINUTE, 45);
//
//        listaRefeicao.add(new Refeicao(UUID.randomUUID().toString(),cal.getTime(), "Lanche", "xdfd"));
//
//        cal.set(Calendar.HOUR_OF_DAY, 20);
//        cal.set(Calendar.MINUTE, 45);
//
//
//        listaRefeicao.add(new Refeicao(UUID.randomUUID().toString(),cal.getTime(), "Jantar", "xdfd"));
//
//
//        cal.set(Calendar.HOUR_OF_DAY, 9);
//        cal.set(Calendar.MINUTE, 45);
//        listaRefeicao.add(new Refeicao(cal.getTime(), "Lanche", "xdfd"));
//
//        cal.set(Calendar.HOUR_OF_DAY, 13);
//        cal.set(Calendar.MINUTE, 45);
//        listaRefeicao.add(new Refeicao(cal.getTime(), "Almoço", "xdfd"));

        refeicaoListView = (ListView) findViewById(R.id.lista_refeicoes);
        if (b != null) {
            listaRefeicao = (ArrayList<Refeicao>) b.getSerializable("plano");
            Collections.sort(listaRefeicao);
            Intent resInt = new Intent();
            Bundle b = new Bundle();
            b.putSerializable("plano", listaRefeicao);
            resInt.putExtra("bundle", b);
            setResult(RESULT_OK, resInt);

        }else{
            listaRefeicao=new ArrayList<Refeicao>();
                    adapter = new RefeicaoAdapater(this, listaRefeicao);
            refeicaoListView.setAdapter(adapter);
        }




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

    public void back(View v) {
        Intent resInt = new Intent();
        Bundle b = new Bundle();
        b.putSerializable("plano", listaRefeicao);
        resInt.putExtra("bundle", b);
        setResult(RESULT_OK, resInt);
        finish();

    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");

        // saves a course into the bundle as a Serializable
        outState.putSerializable("refeicaoListView", listaRefeicao);
    }

    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        Log.d(TAG, "onRestoreInstanceState");
        listaRefeicao = (ArrayList<Refeicao>) outState.getSerializable("refeicaoListView");
        adapter = new RefeicaoAdapater(this, listaRefeicao);
        refeicaoListView = (ListView) findViewById(R.id.lista_refeicoes);
        refeicaoListView.setAdapter(adapter);

        refeicaoListView.setTextFilterEnabled(true);
        // retrieves the course from the bundle

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {

                if (data.getStringExtra("tipo").equalsIgnoreCase("remover")) {
                    Refeicao refeicao = (Refeicao) data.getSerializableExtra("EliminarRefeicao");

                    for (Iterator<Refeicao> iterator = listaRefeicao.iterator(); iterator.hasNext(); ) {
                        Refeicao obj = iterator.next();
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
                else if (data.getStringExtra("tipo").equalsIgnoreCase("alterar")){
                    Refeicao refeicao = (Refeicao) data.getSerializableExtra("AlterarRefeicao");
                    for (Iterator<Refeicao> iterator = listaRefeicao.iterator(); iterator.hasNext(); ) {
                        Refeicao obj = iterator.next();
                        if (obj.getId().equalsIgnoreCase(refeicao.getId())) {

                            // Remove the current element from the iterator and the list.
                            iterator.remove();
                        }
                    }
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
        }
    }

    public void onStart(){  // called just after onCreate (state = Started - transient state)
        super.onStart();
        Log.d(TAG, "onStart");
    }

    public void onResume(){  // called just after onStart (state = Resumed - activity visible and active )
        super.onResume();
        Log.d(TAG, "onResume");
    }

    public void onPause(){  // called when the activity is partially hidden (state = Paused)
        super.onPause();
        Log.d(TAG, "onPause");
    }

    public void onStop(){ // called when the activity is completely hidden (state = Stop)
        super.onStop();
        Log.d(TAG, "onStop");
    }

    public void onRestart(){  // called when the activity is about to return (stated = Started - transient state)
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    public void onDestroy(){ // called when the activity is destroyed (state = Destroyed)

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("listaRefeicao", listaRefeicao);
        setResult(RESULT_OK, intent);
        finish();
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


//    public void onBackPressed(){
//
//    }
}
