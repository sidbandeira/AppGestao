package com.example.sidnei.appgestao.pedidoCompra;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sidnei.appgestao.R;
import com.example.sidnei.appgestao.classeProduto.Produto;
import com.example.sidnei.appgestao.pedidoCompra.Adaptadores.AdapterItemCompra;
import com.example.sidnei.appgestao.pedidoCompra.Classes.PedidoCompraItem;
import com.example.sidnei.appgestao.utilitario.JSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PedidoCompraItemActivity extends AppCompatActivity {
    JSONObject jsonobject;
    JSONArray jsonarray;
    ArrayList<String> itemlist;
    ArrayList<Produto> item;
    ArrayList<String> itemlistpedido;
    ArrayList<PedidoCompraItem> itempedido;
    private ArrayList<PedidoCompraItem> itens = new ArrayList<PedidoCompraItem>();
    ArrayList<String> listaprodutos = new ArrayList<String>();
    private EditText edtCusto;
    private EditText edtQtde;
    private TextView txtSubTotalPedido;
    private TextView txtTotalItem;
    private Spinner spnProduto2;
    private Button btAdicionar;
    private Button btGravarPedido;

    //VARIAVEIS COM AS INFORMAÇOES DO PEDIDOCOMPRA
    Integer codfornecedor;
    String descricaofornecedor;
    String datapedido;
    String formapgto;

    //VARIAVEIS PARA CALCULAR OS VALORES DO PRODUTO SELECIONADO
    Double custo = 0.00;
    Double qtde = 0.00;
    Double total = 0.00;
    String descricaoProduto = "";
    String resultado = "";
    Double subtotal = 0.00;

    private AdapterItemCompra adapter;
    // FORMATA O VALOR DOUBLE COM TRES DECIMAIS
    DecimalFormat formato = new DecimalFormat("#.###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_compra_item);
        TextView txtSubTotalPedido = (TextView) findViewById(R.id.txtSubTotalPedido);
        final TextView txtTotalItem = (TextView) findViewById(R.id.txtTotalItem);
        final EditText edtCusto = (EditText) findViewById(R.id.edtCusto);
        final EditText edtQtde = (EditText) findViewById(R.id.edtQtde);

        //RECEBE O VALOR DAS VARIAVEIS PASSADAS DA PEDIDOCOMPRAACTIVITY
        Intent itPedidoCompra = getIntent();
        codfornecedor = Integer.parseInt(itPedidoCompra.getStringExtra("codfornecedor"));
        descricaofornecedor = itPedidoCompra.getStringExtra("descricaofornecedor");
        datapedido = itPedidoCompra.getStringExtra("datapedido");
        formapgto = itPedidoCompra.getStringExtra("formapgto");

        //COMANDO PARA SUPRIMIR O TECLADO AO ABRIR A TELA
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // RECUPERNADO A LISTVIEW DECLARADA NO XML PARA PODER DEFINIR O ADAPTER
        ListView listagem = (ListView) findViewById(R.id.lstProdutos);

        // RECUPERA O BUTTON ACICIONAR DECLARADO NO XML
        Button btAdicionar = (Button) findViewById(R.id.btAdicionar);

        //CRIA O ADAPTER
        adapter = new AdapterItemCompra(this, itens);

        //DEFINE O ADAPTER
        listagem.setAdapter(adapter);
        listagem.setCacheColorHint(Color.TRANSPARENT);

        // Download DO ARQUIVO JSON DE FORMA ASSINCRONA
        new DownloadJSON().execute();

        //METODO PARA ATUALIZAR O TOTAL QUANDO SETAR OU PERDER O FOCO DO CAMPO QUANTIDADE.
        edtQtde.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //VERIFICA QUAL A SITUACAO DO FOCO
                if(hasFocus){   //SET FOCUS
                    custo = Double.parseDouble(edtCusto.getText().toString());
                    total = custo * qtde;
                    resultado = String.format("%.3f", total);
                    resultado = resultado.replace(",", ".");

                    txtTotalItem.setText(resultado);
                }else {       //LOST FOCUS
                    qtde = Double.parseDouble(edtQtde.getText().toString());
                    total = custo * qtde;
                    resultado = String.format("%.3f", total);
                    resultado = resultado.replace(",", ".");
                    txtTotalItem.setText(resultado);
                }
            }
        });

        // RESPONSAVEL POR EXECUTAR A AÇÃO DO CLIQUE DO BOTÃO ADICIONAR
        btAdicionar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if(descricaoProduto.contains("Selecione")) {
                    Toast.makeText(PedidoCompraItemActivity.this,"Selecione um produto!",Toast.LENGTH_SHORT).show();
                }else{
                    custo = Double.parseDouble(edtCusto.getText().toString());
                    qtde = Double.parseDouble(edtQtde.getText().toString());
                    total = custo * qtde;
                    resultado = String.format("%.3f", total);
                    resultado = resultado.replace(",", ".");

                    txtTotalItem.setText(resultado);

                    PedidoCompraItem item = new PedidoCompraItem();
                    item.setDescricaoItem(descricaoProduto);
                    item.setPrecoCusto(custo);
                    item.setQtdeItem(qtde);
                    item.setTotalItem(Double.parseDouble(resultado));
                    itens.add(item);
                    adapter.notifyDataSetChanged();

                    limpaTela();
                }
            }
        });
    }

    protected void onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState){
        View ListView = inflater.inflate(R.layout.item_listagem_pedido, null);
    }

    // METODO QUE FAZ O DOWNLOAD DO ARQUIVO JSON
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // ARRAY COM A CLASSE PRODUTO
            item = new ArrayList<Produto>();
            // CRIA O ARRAY DE PRODUTOS PARA ALIMENTAR O SPINNER
            itemlist = new ArrayList<String>();

            // CHAMA A CLASSE JSON E PASSA A URL PARA BAIXAR O ARQUIVO COM OS PRODUTOS NO FORMATO JSON
            //jsonobject = JSON.getJSONfromURL("http://10.0.2.2:81/ws_sgestao/Json/ProdutoWS.json");
            jsonobject = JSON.getJSONfromURL("http://sgestao.hol.es/Json/ProdutoWS.json");
            try {
                // ADICIONA UM ITEM NA LISTA DE PRODUTOS PARA SERVIR DE HINT DO SPINNER
                Produto prod0 = new Produto();
                prod0.set_id(0);
                prod0.setProdutoDescricao("Selecione um Produto...");
                prod0.setProdutoPrecovenda(0.00);
                prod0.setProdutoPrecoCusto(0.00);
                item.add(prod0);
                // PREENCHE O SPINNER COM O ITEM DO HINT
                itemlist.add("Selecione um produto ...");

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
            try {
                final Spinner spnProduto2 = (Spinner) findViewById(R.id.spnProduto2);
                final EditText edtCusto = (EditText) findViewById(R.id.edtCusto);
                final EditText edtQtde = (EditText) findViewById(R.id.edtQtde);
                final TextView txtTotalItem = (TextView) findViewById(R.id.txtTotalItem);

                // SPINNER ADAPTER
                spnProduto2.setAdapter(new ArrayAdapter<String>(PedidoCompraItemActivity.this,
                        android.R.layout.simple_dropdown_item_1line, itemlist));

                // EXECUTA A AÇÃO QUANDO CLICADO EM UM ITEM DO SPINNER
                spnProduto2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        descricaoProduto = itemlist.get(position).toString();

                        // METODO PARA SETAR O FOCO NO SPINNER AO ENTRAR NA TELA.
                        arg0.post(new Runnable() {
                            @Override
                            public void run() {spnProduto2.requestFocusFromTouch();}
                        });

                        //ALIMENTA AS VARIAVEIS PARA CALCULAR O TOTAL DO PRODUTO
                        custo = Double.parseDouble(item.get(position).get_produtoPrecoCusto().toString());
                        qtde = Double.parseDouble("1.00");
                        total = custo * qtde;
                        resultado = String.format("%.3f", total);
                        resultado = resultado.replace(",", ".");
                        subtotal += total;

                        //SETA OS VALORES NOS EDITTEXT
                        edtCusto.setText(custo.toString());
                        edtQtde.setText(qtde.toString());
                        txtTotalItem.setText(resultado);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // METODO RESPONSAVEL POR LIMPAR OS DADOS DA TELA.
    public void limpaTela(){
        final EditText edtCusto = (EditText) findViewById(R.id.edtCusto);
        final EditText edtQtde = (EditText) findViewById(R.id.edtQtde);
        final TextView txtTotalItem = (TextView) findViewById(R.id.txtTotalItem);
        Spinner spnProduto2 = (Spinner) findViewById(R.id.spnProduto2);

        edtCusto.setText("0.00");
        edtQtde.setText("1.00");
        txtTotalItem.setText("0.00");
        spnProduto2.setSelection(0);
    }
}
