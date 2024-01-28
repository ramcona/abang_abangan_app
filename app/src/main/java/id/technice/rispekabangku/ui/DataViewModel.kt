package id.technice.rispekabangku.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.technice.rispekabangku.data.repository.DataRepository
import id.technice.rispekabangku.networkUtils.NetworkHelper
import id.technice.rispekabangku.networkUtils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val repository: DataRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _datas = MutableLiveData<Resource<List<String>>>()
    val datas: LiveData<Resource<List<String>>>
        get() = _datas


    fun fetchDatas(lang:String) {
        viewModelScope.launch {
            _datas.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                repository.getData(lang).let {
                    if (it.isSuccessful) {
                        it.body().let { body ->
                            body?.let { bod ->
                                if (bod.isNotEmpty()) {
                                    _datas.postValue(Resource.success(bod))
                                }else {
                                    _datas.postValue(Resource.error("Tidak ada data nih king \uD83D\uDC51\uD83D\uDE47\u200D", null))
                                }
                            } ?: {
                                _datas.postValue(Resource.error("Tidak ada data nih king \uD83D\uDC51\uD83D\uDE47\u200D", null))
                            }
                        }
                    } else _datas.postValue(Resource.error("Terjadi kesalahan coba refresh ulang halaman ini king", null))
                }
            } else _datas.postValue(Resource.error("Tidak dapat terhubung ke server, coba beberapa saat lagi abangkuh", null))
        }
    }


}