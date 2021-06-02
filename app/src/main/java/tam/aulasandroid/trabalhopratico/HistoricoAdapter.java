package tam.aulasandroid.trabalhopratico;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HistoricoAdapter extends BaseAdapter {

    Context context;
    List<RegistroAlimentar> adaptHistorico;

    // The constructor receives a context and the data
    public HistoricoAdapter(Context ctx, List<RegistroAlimentar> list) {
        context = ctx;
        adaptHistorico = list;
    }

    // This method is called each time an item needs to be presented in the ListView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // convertView has the previous View for this position
        View rowView = convertView;

        // we only need to create the view if it does not exist
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.lista_historico, parent, false);
        }

        // These are the Views inside the ListView item
        TextView horaPrevista = (TextView) rowView.findViewById(R.id.hora_prevista);
        TextView nomeRefeicao = (TextView) rowView.findViewById(R.id.refeicao_historico);
        TextView realizada    = (TextView) rowView.findViewById(R.id.realizada_historico);


        // obtains the contact for this position
        RegistroAlimentar r = adaptHistorico.get(position);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        // sets the TextView texts
        horaPrevista.setText(formatter.format(r.getHora()));
        nomeRefeicao.setText("Refeicao");
        realizada.setText(formatter.format(r.getHora()));

        // returns the view
        return rowView;
    }

    // These methods must be implemented
    // They may be used to get information about the selected item

    @Override
    public int getCount() {
        return adaptHistorico.size();
    }

    @Override
    public Object getItem(int position) {
        return adaptHistorico.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
