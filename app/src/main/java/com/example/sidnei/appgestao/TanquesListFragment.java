package com.example.sidnei.appgestao;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class TanquesListFragment extends Fragment{
    TanquesTask mTask;
    List<Tanque> mTanques;
    ListView mListView;
    TextView mTextMensagem;
    ProgressBar mProgressBar;
    ArrayAdapter<Tanque> mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);    //CHAMADA IMPORTANTE, DESTA FORMA RETEM A INSTANCIA  AO GIRAR A TELA NAO PERDE OS DADOS
        //DESTA FORMA NAO PRECISA BAIXAR NOVAMENTE
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_tanques_list, null);
        mTextMensagem = (TextView)layout.findViewById(android.R.id.empty);
        mProgressBar = (ProgressBar)layout.findViewById(R.id.progressBar);
        mListView = (ListView)layout.findViewById(R.id.list);
        mListView.setEmptyView(mTextMensagem);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mTanques == null) {
            mTanques = new ArrayList<Tanque>();
        }
        mAdapter = new TanquesListAdapter(getActivity(), mTanques);
        //mAdapter = new ArrayAdapter<Livro>(getActivity(),android.R.layout.simple_list_item_1,mLivros);

        mListView.setAdapter(mAdapter);
        if (mTask == null) {
            if (TanqueHttp.temConexao(getActivity())) {
                iniciarDownload();
            } else {
                mTextMensagem.setText("Sem conexão");
            }
        } else if (mTask.getStatus() == AsyncTask.Status.RUNNING) {
            exibirProgress(true);
        }
    }

    private void exibirProgress(boolean exibir) {
        if (exibir) {
            mTextMensagem.setText("Baixando informações dos tanques...");
        }
        mTextMensagem.setVisibility(exibir ? View.VISIBLE : View.GONE);
        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    public void iniciarDownload() {
        if (mTask == null ||  mTask.getStatus() != AsyncTask.Status.RUNNING) {
            mTask = new TanquesTask();
            mTask.execute();
        }
    }

    class TanquesTask extends AsyncTask<Void, Void, List<Tanque>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exibirProgress(true);
        }
        @Override
        protected List<Tanque> doInBackground(Void... strings) {
            return carregarTanquesJson();
            //return LivroHttp.carregarLivrosXml();
        }
        @Override
        protected void onPostExecute(List<Tanque> tanques) {
            super.onPostExecute(tanques);
            exibirProgress(false);
            if (tanques != null) {
                mTanques.clear();
                mTanques.addAll(tanques);
                mAdapter.notifyDataSetChanged();
            } else {
                mTextMensagem.setText("Falha ao obter tanques");
            }
        }

        public  List<Tanque> carregarTanquesJson() {
            try {
                HttpURLConnection conexao = TanqueHttp.connectar();
                int resposta = conexao.getResponseCode();
                if (resposta ==  HttpURLConnection.HTTP_OK) {
                    InputStream is = conexao.getInputStream();
                    JSONObject json = new JSONObject(bytesParaString(is));
                    return lerJsonTanques(json);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public  List<Tanque> lerJsonTanques(JSONObject json) throws JSONException {
            List<Tanque> listaDeTanques = new ArrayList<Tanque>();
            //OBJETO PAI DO JSON
            JSONArray jsonTanque = json.getJSONArray("tanques");
            for (int i = 0; i < jsonTanque.length(); i++) {
                JSONObject jsonTq = jsonTanque.getJSONObject(i);
                Tanque tanque = new Tanque(
                        jsonTq.getInt("numero"),
                        jsonTq.getString("descricao"),
                        jsonTq.getDouble("volumeTotal"),
                        jsonTq.getDouble("volume"),
                        jsonTq.getInt("numero")
                );
                listaDeTanques.add(tanque);
            }
            return listaDeTanques;
        }

        private  String bytesParaString(InputStream is) throws IOException {
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


}