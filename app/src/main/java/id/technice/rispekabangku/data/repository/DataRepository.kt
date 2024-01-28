package id.technice.rispekabangku.data.repository

import id.technice.rispekabangku.data.api.ApiHelper
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getData(locale:String = "id") =  apiHelper.getData(locale)
}