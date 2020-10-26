package id.sisco.themoviedb.module.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.MetadataChanges
import id.sisco.themoviedb.model.*
import id.sisco.themoviedb.utils.FirestoreUtils
import kotlinx.coroutines.*

class ProfileViewModel: ViewModel() {
    private val _detailUser = MutableLiveData<UserModel>()
    val detailUser : LiveData<UserModel>
        get() = _detailUser

    private val _status = MutableLiveData(ResultStatus(Status.NULL))
    val status : LiveData<ResultStatus>
        get() = _status

    private val _msg = MutableLiveData("")
    val msg : LiveData<String>
        get() = _msg

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun setUser(username: String) {
        _status.value = ResultStatus(Status.LOADING)
        uiScope.launch {
            getUser(username)
        }
    }

    private suspend fun getUser(email: String) {
        withContext(Dispatchers.IO) {
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
                                    _detailUser.postValue(user)
                                    _status.postValue(ResultStatus(Status.SUCCESS))
                                }
                            }
                        } else {
                            _status.postValue(ResultStatus(Status.ERROR))
                            _msg.postValue("Terjadi kesalahan saat\n mengambil data")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("Error", e.toString())
                _status.postValue(ResultStatus(Status.ERROR))
                _msg.postValue("Terjadi kesalahan saat\n mengambil data")
            }
        }
    }
}