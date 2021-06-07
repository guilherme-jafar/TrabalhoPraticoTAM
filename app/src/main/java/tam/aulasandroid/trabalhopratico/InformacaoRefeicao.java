package tam.aulasandroid.trabalhopratico;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InformacaoRefeicao extends AppCompatActivity implements DialogDelete.DialogDeleteListener{

    private Button save,cancel;
    private Refeicao refeicaoInformacao;
    private EditText infoTxt,refInfo;
    private Button timePicker;
    private ImageView btnEdit;
    private int hora, minuto;
    private Date horaEscolhida;
    String TAG = "Informacao do Plano Alimentar";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_refeicao);

        btnEdit=findViewById(R.id.imageView2);
        timePicker = findViewById(R.id.timeInput2);
        refInfo  = findViewById(R.id.refInfo);
        infoTxt  = findViewById(R.id.infoTxt);
        cancel=findViewById(R.id.cancelDetails);
        save=findViewById(R.id.saveDetails);
        save.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        timePicker.setEnabled(false);
        refInfo.setEnabled(false);
        infoTxt.setEnabled(false);


        Bundle dados = getIntent().getExtras();
        if (dados != null){
            refeicaoInformacao = (Refeicao) dados.get("informacaoRefeicao");
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
            timePicker.setText(formatter.format(refeicaoInformacao.getHora()));
            refInfo.setText(refeicaoInformacao.getRefeicao());
            infoTxt.setText(refeicaoInformacao.getInformacao());
            horaEscolhida=refeicaoInformacao.getHora();

        }
    }

    public void Edit(View View){
        btnEdit.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        timePicker.setEnabled(true);
        refInfo.setEnabled(true);
        infoTxt.setEnabled(true);
    }

    public void cancel(View View){
        save.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        btnEdit.setVisibility(View.VISIBLE);
        timePicker.setEnabled(false);
        refInfo.setEnabled(false);
        infoTxt.setEnabled(false);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timePicker.setText(formatter.format(refeicaoInformacao.getHora()));
        refInfo.setText(refeicaoInformacao.getRefeicao());
        infoTxt.setText(refeicaoInformacao.getInformacao());
    }


    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        View view;

        // saves a course into the bundle as a Serializable
        outState.putSerializable("visibilidadeSave", save.getVisibility());
        outState.putSerializable("visibilidade", btnEdit.getVisibility());
        outState.putSerializable("botaoEstado", refInfo.isEnabled());
        outState.putSerializable("refeicao",refeicaoInformacao);
        outState.putSerializable("hora",timePicker.getText().toString());
        outState.putSerializable("ref",refInfo.getText().toString());
        outState.putSerializable("info",infoTxt.getText().toString());

    }

    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        save.setVisibility((Integer) outState.getSerializable("visibilidadeSave"));
        cancel.setVisibility((Integer) outState.getSerializable("visibilidadeSave"));
        btnEdit.setVisibility((Integer) outState.getSerializable("visibilidade"));
        timePicker.setEnabled((Boolean) outState.getSerializable("botaoEstado"));
        refInfo.setEnabled((Boolean) outState.getSerializable("botaoEstado"));
        infoTxt.setEnabled((Boolean) outState.getSerializable("botaoEstado"));
        refeicaoInformacao=(Refeicao) outState.getSerializable("refeicaoListView");
        refInfo.setText(outState.getSerializable("ref").toString());
        infoTxt.setText(outState.getSerializable("info").toString());
        timePicker.setText(outState.getSerializable("hora").toString());
    }


    public void Save(View v){
//        refeicaoInformacao.setHora(horaEscolhida);
//        refeicaoInformacao.setInformacao(infoTxt.getText().toString());
//        refeicaoInformacao.setRefeicao(refInfo.getText().toString());
//
//        Intent intent = new Intent(this, PlanoAlimentar.class);
//        intent.putExtra("tipo","alterar");
//        intent.putExtra("AlterarRefeicao", refeicaoInformacao);
//        setResult(RESULT_OK, intent);
//        finish();

        if (!refeicaoInformacao.setRefeicao(refInfo.getText().toString())){
            Toast.makeText( getApplicationContext(), "Introduza a refeição!!" , Toast.LENGTH_LONG).show();
        }else if (!refeicaoInformacao.setInformacao(infoTxt.getText().toString())){
            Toast.makeText( getApplicationContext(), "Introduza a informação da refeição!!" , Toast.LENGTH_LONG).show();
        } else {
            refeicaoInformacao.setHora(horaEscolhida);

            Uri uri = Uri.parse("content://tam.aulasandroid.trabalhopratico.refeicao/refeicao");
            String selection = "id=?";
            String [] selectionArgs = new String[] { refeicaoInformacao.getId()};
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
            ContentValues values = new ContentValues();
            values.put("hora",formatter.format(refeicaoInformacao.getHora()));
            values.put("refeicao",refeicaoInformacao.getRefeicao());
            values.put("informacao",refeicaoInformacao.getInformacao());

            Intent intent = new Intent(this, PlanoAlimentar.class);
            intent.putExtra("tipo","alterar");
            intent.putExtra("AlterarRefeicao", refeicaoInformacao);
            setResult(RESULT_OK, intent);
            finish();
        }
    }




    public void warning(View v){
       DialogDelete d=new DialogDelete();
       d.show(getSupportFragmentManager(),"apagar refeição");
    }



    public void popTimePicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hora = hourOfDay;
                minuto = minute;
                timePicker.setText(String.format(Locale.getDefault(), "%02d:%02d", hora, minute));
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, hora);
                cal.set(Calendar.MINUTE, minute);
                horaEscolhida = cal.getTime();



            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hora, minuto, true);
        timePickerDialog.setTitle("Escolha a hora");
        timePickerDialog.show();
    }

    public void endDetalhes(View v){
        finish();
    }

    @Override
    public void ApplyChange(String res) {
        Uri uri = Uri.parse("content://tam.aulasandroid.trabalhopratico.refeicao/refeicao");
        String selection = "id=?";
        String [] selectionArgs = new String[] { refeicaoInformacao.getId()};
        getContentResolver().delete(uri,selection,selectionArgs);
        Uri uri2 = Uri.parse("content://tam.aulasandroid.trabalhopratico.refeicao/historico");
        String selection2 = "idref=?";
        String [] selectionArgs2 = new String[] { refeicaoInformacao.getId()};
        getContentResolver().delete(uri2,selection2,selectionArgs2);

        Intent intent = new Intent(this, PlanoAlimentar.class);
        intent.putExtra("tipo","remover");
        intent.putExtra("EliminarRefeicao", refeicaoInformacao);
        setResult(RESULT_OK, intent);
        finish();
    }
}