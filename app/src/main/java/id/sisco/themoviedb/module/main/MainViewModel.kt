package id.sisco.themoviedb.module.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.sisco.themoviedb.BuildConfig
import id.sisco.themoviedb.model.MovieModel
import id.sisco.themoviedb.model.ResultStatus
import id.sisco.themoviedb.model.Status
import kotlinx.coroutines.*

class MainViewModel: ViewModel() {
    private val _listMovie = MutableLiveData<ArrayList<MovieModel.Result>>()
    val listMovie : LiveData<ArrayList<MovieModel.Result>>
        get() = _listMovie

    private val _status = MutableLiveData(ResultStatus(Status.NULL))
    val status : LiveData<ResultStatus>
        get() = _status

    private val _msg = MutableLiveData("")
    val msg : LiveData<String>
        get() = _msg

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun setMovie() {
        _status.value = ResultStatus(Status.LOADING)
        uiScope.launch {
            getMovie()
        }
    }

    private suspend fun getMovie() {
        withContext(Dispatchers.IO) {
            try {
                val url = BuildConfig.BASE_URL + "3/movie/popular?api_key="+ BuildConfig.API_KEY +"&language=en-US&page=1"
                val response = MainRepository.instance.getData(url)
                if (response?.results != null) {
                    if (response.results.size != 0){
                        _status.postValue(ResultStatus(Status.SUCCESS))
                        _listMovie.postValue(response.results)
                    } else {
                        _status.postValue(ResultStatus(Status.EMPTY))
                        _msg.postValue("Tidak ada data.")
                    }
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