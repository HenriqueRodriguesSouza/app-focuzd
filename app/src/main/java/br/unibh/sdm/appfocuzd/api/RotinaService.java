package br.unibh.sdm.appfocuzd.api;

import java.util.List;

import br.unibh.sdm.appfocuzd.entidades.Rotina;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RotinaService {

    @Headers({
            "Accept: application/json",
            "User-Agent: AppRotina"
    })
    @GET("rotina")
    Call<List<Rotina>> getRotina();

    @GET("rotina/{id}")
    Call<Rotina> getRotina(@Path("id") String id);

    @POST("rotina")
    Call<Rotina> criaRotina(@Body Rotina rotina);

    @PUT("rotina/{id}")
    Call<Rotina> atualizaRotina(@Path("id") String id, @Body Rotina rotina);

    @DELETE("rotina/{id}")
    Call<Boolean> excluiRotina(@Path("id") String id);

}