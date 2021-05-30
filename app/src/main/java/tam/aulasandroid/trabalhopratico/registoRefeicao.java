package tam.aulasandroid.trabalhopratico;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class registoRefeicao extends AppCompatActivity {


    private int hora, minuto;
    private Date horaEscolhida;
    private Button timePicker;
    private Refeicao refeicao;
    private TextView horaTxt,refInfo;
    private SimpleDateFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo_refeicao);
        refInfo  = findViewById(R.id.RefeicaoOk);
        horaTxt  = findViewById(R.id.horaRefeicao);
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

    }

    public void cancel(View view){

        Intent intent = new Intent(this, PlanoAlimentar.class);

        intent.putExtra("Refeicao", refeicao);
        setResult(RESULT_OK, intent);
        finish();
    }
}