package id.sisco.themoviedb.model
import com.google.gson.annotations.SerializedName


data class MovieCreditModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("cast")
    val cast: ArrayList<Cast>?,
    @SerializedName("crew")
    val crew: ArrayList<Crew>?
){

    data class Cast(
        @SerializedName("cast_id")
        val castId: Int?,
        @SerializedName("character")
        val character: String?,
        @SerializedName("credit_id")
        val creditId: String?,
        @SerializedName("gender")
        val gender: Int?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("order")
        val order: Int?,
        @SerializedName("profile_path")
        val profilePath: String?
    )

    data class Crew(
        @SerializedName("credit_id")
        val creditId: String?,
        @SerializedName("department")
        val department: String?,
        @SerializedName("gender")
        val gender: Int?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("job")
        val job: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("profile_path")
        val profilePath: Any?
    )
}
