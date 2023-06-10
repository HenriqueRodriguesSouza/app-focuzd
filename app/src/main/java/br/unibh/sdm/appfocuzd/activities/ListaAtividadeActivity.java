package br.unibh.sdm.appfocuzd.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.unibh.sdm.appfocuzd.R;
import br.unibh.sdm.appfocuzd.api.AtividadeService;
import br.unibh.sdm.appfocuzd.api.RestServiceGenerator;
import br.unibh.sdm.appfocuzd.entidades.Atividade;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaAtividadeActivity extends AppCompatActivity {

    private AtividadeService service = null;
    final private ListaAtividadeActivity listaAtividadeActivity = this;
    private final Context context;

    public ListaAtividadeActivity() {
        context = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Lista de Atividades");
        setContentView(R.layout.activity_lista_atividade);
        service = RestServiceGenerator.createService(AtividadeService.class);
        buscaAtividades();
        criaAcaoBotaoFlutuante();
        criaAcaoCliqueLongo();
    }

    private void criaAcaoCliqueLongo() {
        ListView listView = findViewById(R.id.listViewListaAtividades);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ListaAtividadeActivity","Clicou em clique longo na posicao "+position);
                final Atividade objetoSelecionado = (Atividade) parent.getAdapter().getItem(position);
                Log.i("ListaAtividadeActivity", "Selecionou a atividade "+objetoSelecionado.getId());
                new AlertDialog.Builder(parent.getContext()).setTitle("Removendo Atividade")
                        .setMessage("Tem certeza que quer remover a Atividade "+objetoSelecionado.getNomeAtividade()+"?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeAtividade(objetoSelecionado);
                            }
                        }).setNegativeButton("Não", null).show();
                return true;
            }
        });
    }

    private void removeAtividade(Atividade atividade) {
        Log.i("ListaAtividadeActivity","Vai remover atividade "+atividade.getId());
        Call<Boolean> call = this.service.excluiAtividade(atividade.getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Log.i("ListaAtividadeActivity", "Removeu a atividade " + atividade.getNomeAtividade());
                    Toast.makeText(getApplicationContext(), "Removeu a atividade " + atividade.getNomeAtividade(), Toast.LENGTH_LONG).show();
                    onResume();
                } else {
                    Log.e("ListaAtividadeActivity", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("ListaAtividadeActivity", "Erro: " + t.getMessage());
            }
        });
    }

    private void criaAcaoBotaoFlutuante() {
        FloatingActionButton botaoNovo = findViewById(R.id.floatingActionButtonCriar);
        botaoNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ListaAtividadeActivity","Clicou no botão para adicionar Nova atividade");
                startActivity(new Intent(ListaAtividadeActivity.this,
                        FormularioAtividadeActivity.class));
            }
        });
    }

    public void buscaAtividades(){
        AtividadeService service = RestServiceGenerator.createService(AtividadeService.class);
        Call<List<Atividade>> call = service.getAtividade();
        call.enqueue(new Callback<List<Atividade>>() {
            @Override
            public void onResponse(Call<List<Atividade>> call, Response<List<Atividade>> response) {
                if (response.isSuccessful()) {
                    Log.i("ListaAtiidadeActivity", "Retornou " + response.body().size() + " Atividades!");
                    ListView listView = findViewById(R.id.listViewListaAtividades);
                    listView.setAdapter(new ListaAtividadeAdapter(context,response.body()));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.i("ListaAtividadeActivity", "Selecionou o objeto de posicao "+position);
                            Atividade objetoSelecionado = (Atividade) parent.getAdapter().getItem(position);
                            Log.i("ListaAtividadeActivity", "Selecionou a atividade "+objetoSelecionado.getNomeAtividade());
                            Log.i("ListaAtividadeActivity", "Selecionou a atividade "+objetoSelecionado.getId());
                            Intent intent = new Intent(ListaAtividadeActivity.this, FormularioAtividadeActivity.class);
                            intent.putExtra("objeto", objetoSelecionado);
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.e("AtividadeDAO", "" + response.message());
                    Toast.makeText(getApplicationContext(), "Erro: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Atividade>> call, Throwable t) {
                Log.e("Error", "" + t.getMessage());
            }
        });
    }

    @Override
            protected void onResume() {
            super.onResume();
            buscaAtividades();
            }
}
