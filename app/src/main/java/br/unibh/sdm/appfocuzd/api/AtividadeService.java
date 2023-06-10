package br.unibh.sdm.appfocuzd.api;

import java.util.List;

import br.unibh.sdm.appfocuzd.entidades.Atividade;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AtividadeService {

    @Headers({
            "Accept: application/json",
            "User-Agent: AppAtividade"
    })
    @GET("atividade")
    Call<List<Atividade>> getAtividade();

    @GET("atividade/{id}")
    Call<Atividade> getAtividade(@Path("id") Long id);

    @POST("atividade")
    Call<Atividade> criaAtividade(@Body Atividade atividade);

    @PUT("atividade/{id}")
    Call<Atividade> atualizaAtividade(@Path("id") Long id, @Body Atividade atividade);

    @DELETE("atividade/{id}")
    Call<Boolean> excluiAtividade(@Path("id") Long id);

}