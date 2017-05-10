package com.example.sidnei.appgestao.estoque;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.sidnei.appgestao.Classes.Produto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProdHttp {
    //public static final String PRODUTO_URL_JSON = "http://sgestao.hol.es/Json/ProdutoWS.json";
    public static final String PRODUTO_URL_JSON = "http://sgestao.hol.es/ws/ProdutoWs.php?codempresa=";

    private static HttpURLConnection connectar(String urlArquivo) throws IOException {
        final int SEGUNDOS = 1000;
        URL url = new URL(urlArquivo);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setReadTimeout(10 * SEGUNDOS);
        conexao.setConnectTimeout(15 * SEGUNDOS);
        conexao.setRequestMethod("GET");
        conexao.setDoInput(true);
        conexao.setDoOutput(false);
        conexao.connect();
        return conexao;
    }

    public static boolean temConexao(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    //FUNCAO VAI RECEBER UMA VARIAVEL INTEIRA COM O CODIGO DA EMPRESA PARA RECARREGAR AS UNIDADES
    // DE NEGOCIO SEMPRE QUE O USUARIO EFETUAR O LOGIN
    public static List<Produto> carregarProdutoJson(Integer empresa) {
        try {

            HttpURLConnection conexao = connectar(PRODUTO_URL_JSON + empresa);
            //HttpURLConnection conexao = connectar(PRODUTO_URL_JSON);
            int resposta = conexao.getResponseCode();
            if (resposta == HttpURLConnection.HTTP_OK) {
                InputStream is = conexao.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(is));
                return lerJsonProd(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Produto> lerJsonProd(JSONObject json) throws JSONException {
        List<Produto> listadeProduto = new ArrayList<Produto>();
        //OBJETO PAI DO JSON
        JSONArray jsonProd = json.getJSONArray("produtos");
        for (int i = 0; i < jsonProd.length(); i++) {
            JSONObject jsonProduto = jsonProd.getJSONObject(i);
            Produto prod = new Produto(
                    jsonProduto.getInt("idproduto"),
                    jsonProduto.getString("produtodescricao"),
                    jsonProduto.getDouble("produtoprecovenda"),
                    jsonProduto.getDouble("produtoprecocusto"),
                    jsonProduto.getString("produtodtultimacompra")
            );
            listadeProduto.add(prod);
        }
        return listadeProduto;
    }

    private static String bytesParaString(InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        // O bufferzao vai armazenar todos os bytes lidos
        ByteArrayOutputStream bufferzao = new ByteArrayOutputStream();
        // precisamos saber quantos bytes foram lidos
        int bytesLidos;
        // Vamos lendo de 1KB por vez...
        while ((bytesLidos = is.read(buffer)) != -1) {
            // copiando a quantidade de bytes lidos do buffer para o bufferzão
            bufferzao.write(buffer, 0, bytesLidos);
        }
        return new String(bufferzao.toByteArray(), "UTF-8");
    }

}
