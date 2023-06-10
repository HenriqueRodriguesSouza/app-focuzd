package br.unibh.sdm.appfocuzd.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import br.unibh.sdm.appfocuzd.R;
import br.unibh.sdm.appfocuzd.api.AtividadeService;
import br.unibh.sdm.appfocuzd.api.RestServiceGenerator;
import br.unibh.sdm.appfocuzd.entidades.Atividade;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class FormularioAtividadeActivity extends AppCompatActivity {

    private AtividadeService service = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_atividade);
        setTitle("Edição de Atividade");
        service = RestServiceGenerator.createService(AtividadeService.class);
        configuraBotaoSalvar();
        inicializaObjeto();
    }

    private void inicializaObjeto() {
        Intent intent = getIntent();
        if (intent.getSerializableExtra("objeto") != null) {
            Atividade objeto = (Atividade) intent.getSerializableExtra("objeto");
            EditText nomeAtividade = findViewById(R.id.editTextNomeAtividade);
            EditText diaSemana = findViewById(R.id.editTextDiaSemana);
            EditText horaInicio = findViewById(R.id.editTextHoraInicio);
            EditText horaTermino = findViewById(R.id.editTextHoraTermino);
            EditText repete = findViewById(R.id.editTextRepete);
            nomeAtividade.setText(objeto.getNomeAtividade());
            diaSemana.setText(objeto.getDiaSemana());
            horaInicio.setText(objeto.getHoraInicio());
            horaTermino.setText(objeto.getHoraTermino());
            repete.setText(objeto.getRepete());
            Button botaoSalvar = findViewById(R.id.buttonSalvar);
            botaoSalvar.setText("Atualizar");
        }
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.buttonSalvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("FormularioAtividade","Clicou em Salvar");
                Atividade atividade = recuperaInformacoesFormulario();
                Intent intent = getIntent();
                if (intent.getSerializableExtra("objeto") != null) {
                    Atividade objeto = (Atividade) intent.getSerializableExtra("objeto");
                    atividade.setId(objeto.getId());
                    if (validaFormulario(atividade)) {
                        atualizaAtividade(atividade);
                    }
                } else {
                    if (validaFormulario(atividade)) {
                        salvaAtividade(atividade);
                    }
                }
            }
        });
    }

    private boolean validaFormulario(Atividade atividade){
        boolean valido = true;
        EditText nomeAtividade = findViewById(R.id.editTextNomeAtividade);
        EditText diaSemana = findViewById(R.id.editTextDiaSemana);
        EditText horaInicio = findViewById(R.id.editTextHoraInicio);
        EditText horaTermino = findViewById(R.id.editTextHoraTermino);
        EditText repete = findViewById(R.id.editTextRepete);
        if (atividade.getNomeAtividade() == null || atividade.getNomeAtividade().trim().length() == 0){
            nomeAtividade.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            valido = false;
        } else {
            nomeAtividade.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        }
        if (atividade.getDiaSemana() == null || atividade.getDiaSemana().trim().length() == 0){
            diaSemana.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            valido = false;
        } else {
            diaSemana.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        }
        if (atividade.getHoraInicio() == null || atividade.getHoraInicio().trim().length() == 0){
            horaInicio.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            valido = false;
        } else {
            horaInicio.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        }
        if (atividade.getHoraTermino() == null || atividade.getHoraTermino().trim().length() == 0){
            horaTermino.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            valido = false;
        } else {
            horaTermino.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        }
        if (atividade.getRepete() == null || atividade.getRepete().trim().length() == 0){
            repete.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            valido = false;
        } else {
            repete.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        }
        if (!valido){
            Log.e("FormularioAtividade", "Favor verificar os campos destacados");
            Toast.makeText(getApplicationContext(), "Favor verificar os campos destacados", Toast.LENGTH_LONG).show();
        }
        return valido;
    }

    private void salvaAtividade(Atividade atividade) {
        Call<Atividade> call = service.criaAtividade(atividade);
        call.enqueue(new Callback<Atividade>() {
            @Override
            public void onResponse(Call<Atividade> call, Response<Atividade> response) {
                if (response.isSuccessful()) {
                    Log.i("FormularioAtividade", "Salvou a Atividade "+ atividade.getNomeAtividade());
                    Toast.makeText(getApplicationContext(), "Salvou a Atividade "+ atividade.getNomeAtividade(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("FormularioAtividade", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Atividade> call, Throwable t) {
                Log.e("FormularioAtividade", "Erro: " + t.getMessage());
            }
        });
    }

    private void atualizaAtividade(Atividade atividade) {
        Log.i("FormularioAtividade","Vai atualizar atividade "+atividade.getId());
        Call<Atividade> call = service.atualizaAtividade(atividade.getId(), atividade);
        call.enqueue(new Callback<Atividade>() {
            @Override
            public void onResponse(Call<Atividade> call, Response<Atividade> response) {
                if (response.isSuccessful()) {
                    Log.i("FormularioAtividade", "Atualizou a Atividade " + atividade.getNomeAtividade());
                    Toast.makeText(getApplicationContext(), "Atualizou a Atividade " + atividade.getNomeAtividade(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("FormularioAtividade", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Atividade> call, Throwable t) {
                Log.e("FormularioAtividade", "Erro: " + t.getMessage());
            }
        });
    }

    @NotNull
    private Atividade recuperaInformacoesFormulario() {
        EditText nomeAtividade = findViewById(R.id.editTextNomeAtividade);
        EditText diaSemana = findViewById(R.id.editTextDiaSemana);
        EditText horaInicio = findViewById(R.id.editTextHoraInicio);
        EditText horaTermino = findViewById(R.id.editTextHoraTermino);
        EditText repete = findViewById(R.id.editTextRepete);
        Atividade atividade = new Atividade();
        atividade.setNomeAtividade(nomeAtividade.getText().toString());
        atividade.setDiaSemana(diaSemana.getText().toString());
        atividade.setHoraInicio(horaInicio.getText().toString());
        atividade.setHoraTermino(horaTermino.getText().toString());
        atividade.setRepete(repete.getText().toString());
        return atividade;
    }
}