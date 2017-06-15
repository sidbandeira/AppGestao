package com.example.sidnei.appgestao.pedidoCompra;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sidnei.appgestao.Classes.PedidoCompra;
import com.example.sidnei.appgestao.R;
import com.example.sidnei.appgestao.pedidoCompra.Adaptadores.AdapterListaPedido;
import com.example.sidnei.appgestao.pedidoCompra.Repositorio.PedidoCompraRepositorio;
import com.example.sidnei.appgestao.unidadeNegocio.UnidadeNegocioListFragment;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ListaPedidoCompraActivity extends AppCompatActivity {
    private ListView listaPedidoCompra;
    List<PedidoCompra> pedido = new ArrayList<PedidoCompra>();
    private String codPedido;
    private String nomeFornecedor;
    private String emailFornecedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedido_compra);
        listaPedidoCompra = (ListView)findViewById(R.id.listaPedidoCompra);

        listaPedidoCompra.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


//                PedidoCompraItemRepositorio repItem = new PedidoCompraItemRepositorio(ListaPedidoCompraActivity.this);
//                repItem.excluir(3);

                PedidoCompraRepositorio rep = new PedidoCompraRepositorio(ListaPedidoCompraActivity.this);
                Cursor cursor =  rep.CarregaPedido((int) pedido.get(position)._id);

                if (cursor.getCount()> 0){
                    if (cursor.moveToFirst()) {
                        StringBuffer texto = new StringBuffer();
                        // VARIAVEIS PARA GRAVAR O NOME DO ARQUIVO COM O PEDIDO
                        codPedido = cursor.getString(cursor.getColumnIndexOrThrow("idcompra"));
                        nomeFornecedor =cursor.getString(cursor.getColumnIndexOrThrow("descricaofornecedor"));
                        emailFornecedor = cursor.getString(cursor.getColumnIndexOrThrow("email"));

                        // MONTA O CABEÇALHO DO PEDIDO
                        texto.append("                     PEDIDO DE COMPRA                  " + "\n");
                        texto.append("------------------------------------------------------------" + "\n");
                        texto.append("EMPRESA: " + String.valueOf(UnidadeNegocioListFragment.nomeUnidade.toString()) + "\n\n");


                        texto.append("FORNECEDOR: " + cursor.getString(cursor.getColumnIndexOrThrow("descricaofornecedor")) + "\n");
                        texto.append("DATA PEDIDO: " + cursor.getString(cursor.getColumnIndexOrThrow("dtpedido")) +
                                     "    ENTREGA: " + cursor.getString(cursor.getColumnIndexOrThrow("dtentrega"))  + "\n");
                        texto.append("FORMA PAGTO: " + cursor.getString(cursor.getColumnIndexOrThrow("formapgto")) + "\n");
                        texto.append("VALOR PEDIDO R$: " + cursor.getString(cursor.getColumnIndexOrThrow("totalpedido")) + "\n\n\n");

                        // MONTA OS ITENS DO PEDIDO
                        texto.append("                      ITENS DO PEDIDO                   " + "\n");
                        texto.append("------------------------------------------------------------" + "\n");
                        while (!cursor.isAfterLast()) {
                            texto.append(cursor.getString(cursor.getColumnIndexOrThrow("descricaoitem")) + "\n");
                            texto.append("    QTDE: " + cursor.getString(cursor.getColumnIndexOrThrow("qtdeitem")) +
                                         " VALOR UNIT. R$: " + cursor.getString(cursor.getColumnIndexOrThrow("precocusto")) +
                                         " TOTAL R$: " + cursor.getString(cursor.getColumnIndexOrThrow("precocusto"))+ "\n\n");

                            cursor.moveToNext();
                        }
                        texto.append("------------------------------------------------------------" + "\n");

                        try {
                            salvarExterno(texto.toString() ,codPedido + "_" + nomeFornecedor  + ".txt");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        PedidoCompraRepositorio rep = new PedidoCompraRepositorio(this);
        Cursor cursor = rep.carregaDados();
        if (cursor.getCount()> 0){
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    PedidoCompra ped = new PedidoCompra();
                    ped._id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                    ped.descricaofornecedor = cursor.getString(cursor.getColumnIndexOrThrow("descricaofornecedor"));
                    ped.dtEntrega = cursor.getString(cursor.getColumnIndexOrThrow("dtentrega"));
                    ped.totalPedido = cursor.getDouble(cursor.getColumnIndexOrThrow("totalpedido"));
                    pedido.add(ped);
                    cursor.moveToNext();
                }
            }
        }
        cursor.close();
        listaPedidoCompra.setAdapter(new AdapterListaPedido(this, (ArrayList<PedidoCompra>) pedido));

    }

    // ####### MANUPULACAO DO ARQUIVO TEXTO #########

    //Função: salvarExterno(Text) - Irá salvar o texto na memória Externa
    public String salvarExterno(String dados, String nomeArquivo) throws IOException {
        //Tratativa para memória externa
        String status = Environment.getExternalStorageState();

        //Verifica se está montado o SD Card
        if( !status.equals(Environment.MEDIA_MOUNTED)){
            throw new IOException("O SD Card não montado ou não disponível!!!");
        }
        //Em caso de montado, irá pegar o diretorio padrão
        File dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(dir, nomeArquivo); //Criar arquivo ou reutilizar
        file.getParentFile().mkdirs();
        PrintWriter pw = new PrintWriter(file);	//Funcao para escrita

        try{
            pw.print(dados);
            return file.getPath();
        }finally {
            pw.close();
        }


    }
}
