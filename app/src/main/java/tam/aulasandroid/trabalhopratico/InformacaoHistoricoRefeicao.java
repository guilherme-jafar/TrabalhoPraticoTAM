package tam.aulasandroid.trabalhopratico;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InformacaoHistoricoRefeicao extends AppCompatActivity {

    String TAG = "Infomracao historico";
    private TextView horaRealizadaHist;
    private  TextView nomeRefeicaoHist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_historico_refeicao);

        TextView horaPrevistaHist = findViewById(R.id.horaPrevistaHist);
        nomeRefeicaoHist = findViewById(R.id.nomeRefeicaoHist);
        TextView observacaoHist = findViewById(R.id.observacaoHist);
        horaRealizadaHist = findViewById(R.id.horaRealizadaHist);
        RadioButton radioButtonRealizadaHist = findViewById(R.id.radioButtonRealizadaHist);
        RadioButton radioButtonNaoRealizadaHist = findViewById(R.id.radioButtonNaoRealizadaHist);

        Bundle dados = getIntent().getExtras();
        if (dados != null){
            RegistroAlimentar historicoRef = (RegistroAlimentar) dados.get("informacaoRefeicaoHistorico");
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
            if(historicoRef.getHora()!=null) {
                horaRealizadaHist.setText(formatter.format(historicoRef.getHora()));
            }else{
                horaRealizadaHist.setText("xxx");
            }
            observacaoHist.setText(historicoRef.getObs());
            Log.e(TAG, historicoRef.getRefId());
            nomeRefeicaoHist.setText(historicoRef.getNomeRefeicao());
            horaPrevistaHist.setText(historicoRef.getHoraRefeicao());


            Log.e("historivo",String.valueOf(historicoRef.isEstado())  );
            if (historicoRef.isEstado()){
                radioButtonRealizadaHist.setChecked(true);
                radioButtonNaoRealizadaHist.setChecked(false);
                radioButtonRealizadaHist.setEnabled(false);
                radioButtonNaoRealizadaHist.setEnabled(false);
            }else {
                radioButtonNaoRealizadaHist.setChecked(true);
                radioButtonRealizadaHist.setChecked(false);
                radioButtonRealizadaHist.setEnabled(false);
                radioButtonNaoRealizadaHist.setEnabled(false);
            }

        }



    }




}