package com.example.sidnei.appgestao.unidadeNegocio;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sidnei.appgestao.Classes.UnidadeNegocio;
import com.example.sidnei.appgestao.R;

import java.util.List;

public class UnNegocioListAdapter extends ArrayAdapter<UnidadeNegocio> {
    public UnNegocioListAdapter(Context context, List<UnidadeNegocio> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        UnidadeNegocio unidadenegocio = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_unidadenegocio_list, null);
            holder = new ViewHolder();
            holder.txtUnid = (TextView)convertView.findViewById(R.id.txtUnid);
            holder.txtRazaoSocial =  (TextView)convertView.findViewById(R.id.txtRazaoSocial);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.txtUnid.setText(String.valueOf(unidadenegocio.codunidade));
        holder.txtRazaoSocial.setText(unidadenegocio.razaosocial);

        return convertView;
    }
    static class ViewHolder {
        TextView txtUnid;
        TextView txtRazaoSocial;

    }
}
