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
import android.widget.Toast;

import com.example.sidnei.appgestao.MainActivity;
import com.example.sidnei.appgestao.R;
import com.example.sidnei.appgestao.pedidoCompra.Classes.Fornecedor;
import com.example.sidnei.appgestao.utilitario.JSON;
import com.example.sidnei.appgestao.utilitario.Mascara;
import com.example.sidnei.appgestao.utilitario.Validacoes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PedidoCompraActivity extends AppCompatActivity {
    public static Integer codFornecedor = 0;
    public static String descricaofornecedor = "";
    public static String email = "";
    public static String datapedido = "";
    public static String dataentrega = "";
    public static String formapgto = "";

    private String dataFormatada;

    JSONObject jsonobject;
    JSONArray jsonarray;
    private Button btnProdutos;
    private EditText edtEmail;
    private EditText edtData;
    private EditText edtDataEntrega;
    ArrayList<String> fornecedorlist;
    ArrayList<Fornecedor> fornecedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_compra);
        //FORMATA E SETA A DATA DO DIA
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dataFormatada = sdf.format(date);

        edtEmail = (EditText) findViewById(R.id.edtEmail);

        edtData = (EditText) findViewById(R.id.edtData);
        edtData.addTextChangedListener(Mascara.insert(Mascara.MaskType.DATA,  edtData));
        edtData.setText(dataFormatada);

        edtDataEntrega = (EditText) findViewById(R.id.edtDataEntrega);
        edtDataEntrega.addTextChangedListener(Mascara.insert(Mascara.MaskType.DATA,  edtDataEntrega));
        edtDataEntrega.setText(dataFormatada);

        btnProdutos = (Button) findViewById(R.id.btnProdutos);

        new DownloadJSON().execute();
    }

    public void AdicionarProduto(View view) {
        email = edtEmail.getText().toString();
        dataentrega = edtDataEntrega.getText().toString();

        //VALIDA FORNECEDOR
        if(descricaofornecedor.toString().contains("Selecione") || descricaofornecedor.toString().equals("")){
            Toast.makeText(this,"Selecione um fornecedor!",Toast.LENGTH_LONG).show();
        }else{
            //VALIDA EMAIL FORNECEDOR
            if(email.isEmpty()){
                Toast.makeText(this,"Informe o e-mail do fornecedor!",Toast.LENGTH_LONG).show();
                edtEmail.requestFocus();
            }else {
                //VALIDA DATA DE EMISSAO DO PEDIDO
                if (Validacoes.validaData(edtData.getText().toString(), "dd/MM/yy") == false) {
                    Toast.makeText(this, "Informe uma data de pedido válida!", Toast.LENGTH_LONG).show();
                    edtData.requestFocus();
                } else {
                    //VALIDA DATA DE ENTREGA DO PEDIDO
                    if (Validacoes.validaData(edtDataEntrega.getText().toString(), "dd/MM/yy") == false) {
                        Toast.makeText(this, "Informe uma data de entrega válida!", Toast.LENGTH_LONG).show();
                        edtDataEntrega.requestFocus();
                    } else {
                        //PASSA OS VALORES DA TELA DE PEDIDO PARA A TELA DE ITENS DO PEDIDO
                        Intent it = new Intent(this, PedidoCompraItemActivity.class);
                        it.putExtra("codfornecedor", codFornecedor.toString());
                        it.putExtra("descricaofornecedor", descricaofornecedor);
                        it.putExtra("email", email);
                        it.putExtra("datapedido", datapedido);
                        it.putExtra("dataentrega", dataentrega);
                        it.putExtra("formapgto", formapgto);
                        startActivityForResult(it, 1);
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String retorno = "";
        switch (requestCode) {
            case 1:
                retorno = data.getExtras().getString("PARAM_ACTIVITY2");

                if(retorno.equals("true")){
                    limpaTela();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Download JSON file AsyncTask
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Localiza a classe Fornecedor
            fornecedor = new ArrayList<Fornecedor>();
            // Cria o Array para popular o  spinner
            fornecedorlist = new ArrayList<String>();

            // CHAMA A CLASSE JSON E PASSA A URL PARA BAIXAR O ARQUIVO COM OS FORNECEDORES NO FORMATO JSON
            jsonobject = JSON.getJSONfromURL("http://sgestao.hol.es/ws/FornecedorWs.php?codempresa="+ MainActivity.codEmpresa);
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
            spnFornecedor.setAdapter(new ArrayAdapter<String>(PedidoCompraActivity.this,android.R.layout.simple_spinner_dropdown_item, fornecedorlist));
            spnFornecedor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    // METODO PARA SETAR O FOCO NO SPINNER AO ENTRAR NA TELA.
                    arg0.post(new Runnable() {
                        @Override
                        public void run() {spnFornecedor.requestFocusFromTouch();}
                    });
                    String spfornecedor = fornecedorlist.get(position).toString();;
                    String[] partes = spfornecedor.split("-");
                    if (position != 0){
                        codFornecedor = Integer.parseInt(partes[0]);
                        descricaofornecedor = partes[1];
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });

            //SPINNER PARA LISTAR AS FORMAS DE PAGAMENTO DO PEDIDO
            final Spinner spnFormaPgto = (Spinner) findViewById(R.id.spnFormaPgto);
            ArrayAdapter adapter = ArrayAdapter.createFromResource( PedidoCompraActivity.this, R.array.formapagamento , android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnFormaPgto.setAdapter(adapter);

            spnFormaPgto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    formapgto = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            datapedido = edtData.getText().toString();
        }
    }

    // METODO RESPONSAVEL POR LIMPAR OS DADOS DA TELA.
    public void limpaTela(){
        descricaofornecedor = "";
        email = "";
        dataentrega = "";
        datapedido = "";
        formapgto = "";
        codFornecedor = 0;

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtData = (EditText) findViewById(R.id.edtData);
        edtData.addTextChangedListener(Mascara.insert(Mascara.MaskType.DATA,  edtData));
        edtData.setText(dataFormatada);
        edtDataEntrega = (EditText) findViewById(R.id.edtDataEntrega);
        edtDataEntrega.addTextChangedListener(Mascara.insert(Mascara.MaskType.DATA,  edtDataEntrega));
        edtDataEntrega.setText(dataFormatada);
        edtEmail.setText("");

        final Spinner spnFormaPgto = (Spinner) findViewById(R.id.spnFormaPgto);
        final Spinner spnFornecedor = (Spinner) findViewById(R.id.spnFornecedor);
        spnFornecedor.setSelection(0);
        spnFormaPgto.setSelection(0);
    }
}
