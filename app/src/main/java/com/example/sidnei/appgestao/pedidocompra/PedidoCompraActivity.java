package com.example.sidnei.appgestao.pedidoCompra;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sidnei.appgestao.R;
import com.example.sidnei.appgestao.utilitario.JSON;
import com.example.sidnei.appgestao.utilitario.Mascara;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PedidoCompraActivity extends AppCompatActivity implements View.OnClickListener {
    JSONObject jsonobject;
    JSONArray jsonarray;
    private Button btnProdutos;
    private EditText edtData;
    ArrayList<String> fornecedorlist;
    ArrayList<Fornecedor> fornecedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_compra);

        edtData = (EditText) findViewById(R.id.edtData);

        edtData.addTextChangedListener(Mascara.insert(Mascara.MaskType.DATA,  edtData));
        btnProdutos = (Button) findViewById(R.id.btnProdutos);
        btnProdutos.setOnClickListener(this);

        new DownloadJSON().execute();
    }
    @Override
    public void onClick(View v) {
        Intent it = new Intent(this, PedidoCompraItemActivity.class);
        startActivity(it);
    }

    // Download JSON file AsyncTask
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Locate the WorldPopulation Class
            fornecedor = new ArrayList<Fornecedor>();
            // Create an array to populate the spinner
            fornecedorlist = new ArrayList<String>();

            // CHAMA A CLASSE JSON E PASSA A URL PARA BAIXAR O ARQUIVO COM OS FORNECEDORES NO FORMATO JSON
            //jsonobject = JSON.getJSONfromURL("http://10.0.2.2:81/ws_sgestao/Json/ProdutoWS.json");
            //jsonobject = JSON.getJSONfromURL("http://sgestao.hol.es/Json/ProdutoWS.json");
            jsonobject = JSON.getJSONfromURL("http://sgestao.hol.es/ws/FornecedorWs.php?codempresa=3");
            try {
                // ADICIONA UM ITEM NA LISTA DE PRODUTOS PARA SERVIR DE HINT DO SPINNER
                Fornecedor for0 = new Fornecedor();

                for0.setId(0);
                for0.setDescricaofornecedor("Selecione um Fornecedor...");
                fornecedor.add(for0);

                // PREENCHE O SPINNER COM O ITEM DO HINT
                fornecedorlist.add("Selecione um fornecedor ...");

                // PEGA O NÓ DOS FORNECEDORES
                jsonarray = jsonobject.getJSONArray("fornecedores");
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);

                    Fornecedor forn = new Fornecedor();
                    forn.setId(jsonobject.optInt("id"));
                    forn.setDescricaofornecedor(jsonobject.optString("razaosocial"));
                    fornecedor.add(forn);

                    // PREENCHE O SPINNER COM O ID E RAZAO SOCIAL DO FORNECEDOR
                    fornecedorlist.add(jsonobject.optString("id")+ "- " + jsonobject.optString("razaosocial"));

                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // SETA O SPINNER DO FORNECEDOR DA TELA DE PEDIDO
            final Spinner spnFornecedor = (Spinner) findViewById(R.id.spnFornecedor);

            // SPINNER ADAPTER
            spnFornecedor.setAdapter(new ArrayAdapter<String>(PedidoCompraActivity.this,
                    android.R.layout.simple_spinner_dropdown_item, fornecedorlist));

            spnFornecedor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {

                    // METODO PARA SETAR O FOCO NO SPINNER AO ENTRAR NA TELA.
                    arg0.post(new Runnable() {
                        @Override
                        public void run() {spnFornecedor.requestFocusFromTouch();}
                    });

                    //final EditText edtCusto = (EditText) findViewById(R.id.edtCusto);
                    //final EditText edtQtde = (EditText) findViewById(R.id.edtQtde);
                    //final EditText edtTotal = (EditText) findViewById(R.id.edtTotal);
                    //descricaoProduto = itemlist.get(position).toString();

                    //ALIMENTA AS VARIAVEIS PARA CALCULAR O TOTAL DO PRODUTO
                    //custo = Double.parseDouble(item.get(position).get_produtoPrecoCusto().toString());
                    //qtde = Double.parseDouble("1.00");
                    //total = custo * qtde;
                    //total = Double.valueOf(formato.format(total));

                    //SETA OS VALORES NOS EDITTEXT
                    //edtCusto.setText(custo.toString());
                    //edtQtde.setText(qtde.toString());
                    //edtTotal.setText(total.toString());

                    //METODO PARA ATUALIZAR O TOTAL QUANDO SETAR OU PERDER O FOCO DO CAMPO QUANTIDADE.
                    //edtQtde.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                      //  @Override
                     //   public void onFocusChange(View v, boolean hasFocus) {
                            //EditText edttotal = (EditText) findViewById(R.id.edtTotal);

                            //VERIFICA QUAL A SITUACAO DO FOCO
                            //if(hasFocus){   //SET FOCUS
                            //    custo = Double.parseDouble(edtCusto.getText().toString());
                            //    total = custo * qtde;
                                //total = Double.valueOf(formato.format(total));
                            //    edttotal.setText(total.toString());
                            //}else {       //LOST FOCUS
                            //    qtde = Double.parseDouble(edtQtde.getText().toString());
                            //    total = custo * qtde;
                                //total = Double.valueOf(formato.format(total));
                            //    edttotal.setText(total.toString());
                            //}
                     //   }
                    //});
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });

        }
    }
}
