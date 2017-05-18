package com.example.sidnei.appgestao.menu;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sidnei.appgestao.Classes.Menu;
import com.example.sidnei.appgestao.R;

import java.util.List;

public class MenuAdapter extends BaseAdapter {
    Context ctx;
    List<Menu> menus;

    //CONSTRUTOR
    public MenuAdapter(Context ctx, List<Menu> menus){
        this.ctx = ctx;
        this.menus = menus;
    }

    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public Object getItem(int position) {
        return menus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Menu menu = menus.get(position);
        ViewHolder holder = null;

        if(convertView == null){
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_menu_list, null);
            holder = new ViewHolder();
            holder.txtDescricao = (TextView) convertView.findViewById(R.id.txtMenu);
            holder.imgLogo = (ImageView) convertView.findViewById(R.id.imgMenu);
            //holder.txtDtNasc = (TextView) convertView.findViewById(R.id.txtDtNasc);

            convertView.setTag(holder);;
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        String[] partes = menu.descricao.split("-");
        Resources res = ctx.getResources();
        holder.txtDescricao.setText(partes[1]);

        Resources resImg = ctx.getResources();
        TypedArray logos = resImg.obtainTypedArray(R.array.imgmenus);
        holder.imgLogo.setImageDrawable(logos.getDrawable(Integer.parseInt(partes[0])));

        return convertView;
    }

    static class ViewHolder{
        TextView txtDescricao;
        ImageView imgLogo;
    }

}