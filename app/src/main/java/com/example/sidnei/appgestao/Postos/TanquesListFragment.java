package com.example.sidnei.appgestao.Postos;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sidnei.appgestao.Classes.Tanque;
import com.example.sidnei.appgestao.R;
import com.example.sidnei.appgestao.unidadeNegocio.UnidadeNegocioListFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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
                HttpURLConnection conexao = TanqueHttp.connectar(UnidadeNegocioListFragment.codUnidade);
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
                Double capacidade = 0.00;
                Double estoque = 0.00;
                Double percent = 0.00;
                JSONObject jsonTq = jsonTanque.getJSONObject(i);
                Tanque tanque = new Tanque();
                capacidade = jsonTq.getDouble("volumetotal");
                estoque = jsonTq.getDouble("volume");
                percent = (100 * estoque)/capacidade;

                tanque.setCodTanque(jsonTq.getInt("codintegracao"));
                tanque.setTipoCombustivel(jsonTq.getString("descricao"));
                tanque.setCapacidadeTanque(jsonTq.getDouble("volumetotal"));
                tanque.setEstoqueTanque(jsonTq.getDouble("volume"));
                tanque.setCodImagem(RetornaCodImagem(percent));

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

    // RETORNA O CODIGO DA IMAGEM A SER  SETADA.
    private static Integer RetornaCodImagem(Double percent){
        Integer cod = 0;

        if(percent < 15.0){
            //<item>@drawable/tqvazio</item>
            cod = 0;
        }else{
            if(percent < 30){
                //<item>@drawable/tqmedio</item>
                cod = 1;
            }else{
                if(percent < 75){
                    //<item>@drawable/tqnormal</item>
                    cod =2;
                }else{
                    //<item>@drawable/tqcheio</item>
                    cod =3;
                }
            }
        }
        return cod;
    }
}




