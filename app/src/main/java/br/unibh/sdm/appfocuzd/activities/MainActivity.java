package br.unibh.sdm.appfocuzd.activities;

import br.unibh.sdm.appfocuzd.R;
import br.unibh.sdm.appfocuzd.api.RotinaService;
import br.unibh.sdm.appfocuzd.api.RestServiceGenerator;
import br.unibh.sdm.appfocuzd.entidades.Rotina;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RotinaService service = null;
    final private MainActivity mainActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = RestServiceGenerator.createService(RotinaService.class);
        buscaRotinas();
    }

    public void buscaRotinas(){
        RotinaService service = RestServiceGenerator.createService(RotinaService.class);
        Call<List<Rotina>> call = service.getRotina();
        call.enqueue(new Callback<List<Rotina>>() {
            @Override
            public void onResponse(Call<List<Rotina>> call, Response<List<Rotina>> response) {
                if (response.isSuccessful()) {
                    Log.i("RotinaDAO", "Retornou " + response.body().size() + " Rotina!");
                    List<String> lista2 = new ArrayList<String>();
                    for (Rotina item : response.body()) {
                        lista2.add(item.getData().toString());
                    }
                    Log.i("MainActivity", lista2.toArray().toString());
                    ListView listView = findViewById(R.id.listViewListaRotinas);
                    listView.setAdapter(new ArrayAdapter<String>(mainActivity,
                            android.R.layout.simple_list_item_1,
                            lista2));
                } else {
                    Log.e("RotinaDAO", "" + response.message());
                    Toast.makeText(getApplicationContext(), "Erro: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Rotina>> call, Throwable t) {
                Log.e("Error", "" + t.getMessage());
            }
        });
    }

}