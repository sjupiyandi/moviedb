package id.sisco.themoviedb.network

import id.sisco.themoviedb.model.MovieCreditModel
import id.sisco.themoviedb.model.MovieDetailModel
import id.sisco.themoviedb.model.MovieModel
import id.sisco.themoviedb.model.MovieVideoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface ApiRoute {
    @GET
    suspend fun listMovie( @Url url: String): Response<MovieModel>

    @GET
    suspend fun detailMovie( @Url url: String): Response<MovieDetailModel>

    @GET
    suspend fun detailVideo( @Url url: String): Response<MovieVideoModel>

    @GET
    suspend fun detailCredit( @Url url: String): Response<MovieCreditModel>

}