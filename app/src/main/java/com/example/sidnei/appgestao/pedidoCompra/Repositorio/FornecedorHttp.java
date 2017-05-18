package com.example.sidnei.appgestao.pedidoCompra.Repositorio;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.sidnei.appgestao.Classes.Fornecedor;

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

public class FornecedorHttp {
    public static final String FORNECEDOR_URL_JSON = "http://sgestao.hol.es/ws/FornecedorWs.php?codempresa=";

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

    //FUNCAO VAI RECEBER UMA VARIAVEL INTEIRA COM O CODIGO DA EMPRESA PARA RECARREGAR OS FORNECEDORES CADASTRADOS
    public static List<Fornecedor> carregarFornecedorJson(Integer empresa) {
        try {

            HttpURLConnection conexao = connectar(FORNECEDOR_URL_JSON + empresa);
            int resposta = conexao.getResponseCode();
            if (resposta == HttpURLConnection.HTTP_OK) {
                InputStream is = conexao.getInputStream();
                JSONObject json = new JSONObject(bytesParaString(is));
                return lerJsonFornecedor(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Fornecedor> lerJsonFornecedor(JSONObject json) throws JSONException {
        List<Fornecedor> listaDeFornecedores = new ArrayList<Fornecedor>();
        //OBJETO PAI DO JSON
        JSONArray jsonFornecedores = json.getJSONArray("fornecedores");
        for (int i = 0; i < jsonFornecedores.length(); i++) {
            JSONObject jsonFor = jsonFornecedores.getJSONObject(i);
            Fornecedor fornecedor = new Fornecedor(
                    jsonFor.getInt("id"),
                    jsonFor.getString("razaosocial")
            );
            listaDeFornecedores.add(fornecedor);
        }
        return listaDeFornecedores;
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
