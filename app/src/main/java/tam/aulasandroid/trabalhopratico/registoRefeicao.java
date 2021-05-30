package tam.aulasandroid.trabalhopratico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
    private TextView horaTxt,refInfo;
    private SimpleDateFormat formatter;
    private EditText info;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo_refeicao);
        refInfo  = findViewById(R.id.RefeicaoOk);
        horaTxt  = findViewById(R.id.horaRefeicao);
        info=findViewById(R.id.infoTxt2);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        timePicker = findViewById(R.id.timeInput3);


        formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());

        Bundle dados = getIntent().getExtras();
        if (dados != null){
            refeicao = (Refeicao) dados.get("Refeicao");

            refInfo.setText(refeicao.getRefeicao());
            System.out.println(refeicao.getHora());
            horaTxt.setText(
                    formatter.format(refeicao.getHora()));

            horaEscolhida=refeicao.getHora();

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

    public void cancel(View view){

        Intent intent = new Intent(this, PlanoAlimentar.class);
        intent.putExtra("estado", "cancelar");
        intent.putExtra("Refeicao", refeicao);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void save(View view){

        boolean status;

        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId==0){
            Toast.makeText( getApplicationContext(), "Indique se a refeicao foi realizada" , Toast.LENGTH_LONG).show();
        }else {
            radioButton = (RadioButton) findViewById(selectedId);
            if (radioButton.getText().toString().equalsIgnoreCase("realizada")) {
                status = true;
            } if (horaEscolhida == null){
                Toast.makeText( getApplicationContext(), "Escolha uma hora!!" , Toast.LENGTH_LONG).show();
            }
            else if(info.getText().toString()==null){
                Toast.makeText( getApplicationContext(), "Indique alguma observação" , Toast.LENGTH_LONG).show();
            }
            else {
                if (radioButton.getText().toString().equalsIgnoreCase("realizada")) {
                    status = true;
                }else {
                    status = false;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = Calendar.getInstance();
                String date = sdf.format(c.getTime());
                ContentValues values = new ContentValues();
                values.put("id",UUID.randomUUID().toString());
                values.put("idref",refeicao.getId());
                values.put("estado",status);
                values.put("hora",formatter.format(horaEscolhida));
                values.put("dia",date);
                values.put("obs",info.getText().toString());
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