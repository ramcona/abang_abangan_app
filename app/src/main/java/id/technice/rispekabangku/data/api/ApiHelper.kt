package id.technice.rispekabangku.data.api

import retrofit2.Response

interface ApiHelper {

    suspend fun getData(lang:String): Response<List<String>>

}
