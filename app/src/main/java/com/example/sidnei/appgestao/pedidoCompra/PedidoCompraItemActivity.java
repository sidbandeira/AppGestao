package com.example.sidnei.appgestao.pedidoCompra;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sidnei.appgestao.R;
import com.example.sidnei.appgestao.classeProduto.Produto;
import com.example.sidnei.appgestao.utilitario.JSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PedidoCompraItemActivity extends AppCompatActivity {
    JSONObject jsonobject;
    JSONArray jsonarray;

    ArrayList<String> itemlist;
    ArrayList<Produto> item;

    //VARIAVEIS PARA CALCULAR OS VALORES DO PRODUTO SELECIONADO
    Double custo = 0.00;
    Double qtde = 0.00;
    Double total = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_compra_item);

        // Download JSON file AsyncTask
        new DownloadJSON().execute();
    }


    // Download JSON file AsyncTask
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Locate the WorldPopulation Class
            item = new ArrayList<Produto>();
            // Create an array to populate the spinner
            itemlist = new ArrayList<String>();


            // CHAMA A CLASSE JSON E PASSA A URL PARA BAIXAR O ARQUIVO COM OS PRODUTOS NO FORMATO JSON
            jsonobject = JSON.getJSONfromURL("http://10.0.2.2:81/ws_sgestao/Json/ProdutoWS.json");

            try {
                // PEGA O NÓ DOS PRODUTOS
                jsonarray = jsonobject.getJSONArray("produtos");
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);

                    Produto prod = new Produto();

                    prod.set_id(jsonobject.optInt("idproduto"));
                    prod.setProdutoDescricao(jsonobject.optString("produtodescricao"));
                    prod.setProdutoPrecovenda(jsonobject.optDouble("produtoprecovenda"));
                    prod.setProdutoPrecoCusto(jsonobject.optDouble("produtoprecocusto"));
                    item.add(prod);

                    // PREENCHE O SPINNER COM O ID E DESCRICAO DO PRODUTO
                    itemlist.add(jsonobject.optString("idproduto")+ "- " + jsonobject.optString("produtodescricao"));

                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // SETA O SPINNER DA TELA DE PEDIDOITEM

            Spinner spnProduto2 = (Spinner) findViewById(R.id.spnProduto2);

            // SPINNER ADAPTER
            spnProduto2.setAdapter(new ArrayAdapter<String>(PedidoCompraItemActivity.this,
                    android.R.layout.simple_spinner_dropdown_item, itemlist));

            // EXECUTA A AÇÃO QUANDO CLICADO EM UM ITEM DO SPINNER
            spnProduto2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    final EditText edtCusto = (EditText) findViewById(R.id.edtCusto);
                    final EditText edtQtde = (EditText) findViewById(R.id.edtQtde);
                    EditText edtTotal = (EditText) findViewById(R.id.edtTotal);

                    //ALIMENTA AS VARIAVEIS PARA CALCULAR O TOTAL DO PRODUTO
                    custo = Double.parseDouble(item.get(position).get_produtoPrecoCusto().toString());
                    qtde = Double.parseDouble("1.00");
                    total = custo * qtde;

                    //SETA OS VALORES NOS EDITTEXT
                    edtCusto.setText(custo.toString());
                    edtQtde.setText(qtde.toString());
                    edtTotal.setText(total.toString());

                    //METODO PARA ATUALIZAR O TOTAL QUANDO SETAR OU PERDER O FOCO DO CAMPO QUANTIDADE.
                    edtQtde.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            EditText edttotal = (EditText) findViewById(R.id.edtTotal);

                            //VERIFICA QUAL A SITUACAO DO FOCO
                            if(hasFocus){   //SET FOCUS
                                custo = Double.parseDouble(edtCusto.getText().toString());
                                total = custo * qtde;
                                edttotal.setText(total.toString());
                            }else {       //LOST FOCUS
                                qtde = Double.parseDouble(edtQtde.getText().toString());
                                total = custo * qtde;
                                edttotal.setText(total.toString());
                            }
                        }
                    });

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
        }
    }
}
