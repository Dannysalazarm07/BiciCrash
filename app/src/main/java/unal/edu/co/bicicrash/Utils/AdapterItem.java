package unal.edu.co.bicicrash.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import unal.edu.co.bicicrash.R;

/**
 * Created by MiguelPC on 30-Sep-17.
 */

public class AdapterItem extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<BiciContact> items;

    public AdapterItem (Activity activity, ArrayList<BiciContact> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;

        if (view == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_contact, null);
        }

        BiciContact dir = items.get(i);

        TextView title = (TextView) v.findViewById(R.id.category);
        title.setText(dir.getName());

        TextView description = (TextView) v.findViewById(R.id.texto);
        description.setText(dir.getNumber());

        //TODO aqui se debe cargar el icono de cada contacto
        //ImageView imagen = (ImageView) v.findViewById(R.id.imageView);
        //imagen.setImageDrawable(dir.getImage());

        return v;


    }
}
