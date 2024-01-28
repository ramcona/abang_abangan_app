package id.technice.rispekabangku.base

data class BaseResponse<T>(
    val status_data: Boolean,
    val msg: String,
    val data: T?
)