package id.sisco.themoviedb.module.main

import android.util.Log
import com.google.gson.GsonBuilder
import id.sisco.themoviedb.model.MovieModel
import id.sisco.themoviedb.network.BaseApi
import java.io.IOException


class MainRepository {

    suspend fun getData(url: String): MovieModel? {
        val response = BaseApi().api().listMovie(url)
        return if (response.code() == 200) {
            response.body()
        } else {
            val gson = GsonBuilder().create()
            var mError: MovieModel? = null
            try {
                mError =
                    gson.fromJson(response.errorBody()!!.string(), MovieModel::class.java)
            } catch (e: IOException) {
                Log.e("error", "Error")
            }
            mError
        }
    }

    companion object {
        val instance by lazy { MainRepository() }
    }
}