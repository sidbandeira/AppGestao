package com.example.sidnei.appgestao.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sidnei.appgestao.FechamentoCaixaActivity;
import com.example.sidnei.appgestao.MainActivity;
import com.example.sidnei.appgestao.PostosActivity;
import com.example.sidnei.appgestao.ProdutosActivity;
import com.example.sidnei.appgestao.R;
import com.example.sidnei.appgestao.classeProduto.PedidoItemActivity;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    List<Menu> menus;
    MenuAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listView = new ListView(this);
        setContentView(listView);

        menus = new ArrayList<Menu>();
        String descricao = "";
        for(int l = 0; l < 4; l++){
            switch (l) {
                case 0:
                    if(MainActivity.menuPosto ==  1) {
                        descricao = "1-Posto";
                    }
                    break;
                case 1:
                    if(MainActivity.menuLoja ==  1) {
                        descricao = "2-Vendas";
                    }
                    break;
                case 2:
                    if(MainActivity.menuLoja ==  1) {
                        descricao = "0-Caixa";
                    }
                    break;
                case 3:
                    if(MainActivity.menuLoja ==  1) {
                        descricao = "3-Produtos";
                    }
                    break;
                default:
                    descricao = "";
                    break;
            }

            if(descricao.toString() != ""){
                menus.add(new Menu(descricao));
                descricao = "";
            }
        }
        adapter = new MenuAdapter(this, menus);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //String[] partes = this.menus. .split("-");

        String[] partes = this.menus.get(position).descricao.split("-") ;

        switch (partes[1]) {
            case "Posto":
                    Intent it = new Intent(this, PostosActivity.class);
                    startActivity(it);
                break;
            case "Vendas":
                    Intent it1 = new Intent(this, PedidoItemActivity.class);
                    startActivity(it1);
                break;
            case "Caixa":
                    Intent it2 = new Intent(this, FechamentoCaixaActivity.class);
                    startActivity(it2);
                break;
            case "Produtos":
                    Intent it3 = new Intent(this, ProdutosActivity.class);
                    startActivity(it3);
                break;
            default:
                //Descricao = "";
                break;
        }
    }
}

//public class MenuActivity extends AppCompatActivity {
//
//    private Button btnVendas;
//    private Button btnCaixa;
//    private Button btnProdutos;
//    private Button btnPostos;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_menu);
//        btnCaixa = (Button) findViewById(R.id.btnCaixa);
//        btnVendas = (Button) findViewById(R.id.btnVendas);
//        btnProdutos = (Button) findViewById(R.id.btnProdutos);
//        btnPostos = (Button) findViewById(R.id.btnPostos);
//
//        if(MainActivity.menuLoja ==  0){
//            btnVendas.setVisibility(View.INVISIBLE);
//            btnCaixa.setVisibility(View.INVISIBLE);
//            btnProdutos.setVisibility(View.INVISIBLE);
//        }
//
//        if(MainActivity.menuPosto == 0){
//            btnPostos.setVisibility(View.INVISIBLE);
//        }
//    }
//
//    // CHAMA A TELA DE RESUMO DA VENDAS DO DIA
//    public void Vendas(View view) {
//        Intent it = new Intent(this,PedidoItemActivity.class);
//        startActivity(it);
//    }
//
//    //CHAMADA PARA A TELA DE RESUMO DE FECHAMENTO DE CAIXA
//    public void Caixa(View view) {
//        Intent it = new Intent(this, FechamentoCaixaActivity.class);
//        startActivity(it);
//    }
//
//    // CHAMADA DA TELA DE ESTOQUE DE PRODUTOS
//    public void Produtos(View view) {
//        Intent it = new Intent(this,ProdutosActivity.class);
//        startActivity(it);
//    }
//
//    // CHAMADA PARA A TELA DE ESTOQUE DOS TANQUES DE COMBUSTIVEIS - HABILITADO SOMENTE PARA POSTOS
//    public void Postos(View view) {
//        Intent it = new Intent(this,PostosActivity.class);
//        startActivity(it);
//    }
//}
