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
            holder.txtId = (TextView)convertView.findViewById(R.id.txtId);
            holder.txtRazaoSocial =  (TextView)convertView.findViewById(R.id.txtRazaoSocial);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.txtId.setText(String.valueOf(unidadenegocio.id));
        holder.txtRazaoSocial.setText(unidadenegocio.razaosocial);

        return convertView;
    }
    static class ViewHolder {
        TextView txtId;
        TextView txtRazaoSocial;

    }
}
