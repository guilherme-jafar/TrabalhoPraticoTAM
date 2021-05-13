package tam.aulasandroid.trabalhopratico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

public class NovaRefeicao extends AppCompatActivity {
    private int hora, minuto;
    private Button timePicker;
    private Date horaEscolhida = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_refeicao);


         timePicker = findViewById(R.id.timeInput);


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

    public void gravarNovaRefeicao(View v){
         Button hora = findViewById(R.id.timeInput);
         EditText nomeRefeicao = findViewById(R.id.refeicaoInput);
        EditText informacao = findViewById(R.id.informacaoInput);

            Refeicao refeicao = new Refeicao();


         if (horaEscolhida == null){
             Toast.makeText( getApplicationContext(), "Escolha uma hora!!" , Toast.LENGTH_LONG).show();
         }else if (!refeicao.setRefeicao(nomeRefeicao.getText().toString())){
             Toast.makeText( getApplicationContext(), "Introduza a refeição!!" , Toast.LENGTH_LONG).show();
         }else {
             refeicao.setId(UUID.randomUUID().toString());
             refeicao.setHora(horaEscolhida);
             refeicao.setInformacao(informacao.getText().toString());
             Intent intent = new Intent(this, PlanoAlimentar.class);
             intent.putExtra("novaRefeicao", refeicao);
             setResult(RESULT_OK, intent);
             finish();
             //startActivity(intent);
         }







    }

    public void voltar(View v){
        startActivity(new Intent(this, PlanoAlimentar.class));
    }
}