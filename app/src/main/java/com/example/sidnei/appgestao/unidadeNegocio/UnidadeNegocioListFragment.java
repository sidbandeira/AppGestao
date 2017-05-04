package com.example.sidnei.appgestao.unidadeNegocio;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sidnei.appgestao.MainActivity;
import com.example.sidnei.appgestao.R;
import com.example.sidnei.appgestao.menu.MenuActivity;

import java.util.ArrayList;
import java.util.List;


public class UnidadeNegocioListFragment extends Fragment implements AdapterView.OnItemClickListener{
    // VARIAVEL PUBLICA PARA PASSAR O CODIGO DA UNIDADE DE NEGOCIO SELECIONADA.
    public static Integer codUnidade = 0;
    UnidadeNegocioTask mTask;
    List<UnidadeNegocio> mUnidadeNegocio;
    ListView mListView;
    TextView mTextMensagem;
    ProgressBar mProgressBar;
    ArrayAdapter<UnidadeNegocio> mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_unidadenegocio_list, null);
        mTextMensagem = (TextView)layout.findViewById(android.R.id.empty);
        mProgressBar = (ProgressBar)layout.findViewById(R.id.progressBar);
        mListView = (ListView)layout.findViewById(R.id.list);
        mListView.setEmptyView(mTextMensagem);
        return layout;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mUnidadeNegocio == null) {
            mUnidadeNegocio = new ArrayList<UnidadeNegocio>();
        }
        mAdapter = new UnNegocioListAdapter(getActivity(), mUnidadeNegocio);
        mListView.setAdapter(mAdapter);
        if (mTask == null) {
            if (UnNegocioHttp.temConexao(getActivity())) {
                iniciarDownload();
            } else {
                mTextMensagem.setText("Sem conexão");
            }
        } else if (mTask.getStatus() == AsyncTask.Status.RUNNING) {
            exibirProgress(true);
        }
        mListView.setOnItemClickListener(this);
    }

    private void exibirProgress(boolean exibir) {
        if (exibir) {
            mTextMensagem.setText("Aguarde baixando informações...");
        }
        mTextMensagem.setVisibility(exibir ? View.VISIBLE : View.GONE);
        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }
    public void iniciarDownload() {
        if (mTask == null || mTask.getStatus() != AsyncTask.Status.RUNNING) {
            mTask = new UnidadeNegocioTask();
            mTask.execute();
        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        UnidadeNegocio unneg = (UnidadeNegocio) adapterView.getItemAtPosition(i);
        codUnidade = unneg.id;
        Intent it = new Intent(getActivity(),MenuActivity.class );
        startActivity(it);
    }

    class UnidadeNegocioTask extends AsyncTask<Void, Void, List<UnidadeNegocio>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exibirProgress(true);
        }
        @Override
        protected List<UnidadeNegocio> doInBackground(Void... strings) {

            return UnNegocioHttp.carregarUnidadeNegocioJson(MainActivity.codEmpresa);
        }
        @Override
        protected void onPostExecute(List<UnidadeNegocio> unidadeNegocios) {
            super.onPostExecute(unidadeNegocios);
            exibirProgress(false);
            if (unidadeNegocios != null) {
                mUnidadeNegocio.clear();
                mUnidadeNegocio.addAll(unidadeNegocios);
                mAdapter.notifyDataSetChanged();
            } else {
                mTextMensagem.setText("Falha ao obter registros");
            }
        }
    }
}
