package tam.aulasandroid.trabalhopratico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class InformacaoHistoricoRefeicao extends AppCompatActivity {

    private TextView horaPrevistaHist,nomeRefeicaoHist, observacaoHist, horaRealizadaHist;
    private RegistroAlimentar historicoRef;
    private RadioButton radioButtonNaoRealizadaHist, radioButtonRealizadaHist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_historico_refeicao);

        horaPrevistaHist = findViewById(R.id.horaPrevistaHist);
        nomeRefeicaoHist = findViewById(R.id.nomeRefeicaoHist);
        observacaoHist = findViewById(R.id.observacaoHist);
        horaRealizadaHist = findViewById(R.id.horaRealizadaHist);
        radioButtonRealizadaHist = findViewById(R.id.radioButtonRealizadaHist);
        radioButtonNaoRealizadaHist = findViewById(R.id.radioButtonNaoRealizadaHist);

        Bundle dados = getIntent().getExtras();
        if (dados != null){
            historicoRef = (RegistroAlimentar) dados.get("informacaoRefeicaoHistorico");
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
            horaPrevistaHist.setText(formatter.format(historicoRef.getHora()) );
            nomeRefeicaoHist.setText("Nome da refeicao");
            observacaoHist.setText(historicoRef.getObs());
            horaRealizadaHist.setText(formatter.format(historicoRef.getHora()));

            if (historicoRef.isEstado()){
                radioButtonRealizadaHist.setChecked(true);
                radioButtonNaoRealizadaHist.setChecked(false);
                radioButtonRealizadaHist.setEnabled(false);
                radioButtonNaoRealizadaHist.setEnabled(false);
            }else {
                radioButtonNaoRealizadaHist.setActivated(true);
                radioButtonRealizadaHist.setChecked(false);
                radioButtonRealizadaHist.setEnabled(false);
                radioButtonNaoRealizadaHist.setEnabled(false);
            }

        }



    }


}