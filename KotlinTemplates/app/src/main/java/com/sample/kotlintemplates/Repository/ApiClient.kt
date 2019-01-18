package kotlinbigboss.infoappmarkers.com.kotlinbigboss.restcall

import com.sample.kotlintemplates.MainApp
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This class for retrieving the retrofit obj
 *
 * To use RxJava Observable along with Retrofit, we need to use a new Adapter Factory implementation.
 * It is done by using addCallAdapterFactory() method.
 *
 *
 */
class ApiClient {

    companion object {
        val retrofit = Retrofit.Builder()
                .baseUrl(MainApp.Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create<ApiInterface>(ApiInterface::class.java)
    }
}