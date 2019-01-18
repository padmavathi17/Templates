package kotlinbigboss.infoappmarkers.com.kotlinbigboss.restcall

import com.sample.kotlintemplates.Model.Pojo
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

/**
 * ApiInterface||Retrofit provides with a list of annotations for each of the HTTP methods:
 * @GET, @POST, @PUT, @DELETE, @PATCH or @HEAD
 */
interface ApiInterface {
    @GET("/bigboss/bigboss.json")
    fun namesAPI() : Call<Pojo>

    @GET("/bigboss/bigboss.json")
    fun dataApi(): Observable<Pojo>


}