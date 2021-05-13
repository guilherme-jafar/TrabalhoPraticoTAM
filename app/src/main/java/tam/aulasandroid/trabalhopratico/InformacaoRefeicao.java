package tam.aulasandroid.trabalhopratico;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
    private Date horaEscolhida = null;
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


    public void Save(View v){
        refeicaoInformacao.setHora(horaEscolhida);
        refeicaoInformacao.setInformacao(infoTxt.getText().toString());
        refeicaoInformacao.setRefeicao(refInfo.getText().toString());
        Intent intent = new Intent(this, PlanoAlimentar.class);
        intent.putExtra("tipo","alterar");
        intent.putExtra("AlterarRefeicao", refeicaoInformacao);
        setResult(RESULT_OK, intent);
        finish();
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

        Intent intent = new Intent(this, PlanoAlimentar.class);
        intent.putExtra("tipo","remover");
        intent.putExtra("EliminarRefeicao", refeicaoInformacao);
        setResult(RESULT_OK, intent);
        finish();
    }
}