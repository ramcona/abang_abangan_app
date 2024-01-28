package id.technice.rispekabangku.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.technice.rispekabangku.R
import id.technice.rispekabangku.base.App.Companion.pref
import id.technice.rispekabangku.base.BaseActivity
import id.technice.rispekabangku.databinding.ActivityMainBinding
import id.technice.rispekabangku.extention.onTextChanged
import id.technice.rispekabangku.extention.viewBinding
import id.technice.rispekabangku.networkUtils.Status

class MainActivity : BaseActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: DataViewModel by viewModels()

    private val datasAdapter: DataAdapter by lazy {
        DataAdapter(ArrayList(), callback = object : DataAdapter.Callback {
            override fun dataCopy(data: String) {
                //copy to clipbaord
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Copied Text", data)
                clipboard.setPrimaryClip(clip)

                //show message success
                showSnackbar(binding.root)

            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        removeActionBar()
        setupUI()
        action()
        setupObserver()

        viewModel.fetchDatas(pref.getLanguange())

    }

    private fun action() {
        binding.swipe.setOnRefreshListener {
            datasAdapter.reset()
            binding.edtSearch.setText("")
            binding.swipe.isRefreshing = false
            viewModel.fetchDatas(pref.getLanguange())
        }

        binding.toolbar.ivInfo.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.text_information))
                .setPositiveButton(getString(R.string.teks_kasih_paham_wak)) { _, _ ->
                }
                .show()
        }

        binding.toolbar.ivLang.setOnClickListener {
            if (pref.getLanguange() == "id") {
                pref.setLanguange("en")
            }else {
                pref.setLanguange("id")
            }
            updateLanguangeImage()

            //reset current list
            datasAdapter.reset()
            binding.edtSearch.setText("")

            viewModel.fetchDatas(pref.getLanguange())
        }

        binding.edtSearch.onTextChanged {
            datasAdapter.filter.filter(it)
        }

        binding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.edtSearch.text.toString()
                datasAdapter.filter.filter(query)
            }
            true
        }
    }

    private fun setupUI() {
        binding.rvData.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = datasAdapter
        }

        updateLanguangeImage()
    }

    private fun updateLanguangeImage() {
        if (pref.getLanguange() == "id") {
            binding.toolbar.ivLang.setImageResource(R.drawable.ic_id_flag)
        }else {
            binding.toolbar.ivLang.setImageResource(R.drawable.ic_uk_flag)
        }
    }

    private fun setupObserver() {
        viewModel.datas.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {datas ->
                        datasAdapter.add(datas)
                    }
                }

                Status.ERROR -> {

                    binding.viewNoData.isVisible = true
                }

                Status.LOADING -> {

                    binding.viewNoData.isVisible = false
                }
            }
        }
    }
}