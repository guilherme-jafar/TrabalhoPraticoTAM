package tam.aulasandroid.trabalhopratico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class InformacaoRefeicao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_refeicao);

        TextView horaInfo = findViewById(R.id.horaInfo);
        TextView refInfo  = findViewById(R.id.refInfo);
        TextView infoTxt  = findViewById(R.id.infoTxt);



        Bundle dados = getIntent().getExtras();
        if (dados != null){
            Refeicao refeicaoInformacao = (Refeicao) dados.get("informacaoRefeicao");
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
            horaInfo.setText(formatter.format(refeicaoInformacao.getHora()));
            refInfo.setText(refeicaoInformacao.getRefeicao());
            infoTxt.setText(refeicaoInformacao.getInformacao());

        }
    }
}