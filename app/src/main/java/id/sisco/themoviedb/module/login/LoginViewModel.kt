package id.sisco.themoviedb.module.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.favre.lib.crypto.bcrypt.BCrypt
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.MetadataChanges
import id.sisco.themoviedb.model.ResultStatus
import id.sisco.themoviedb.model.Status
import id.sisco.themoviedb.model.UserModel
import id.sisco.themoviedb.utils.FirestoreUtils
import id.sisco.themoviedb.utils.SPManager
import kotlinx.coroutines.*

class LoginViewModel(private val pref: SPManager): ViewModel() {

    private var _status = MutableLiveData(ResultStatus(Status.NULL))
    val status : LiveData<ResultStatus>
        get() = _status

    private val _msg = MutableLiveData("")
    val msg : LiveData<String>
        get() = _msg

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun login(email: String, password: String) {
        _status.value = ResultStatus(Status.LOADING)
        uiScope.launch {
            try {
                val db = FirestoreUtils.getDbInstance().collection("users").whereEqualTo("email", email)
                db.addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                    if (e != null) {
                        Log.e("Error", e.toString())
                        _status.postValue(ResultStatus(Status.ERROR))
                        _msg.postValue("Terjadi kesalahan saat\n mengambil data")
                        return@addSnapshotListener
                    } else {
                        if (!querySnapshot?.isEmpty!!) {
                            for (change in querySnapshot.documentChanges) {
                                if (change.type == DocumentChange.Type.ADDED) {
                                    val response = change.document
                                    val user = response.toObject(UserModel::class.java)
                                    val result = BCrypt.verifyer().verify(password.toCharArray(), user.password)
                                    if (result.verified){
                                        pref.saveSPUserId(email)
                                        pref.saveSPSudahLogin(true)
                                        _status.postValue(ResultStatus(Status.SUCCESS))
                                    } else {
                                        _status.postValue(ResultStatus(Status.ERROR))
                                        _msg.postValue("Password wrong")
                                    }
                                }
                            }
                        } else {
                            _status.postValue(ResultStatus(Status.ERROR))
                            _msg.postValue("Email not found")
                        }
                    }
                }
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