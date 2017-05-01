package com.example.sidnei.appgestao.pedidoCompra.Repositorio;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.sidnei.appgestao.classeProduto.Produto;

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

public class ProdutoHttp {
    //public static final String UNNEGOCIO_URL_JSON = "http://sgestao.hol.es/ws/UnNegocioWs.php?codempresa=";
    public static final String UNNEGOCIO_URL_JSON = "http://10.0.2.2:81/ws_sgestao/Json/ProdutoWS.json";
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
    public static List<Produto> carregarProdutoJson() {
        try {

            //HttpURLConnection conexao = connectar(UNNEGOCIO_URL_JSON + empresa);
            HttpURLConnection conexao = connectar(UNNEGOCIO_URL_JSON);
            int resposta = conexao.getResponseCode();
            if (resposta == HttpURLConnection.HTTP_OK) {
                InputStream is = conexao.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(is));
                return lerJsonProduto(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Produto> lerJsonProduto(JSONObject json) throws JSONException {
        List<Produto> listaDeProduto = new ArrayList<Produto>();
        //OBJETO PAI DO JSON
        JSONArray jsonProduto = json.getJSONArray("produtos");
        for (int i = 0; i < jsonProduto.length(); i++) {
            JSONObject jsonProd = jsonProduto.getJSONObject(i);
            Produto produto = new Produto();
            jsonProd.getInt("idproduto");
            jsonProd.getString("produtodescricao");
            jsonProd.getDouble("produtoprecovenda");
            jsonProd.getDouble("produtoprecocusto");

            listaDeProduto.add(produto);
        }
        return listaDeProduto;
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
