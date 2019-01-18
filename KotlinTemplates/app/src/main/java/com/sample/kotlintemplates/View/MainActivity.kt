package com.sample.kotlintemplates.View

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import com.sample.kotlintemplates.Helper.CustomAdapter
import com.sample.kotlintemplates.MainApp
import com.sample.kotlintemplates.Model.Pojo
import com.sample.kotlintemplates.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinbigboss.infoappmarkers.com.kotlinbigboss.restcall.ApiClient
import kotlinbigboss.infoappmarkers.com.kotlinbigboss.restcall.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    var recyclerView: RecyclerView? = null
    /*We initialise a CompositeDisposable object and add all the RxJava disposable to it.
     Previously it was CompositeSubscription in RxJava 1 which is replaced by CompositeDisposable.
     During onDestroy() we can clear the disposables so that it prevents the memory leak.*/
    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        // loadJsonwithoutRxjava()
        loadJsonwithRxjava()

    }

    private fun initRecyclerView() {

        recyclerView = findViewById(R.id.datalist) as RecyclerView
        recyclerView!!.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)

    }

    /*
    loading data from server using Retrofit and gson
     */
    fun loadJsonwithoutRxjava() {
        val request: Call<Pojo> = ApiClient.service.namesAPI()

        request.enqueue(object : Callback<Pojo> {
            override fun onFailure(call: Call<Pojo>, t: Throwable) {
                Log.d("MainActivity", "on Failure Triggered")
            }

            override fun onResponse(call: Call<Pojo>, response: Response<Pojo>) {
                Log.d("MainActivity", "on Response Triggered")
                val list: Pojo? = response.body();
                if (list != null) {
                    Log.d("MainActivity", "Data ${list.intro}")
                    //creating our adapter
                    val adapter = CustomAdapter(list.intro, this@MainActivity)
                    //now adding the adapter to recyclerview
                    recyclerView?.adapter = adapter
                }
            }
        })
    }


    fun loadJsonwithRxjava() {
        /*val requestInterface: ApiInterface = Retrofit.Builder()
                .baseUrl(MainApp.Base_Url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiInterface::class.java)*/

        mCompositeDisposable.add(ApiClient.service.dataApi()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));


    }

    private fun handleResponse(list: Pojo) {
        //creating our adapter
        val adapter = CustomAdapter(list.intro, this@MainActivity)
        //now adding the adapter to recyclerview
        recyclerView?.adapter = adapter
    }

    private fun handleError(error: Throwable) {

        Toast.makeText(this, "Error " + error.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    public override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
    }

    override fun isDestroyed(): Boolean {
        return super.isDestroyed()
        mCompositeDisposable.dispose()
    }
}

