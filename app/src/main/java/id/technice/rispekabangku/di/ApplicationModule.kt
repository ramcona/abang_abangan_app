package id.technice.rispekabangku.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.technice.rispekabangku.BuildConfig
import id.technice.rispekabangku.data.api.ApiHelper
import id.technice.rispekabangku.data.api.ApiHelperImpl
import id.technice.rispekabangku.data.api.ApiService
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL + "api/"

    @Provides
    @Singleton
    fun provideInterceptorAccept() = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .build()
            try {
                return chain.proceed(newRequest)
            } catch (e: SocketTimeoutException) {
                // Create error response
                val errorBody = ResponseBody.create("application/json".toMediaTypeOrNull(), "{ \"error\": \"Network error\" }")
                return Response.Builder()
                    .code(500)
                    .message("Internal Server Error")
                    .body(errorBody)
                    .request(newRequest)
                    .protocol(Protocol.HTTP_1_1)
                    .build()
            } catch (e: IOException) {
                // Handle other IOExceptions
                throw e
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(provideInterceptorAccept())
                .addInterceptor(loggingInterceptor)
                .build()
        } else OkHttpClient.Builder()
            .addInterceptor(provideInterceptorAccept())
            .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit {
        val gson = GsonBuilder().setLenient().create()  // Create lenient Gson instance

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson)) // Use the lenient Gson
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

}