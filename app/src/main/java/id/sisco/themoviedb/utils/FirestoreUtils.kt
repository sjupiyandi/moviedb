package id.sisco.themoviedb.utils

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreUtils {

    private var db: FirebaseFirestore? = null

    companion object {
        private var instance: FirestoreUtils? = null

        fun getDbInstance(): FirebaseFirestore {
            if (instance == null) {
                instance = FirestoreUtils()

                val settingBuilder = FirebaseFirestoreSettings.Builder()
//                settingBuilder.isPersistenceEnabled = true

                instance!!.db = Firebase.firestore
                instance!!.db!!.firestoreSettings = settingBuilder.build()
            }

            return instance!!.db!!
        }
    }
}