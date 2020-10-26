package id.sisco.themoviedb.module.detail

import android.util.Log
import com.google.gson.GsonBuilder
import id.sisco.themoviedb.model.MovieCreditModel
import id.sisco.themoviedb.model.MovieDetailModel
import id.sisco.themoviedb.model.MovieVideoModel
import id.sisco.themoviedb.network.BaseApi
import java.io.IOException


class DetailRepository {

    suspend fun getData(url: String): MovieDetailModel? {
        val response = BaseApi().api().detailMovie(url)
        return if (response.code() == 200) {
            response.body()
        } else {
            val gson = GsonBuilder().create()
            var mError: MovieDetailModel? = null
            try {
                mError =
                    gson.fromJson(response.errorBody()!!.string(), MovieDetailModel::class.java)
            } catch (e: IOException) {
                Log.e("error", "Error")
            }
            mError
        }
    }

    suspend fun getDataVideo(url: String): MovieVideoModel? {
        val response = BaseApi().api().detailVideo(url)
        return if (response.code() == 200) {
            response.body()
        } else {
            val gson = GsonBuilder().create()
            var mError: MovieVideoModel? = null
            try {
                mError =
                    gson.fromJson(response.errorBody()!!.string(), MovieVideoModel::class.java)
            } catch (e: IOException) {
                Log.e("error", "Error")
            }
            mError
        }
    }

    suspend fun getDataCredit(url: String): MovieCreditModel? {
        val response = BaseApi().api().detailCredit(url)
        return if (response.code() == 200) {
            response.body()
        } else {
            val gson = GsonBuilder().create()
            var mError: MovieCreditModel? = null
            try {
                mError =
                    gson.fromJson(response.errorBody()!!.string(), MovieCreditModel::class.java)
            } catch (e: IOException) {
                Log.e("error", "Error")
            }
            mError
        }
    }

    companion object {
        val instance by lazy { DetailRepository() }
    }
}