package id.technice.rispekabangku.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import id.technice.rispekabangku.databinding.ItemDataBinding
import java.util.Locale

class DataAdapter(datas: ArrayList<String>, var callback:Callback): RecyclerView.Adapter<DataAdapter.Holder>(),
    Filterable {

    var allDatas: ArrayList<String> =  datas
    var listDataFiltered: ArrayList<String> = datas

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setData(listDataFiltered[position], callback)
    }

    fun reset() {
        allDatas.clear()
        listDataFiltered.clear()
        notifyDataSetChanged()
    }

    fun add(newData:List<String>) {
        allDatas.addAll(newData)
        listDataFiltered.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount() = listDataFiltered.size

    class Holder(var item: ItemDataBinding): RecyclerView.ViewHolder(item.root) {


        val context:Context = itemView.context

        fun setData(data: String, callback: Callback) {

            item.tvMessage.text = data

            item.ivCopy.setOnClickListener {
                //action to copy
                callback.dataCopy(data)
            }
        }
    }

    interface Callback {
        fun dataCopy(data:String)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            @SuppressLint("DefaultLocale")
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                val result2 = java.util.ArrayList<String>()
                listDataFiltered = allDatas
                if (constraint != null) {
                    if ((listDataFiltered.size > 0)) {
                        for (map in listDataFiltered) {
                            if (map.lowercase(Locale.getDefault()).contains(constraint.toString().lowercase(Locale.getDefault()))) {
                                result2.add(map)
                            }
                        }

                    }
                    results.values = result2
                }

                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                listDataFiltered = results.values as java.util.ArrayList<String>
                notifyDataSetChanged()
            }
        }
    }

}