package id.technice.rispekabangku.data.api

import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getData(lang: String): Response<List<String>> = apiService.getData(lang)

}