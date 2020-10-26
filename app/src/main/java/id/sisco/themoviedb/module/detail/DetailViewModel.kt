package id.sisco.themoviedb.module.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.sisco.themoviedb.BuildConfig
import id.sisco.themoviedb.model.*
import kotlinx.coroutines.*

class DetailViewModel: ViewModel() {

    private val _detailMovie = MutableLiveData<MovieDetailModel>()
    val detailMovie : LiveData<MovieDetailModel>
        get() = _detailMovie

    private val _detailVideo = MutableLiveData<MovieVideoModel.Result>()
    val detailVideo : LiveData<MovieVideoModel.Result>
        get() = _detailVideo

    private val _detailCast = MutableLiveData<ArrayList<MovieCreditModel.Cast>>()
    val detailCast : LiveData<ArrayList<MovieCreditModel.Cast>>
        get() = _detailCast

    private val _listGenre = MutableLiveData<ArrayList<MovieDetailModel.Genre>>()
    val listGenre : LiveData<ArrayList<MovieDetailModel.Genre>>
        get() = _listGenre

    private val _status = MutableLiveData(ResultStatus(Status.NULL))
    val status : LiveData<ResultStatus>
        get() = _status

    private val _msg = MutableLiveData("")
    val msg : LiveData<String>
        get() = _msg

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun setDetailMovie(id: String) {
        _status.value = ResultStatus(Status.LOADING)
        uiScope.launch {
            getDetailMovie(id)
        }
    }

    private suspend fun getDetailMovie(id: String) {
        withContext(Dispatchers.IO) {
            try {
                val url = BuildConfig.BASE_URL + "3/movie/$id?api_key="+ BuildConfig.API_KEY +"&language=en-US\n"
                val response = DetailRepository.instance.getData(url)
                if (response != null) {
                    _status.postValue(ResultStatus(Status.SUCCESS))
                    _detailMovie.postValue(response)
                    _listGenre.postValue(response.genres)
                    getDetailVideo(id)
                    getDetailCredit(id)
                } else {
                    _status.postValue(ResultStatus(Status.ERROR))
                    _msg.postValue("Terjadi kesalahan saat\n mengambil data")
                }
            } catch (e: Exception) {
                Log.e("Error", e.toString())
                _status.postValue(ResultStatus(Status.ERROR))
                _msg.postValue("Terjadi kesalahan saat\n mengambil data")
            }
        }
    }

    private suspend fun getDetailVideo(id: String) {
        withContext(Dispatchers.IO) {
            try {
                val url = BuildConfig.BASE_URL + "3/movie/$id/videos?api_key="+ BuildConfig.API_KEY +"&language=en-US\n"
                val response = DetailRepository.instance.getDataVideo(url)
                if (response != null) {
                    _detailVideo.postValue(response.results?.get(0))
                } else {
                    _status.postValue(ResultStatus(Status.ERROR))
                    _msg.postValue("Terjadi kesalahan saat\n mengambil data")
                }
            } catch (e: Exception) {
                Log.e("Error", e.toString())
                _status.postValue(ResultStatus(Status.ERROR))
                _msg.postValue("Terjadi kesalahan saat\n mengambil data")
            }
        }
    }

    private suspend fun getDetailCredit(id: String) {
        withContext(Dispatchers.IO) {
            try {
                val url = BuildConfig.BASE_URL + "3/movie/$id/credits?api_key="+ BuildConfig.API_KEY
                val response = DetailRepository.instance.getDataCredit(url)
                if (response != null) {
                    _detailCast.postValue(response.cast)
                } else {
                    _status.postValue(ResultStatus(Status.ERROR))
                    _msg.postValue("Terjadi kesalahan saat\n mengambil data")
                }
            } catch (e: Exception) {
                Log.e("Error", e.toString())
                _status.postValue(ResultStatus(Status.ERROR))
                _msg.postValue("Terjadi kesalahan saat\n mengambil data")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}