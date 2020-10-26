package id.sisco.themoviedb.model

data class ResultStatus (
    val status: Status
)

enum class Status{
    NULL,
    LOADING,
    SUCCESS,
    FAILED,
    EMPTY,
    ERROR
}