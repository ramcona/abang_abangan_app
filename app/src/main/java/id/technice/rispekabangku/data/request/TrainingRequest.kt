package id.technice.rispekabangku.data.request

import java.io.Serializable

class TrainingRequest : Serializable {
    var token:String = ""
    var id:String = ""
    var tgl_pelatihan:String = ""
    var jenis_diklat:String = ""
    var nama_pelatihan:String = ""
    var tempat_pelatihan:String = ""
    var akreditasi:String  = ""
    var lama_pelatihan:String  = ""
    var jumlah_jam:String  = ""
    var penyelenggara:String  = ""
}