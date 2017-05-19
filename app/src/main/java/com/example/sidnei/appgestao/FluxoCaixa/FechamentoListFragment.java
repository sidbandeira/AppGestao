package com.example.sidnei.appgestao.FluxoCaixa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sidnei.appgestao.Classes.Venda;
import com.example.sidnei.appgestao.R;
import com.example.sidnei.appgestao.unidadeNegocio.UnidadeNegocioListFragment;

import java.util.ArrayList;
import java.util.List;

public class FechamentoListFragment extends Fragment implements AdapterView.OnItemClickListener {

    VendaTask mTask;
    List<Venda> mVenda;
    ListView mListView;
    TextView mTextMensagem;
    ProgressBar mProgressBar;
    ArrayAdapter<Venda> mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_fechamento_list, null);
        mTextMensagem = (TextView)layout.findViewById(android.R.id.empty);
        mProgressBar = (ProgressBar)layout.findViewById(R.id.progressBar);
        mListView = (ListView)layout.findViewById(R.id.list);
        mListView.setEmptyView(mTextMensagem);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mVenda == null) {
            mVenda = new ArrayList<Venda>();
        }
        mAdapter = new FechamentoAdapter(getActivity(), mVenda);
        mListView.setAdapter(mAdapter);
        if (mTask == null) {
            if (FechamentoHttp.temConexao(getActivity())) {
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
            mTask = new VendaTask();
            mTask.execute();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Venda venda = (Venda) adapterView.getItemAtPosition(i);
        //Intent it = new Intent(getActivity(),ProdDetalheActivity.class );
        //it.putExtra( "descricao" , pro.produtoDescricao );
        //it.putExtra( "codBarras" , pro.produtoCodBarras );
        //it.putExtra( "precoCusto" , pro.produtoPrecoCusto.toString() );
        //it.putExtra( "precoVenda" , pro.produtoPrecoVenda.toString() );
        //it.putExtra( "saldo" , pro.produtoSaldo.toString() );

        //startActivity(it);
    }

    class VendaTask extends AsyncTask<Void, Void, List<Venda>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exibirProgress(true);
        }
        @Override
        protected List<Venda> doInBackground(Void... strings) {
            return FechamentoHttp.carregarVendaJson(UnidadeNegocioListFragment.codUnidade,FechamentoPesquisaActivity.dataFormatadaBanco);
        }

        @Override
        protected void onPostExecute(List<Venda> vendas) {
            super.onPostExecute(vendas);
            exibirProgress(false);
            if (vendas != null) {
                mVenda.clear();
                mVenda.addAll(vendas);
                mAdapter.notifyDataSetChanged();
            } else {
                mTextMensagem.setText("Falha ao obter registros");
            }
        }
    }
}
