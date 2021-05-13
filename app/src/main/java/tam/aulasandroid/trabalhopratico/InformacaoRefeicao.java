package tam.aulasandroid.trabalhopratico;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class InformacaoRefeicao extends AppCompatActivity implements DialogDelete.DialogDeleteListener{

    Button save,cancel;
    Refeicao refeicaoInformacao;
    EditText horaInfo,infoTxt,refInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_refeicao);


        horaInfo = findViewById(R.id.horaInfo);
        refInfo  = findViewById(R.id.refInfo);
        infoTxt  = findViewById(R.id.infoTxt);
        cancel=findViewById(R.id.cancelDetails);
        save=findViewById(R.id.saveDetails);
        save.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        horaInfo.setKeyListener(null);
        refInfo.setKeyListener(null);
        infoTxt.setKeyListener(null);


        Bundle dados = getIntent().getExtras();
        if (dados != null){
            refeicaoInformacao = (Refeicao) dados.get("informacaoRefeicao");
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
            horaInfo.setText(formatter.format(refeicaoInformacao.getHora()));
            refInfo.setText(refeicaoInformacao.getRefeicao());
            infoTxt.setText(refeicaoInformacao.getInformacao());

        }
    }

    public void Edit(View View){
        cancel.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
    }
    public void warning(View v){
       DialogDelete d=new DialogDelete();
       d.show(getSupportFragmentManager(),"apagar refeição");
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