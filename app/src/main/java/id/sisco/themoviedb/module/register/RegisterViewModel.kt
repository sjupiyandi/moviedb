package id.sisco.themoviedb.module.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.favre.lib.crypto.bcrypt.BCrypt
import id.sisco.themoviedb.model.ResultStatus
import id.sisco.themoviedb.model.Status
import id.sisco.themoviedb.model.UserModel
import id.sisco.themoviedb.utils.FirestoreUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class RegisterViewModel: ViewModel() {

    private var _status = MutableLiveData(ResultStatus(Status.NULL))
    val status : LiveData<ResultStatus>
        get() = _status

    private val _msg = MutableLiveData("")
    val msg : LiveData<String>
        get() = _msg

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun register(user: UserModel){
        _status.value = ResultStatus(Status.LOADING)
        uiScope.launch {
            try {
                val bcryptHashPassword = BCrypt.withDefaults().hashToString(12, user.password?.toCharArray())
                Log.v("siscoo", bcryptHashPassword)
                val users = UserModel(user.name, user.email, bcryptHashPassword, user.phone, user.address)
                val db = FirestoreUtils.getDbInstance()
                db.collection("users").document(user.email.toString()).set(users)
                _status.postValue(ResultStatus(Status.SUCCESS))
            } catch (e: Exception){
                Log.e("Error", e.toString())
                _status.postValue(ResultStatus(Status.ERROR))
                _msg.postValue("Terjadi kesalahan saat mengambil data")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}