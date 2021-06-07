package tam.aulasandroid.trabalhopratico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class registoRefeicao extends AppCompatActivity {


    private int hora, minuto;
    private Date horaEscolhida = null;
    private Button timePicker;
    private Refeicao refeicao;
    private TextView horaTxt, refInfo;
    private SimpleDateFormat formatter;
    private EditText info;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String dataAtual;
    Format formatter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo_refeicao);
        refInfo = findViewById(R.id.RefeicaoOk);
        horaTxt = findViewById(R.id.horaRefeicao);
        info = findViewById(R.id.infoTxt2);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        timePicker = findViewById(R.id.timeInput3);
        formatter2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dataAtual = formatter2.format(Calendar.getInstance().getTime());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);

                timePicker.setEnabled(radioButton.getText().toString().equalsIgnoreCase("realizada"));


            }
        });

        formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());

        Bundle dados = getIntent().getExtras();
        if (dados != null) {
            refeicao = (Refeicao) dados.get("Refeicao");

            refInfo.setText(refeicao.getRefeicao());
            System.out.println(refeicao.getHora());
            horaTxt.setText(
                    formatter.format(refeicao.getHora()));

            horaEscolhida = null;

        }
    }


    public void popTimePicker(View view) {
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

    public void cancel(View view) {

        Intent intent = new Intent(this, PlanoAlimentar.class);
        intent.putExtra("estado", "cancelar");
        intent.putExtra("Refeicao", refeicao);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        View view;

        outState.putSerializable("hora", timePicker.getText().toString());

        outState.putSerializable("info", info.getText().toString());

    }

    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);

        info.setText(outState.getSerializable("info").toString());
        timePicker.setText(outState.getSerializable("hora").toString());
    }

    public void save(View view) {

        boolean status;

        int selectedId = radioGroup.getCheckedRadioButtonId();

        radioButton = (RadioButton) findViewById(selectedId);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        if (horaEscolhida == null && radioButton.getText().toString().equalsIgnoreCase("realizada")) {
            Toast.makeText(getApplicationContext(), "Escolha uma hora!!", Toast.LENGTH_LONG).show();
        } else if (info.getText().toString().matches("") && radioButton.getText().toString().equalsIgnoreCase("não realizada")) {
            Toast.makeText(getApplicationContext(), "Indique alguma observação", Toast.LENGTH_LONG).show();
        }  else {

                 status = radioButton.getText().toString().equalsIgnoreCase("realizada");
                 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                 Calendar c = Calendar.getInstance();
                 String date = sdf.format(c.getTime());
                 ContentValues values = new ContentValues();
                 values.put("id", UUID.randomUUID().toString());
                 values.put("idref", refeicao.getId());
                 values.put("estado", status);
                 values.put("dia", date);
                 values.put("obs", info.getText().toString());
                 if (status) {
                     values.put("hora", formatter.format(horaEscolhida));
                     Uri uriAll = Uri.parse("content://tam.aulasandroid.trabalhopratico.refeicao/historico");

                     String selection = "dia=? and hora >= ?";

                     String[] selectionArgs = new String[]{dataAtual,formatter.format(horaEscolhida)};

                     Cursor curRes = managedQuery(uriAll, null, selection, selectionArgs, null);

                         System.out.println(curRes.getCount());

                         if (curRes.getCount()!=0) {
                         Toast.makeText(getApplicationContext(), "Hora não pode ser inferior a ultima refeicao!!", Toast.LENGTH_LONG).show();
                     }else {
                         Uri uri = Uri.parse("content://tam.aulasandroid.trabalhopratico.refeicao/historico");
                         getContentResolver().insert(uri, values);
                         Intent intent = new Intent(this, PlanoAlimentar.class);

                         intent.putExtra("estado", "salvar");
                         setResult(RESULT_OK, intent);
                         finish();

                     }
                 } else {
                     values.put("hora", "");
                     Uri uri = Uri.parse("content://tam.aulasandroid.trabalhopratico.refeicao/historico");
                     getContentResolver().insert(uri, values);
                     Intent intent = new Intent(this, PlanoAlimentar.class);

                     intent.putExtra("estado", "salvar");
                     setResult(RESULT_OK, intent);
                     finish();
                 }



        }


//        if (!refeicaoInformacao.setRefeicao(refInfo.getText().toString())){
//            Toast.makeText( getApplicationContext(), "Introduza a refeição!!" , Toast.LENGTH_LONG).show();
//        }else if (!refeicaoInformacao.setInformacao(infoTxt.getText().toString())){
//            Toast.makeText( getApplicationContext(), "Introduza a informação da refeição!!" , Toast.LENGTH_LONG).show();
//        } else {
//            refeicaoInformacao.setHora(horaEscolhida);
//
//            Intent intent = new Intent(this, PlanoAlimentar.class);
//            intent.putExtra("tipo","alterar");
//            intent.putExtra("AlterarRefeicao", refeicaoInformacao);
//            setResult(RESULT_OK, intent);
//            finish();


    }
}