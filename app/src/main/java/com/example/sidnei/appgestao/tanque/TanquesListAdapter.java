package com.example.sidnei.appgestao.tanque;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sidnei.appgestao.R;

import java.util.List;

public class TanquesListAdapter extends ArrayAdapter<Tanque>{
    public TanquesListAdapter(Context context, List<Tanque> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tanque tanque = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tanque,null);
            holder = new ViewHolder();
            holder.imgTanque = (ImageView) convertView.findViewById(R.id.imgTanque);
            holder.txtCapacidade =(TextView) convertView.findViewById(R.id.txtCapacidade);
            holder.txtEstoque = (TextView) convertView.findViewById(R.id.txtEstoque);
            holder.txtTanque= (TextView) convertView.findViewById(R.id.txtTanque);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Resources res = getContext().getResources();
        TypedArray logos = res.obtainTypedArray(R.array.logos);
        holder.imgTanque.setImageDrawable(logos.getDrawable(tanque.codImagem));

        holder.txtTanque.setText(Integer.toString(tanque.codTanque) + " - " + tanque.tipoCombustivel);
        holder.txtCapacidade.setText(Double.toString(tanque.capacidadeTanque)) ;
        holder.txtEstoque.setText(Double.toString(tanque.estoqueTanque));

        return convertView;
    }

    static class ViewHolder{
        ImageView imgTanque;
        TextView txtTanque;
        TextView txtCapacidade;
        TextView txtEstoque;
    }
}
