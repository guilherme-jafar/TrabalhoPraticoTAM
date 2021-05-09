package tam.aulasandroid.trabalhopratico;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RefeicaoAdapater extends BaseAdapter {

    Context context;
    List<Refeicao> adaptRefeicoes;

    // The constructor receives a context and the data
    public RefeicaoAdapater(Context ctx, List<Refeicao> list) {
        context = ctx;
        adaptRefeicoes = list;
    }

    // This method is called each time an item needs to be presented in the ListView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // convertView has the previous View for this position
        View rowView = convertView;

        // we only need to create the view if it does not exist
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.lista_refeicoes, parent, false);
        }

        // These are the Views inside the ListView item
        TextView textName = (TextView) rowView.findViewById(R.id.hora_lista);
        TextView textEmail = (TextView) rowView.findViewById(R.id.refeicao_lista);


        // obtains the contact for this position
        Refeicao r = adaptRefeicoes.get(position);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        // sets the TextView texts
        textName.setText(formatter.format(r.getHora()));
        textEmail.setText(r.getRefeicao());

        // returns the view
        return rowView;
    }

    // These methods must be implemented
    // They may be used to get information about the selected item

    @Override
    public int getCount() {
        return adaptRefeicoes.size();
    }

    @Override
    public Object getItem(int position) {
        return adaptRefeicoes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
