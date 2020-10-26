package id.sisco.themoviedb.model

import com.google.gson.annotations.SerializedName

class UserModel (
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("email")
    val email: String? = "",
    @SerializedName("password")
    val password: String? = "",
    @SerializedName("phone")
    val phone: String? = "",
    @SerializedName("address")
    val address: String? = ""
)