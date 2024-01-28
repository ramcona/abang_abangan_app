package id.technice.rispekabangku.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("all")
    suspend fun getData(
        @Query("lang") lang:String
    ): Response<List<String>>



}