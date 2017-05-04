package com.example.sidnei.appgestao.pedidoCompra;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import com.example.sidnei.appgestao.MainActivity;
import com.example.sidnei.appgestao.R;
import com.example.sidnei.appgestao.classeProduto.Produto;
import com.example.sidnei.appgestao.pedidoCompra.Adaptadores.AdapterItemCompra;
import com.example.sidnei.appgestao.pedidoCompra.Classes.PedidoCompra;
import com.example.sidnei.appgestao.pedidoCompra.Classes.PedidoCompraItem;
import com.example.sidnei.appgestao.pedidoCompra.Repositorio.PedidoCompraItemRepositorio;
import com.example.sidnei.appgestao.pedidoCompra.Repositorio.PedidoCompraRepositorio;
import com.example.sidnei.appgestao.unidadeNegocio.UnidadeNegocioListFragment;
import com.example.sidnei.appgestao.utilitario.JSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PedidoCompraItemActivity extends AppCompatActivity {
    private JSONObject jsonobject;
    private JSONArray jsonarray;
    private ArrayList<String> itemlist;
    private ArrayList<Produto> item;
    private ArrayList<String> itemlistpedido;
    private ArrayList<PedidoCompraItem> itempedido;
    private ArrayList<PedidoCompraItem> itens = new ArrayList<PedidoCompraItem>();
    private ArrayList<String> listaprodutos = new ArrayList<String>();
    private EditText edtCusto;
    private EditText edtQtde;
    private TextView txtSubTotalPedido;
    private TextView txtTotalItem;
    private Spinner spnProduto2;
    private Button btAdicionar;
    private Button btSalvarPedido;
    private ListView listagem;

    //VARIAVEIS COM AS INFORMAÇOES DO PEDIDOCOMPRA
    private Integer codfornecedor;
    private String descricaofornecedor;
    private String email;
    private String datapedido;
    private String dataentrega;
    private String formapgto;

    //VARIAVEIS PARA CALCULAR OS VALORES DO PRODUTO SELECIONADO
    private Integer codItem = 0;
    private Double custo = 0.00;
    private Double qtde = 0.00;
    private Double total = 0.00;
    private String descricaoProduto = "";
    private String resultado = "";
    private Double subtotalItem = 0.00;
    private Double subtotalPedido = 0.00;

    private AdapterItemCompra adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_compra_item);
        final TextView txtSubTotalPedido = (TextView) findViewById(R.id.txtSubTotalPedido);
        final TextView txtTotalItem = (TextView) findViewById(R.id.txtTotalItem);
        final EditText edtCusto = (EditText) findViewById(R.id.edtCusto);
        final EditText edtQtde = (EditText) findViewById(R.id.edtQtde);

        //RECEBE O VALOR DAS VARIAVEIS PASSADAS DA PEDIDOCOMPRAACTIVITY
        Intent itPedidoCompra = getIntent();
        codfornecedor = Integer.parseInt(itPedidoCompra.getStringExtra("codfornecedor"));
        descricaofornecedor = itPedidoCompra.getStringExtra("descricaofornecedor");
        email = itPedidoCompra.getStringExtra("email");
        datapedido = itPedidoCompra.getStringExtra("datapedido");
        dataentrega = itPedidoCompra.getStringExtra("dataprevisao");
        formapgto = itPedidoCompra.getStringExtra("formapgto");

        //COMANDO PARA SUPRIMIR O TECLADO AO ABRIR A TELA
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // RECUPERNADO A LISTVIEW DECLARADA NO XML PARA PODER DEFINIR O ADAPTER
        final ListView listagem = (ListView) findViewById(R.id.lstProdutos);

        // RECUPERA O BUTTON ACICIONAR DECLARADO NO XML
        Button btAdicionar = (Button) findViewById(R.id.btAdicionar);
        Button btSalvarPedido = (Button) findViewById(R.id.btSalvarPedido);

        //CRIA O ADAPTER
        adapter = new AdapterItemCompra(this, itens);

        //DEFINE O ADAPTER
        listagem.setAdapter(adapter);
        listagem.setCacheColorHint(Color.TRANSPARENT);

        // DOWNLOAD DO ARQUIVO JSON DE FORMA ASSINCRONA
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
                //VERIFICA SE FOI SELECIONADO ALGUM PRODUTO
                if(descricaoProduto.contains("Selecione")) {
                    Toast.makeText(PedidoCompraItemActivity.this,"Selecione um produto!",Toast.LENGTH_SHORT).show();
                }else{
                    String[] partes = descricaoProduto.split("-");
                    codItem = Integer.parseInt(partes[0]);
                    custo = Double.parseDouble(edtCusto.getText().toString());
                    qtde = Double.parseDouble(edtQtde.getText().toString());
                    total = custo * qtde;
                    resultado = String.format("%.3f", total);
                    resultado = resultado.replace(",", ".");
                    subtotalPedido += total;
                    //FORMATA VALOR PARA 3 DECIMAIS
                    String tot = String.format("%.3f",subtotalPedido);
                    txtSubTotalPedido.setText(tot);

                    //
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

        listagem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PedidoCompraItemActivity.this);

                // SETA UM TITULO PARA O DIALOG
                alertDialogBuilder.setTitle("Excluir");

                // SETA UMA MENSAGEM PARA O DIALOG
                alertDialogBuilder
                        .setMessage("Confirma exclusão do item?")
                        .setCancelable(false)
                        .setPositiveButton("Sim",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                final TextView txtTotalPedido = (TextView) findViewById(R.id.txtSubTotalPedido);
                                String temp = txtTotalPedido.getText().toString();
                                temp = temp.replace(",", ".");
                                Double totPedido = Double.parseDouble(temp.toString());
                                String result = "";
                                subtotalPedido = totPedido;
                                subtotalPedido -= itens.get(position).getTotalItem();
                                result = String.format("%.3f", subtotalPedido);
                                result = result.replace(",", ".");

                                itens.remove(position);
                                adapter.notifyDataSetChanged();

                                txtSubTotalPedido.setText(result);
                                limpaTela();
                            }
                        })
                        .setNegativeButton("Não",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

                // CRIA O DIALOG
                AlertDialog alertDialog = alertDialogBuilder.create();

                // EXIBE O DIALOG
                alertDialog.show();
            }
        });


    }

    protected void onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState){
        View ListView = inflater.inflate(R.layout.item_listagem_pedido, null);
    }

    public void btSalvarPedido(View view) {
        //Grava o Pedido
        PedidoCompraRepositorio pedRep = new PedidoCompraRepositorio(this);
        PedidoCompra pedido = new PedidoCompra();
        pedido.codEmpresa = MainActivity.codEmpresa;
        pedido.codUnNegocio = UnidadeNegocioListFragment.codUnidade;
        pedido.idFornecedor = codfornecedor;
        pedido.email = email;
        pedido.dtPedido = datapedido;
        pedido.dtEntrega = dataentrega;
        pedido.formapgto = formapgto;
        pedido.totalPedido = subtotalPedido;
        pedRep.salvar(pedido);

        if (pedido.get_id() > 0){
            //GRAVAR OS ITENS DO PEDIDO
            for (int i = 0; i < itens.size(); i++) {
                PedidoCompraItemRepositorio itemRep = new PedidoCompraItemRepositorio(this);
                PedidoCompraItem pedItem = new PedidoCompraItem();

                pedItem.idCompra = pedido.get_id();
                pedItem.descricaoItem = itens.get(i).descricaoItem;
                pedItem.idItem = itens.get(i).idItem;
                pedItem.qtdeItem = itens.get(i).qtdeItem;
                pedItem.precoCusto = itens.get(i).precoCusto;
                pedItem.totalItem = itens.get(i).totalItem;

                itemRep.salvar(pedItem);
            }

        }

        limpaTela();
        Toast.makeText(this,"Pedido salvo com sucesso!",Toast.LENGTH_LONG).show();

        // PROGRAMAR A VOLTA PARA A TELA INICIAL DO PEDIDO

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
                        subtotalItem += total;

                        //SETA OS VALORES NOS EDITTEXT
                        edtCusto.setText(custo.toString());
                        edtQtde.setText(qtde.toString());
                        txtTotalItem.setText(subtotalItem.toString());
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

        spnProduto2.setSelection(0);
        edtCusto.setText("0.00");
        edtQtde.setText("1.00");
        txtTotalItem.setText("0.00");
        subtotalItem = 0.00;
    }
}
