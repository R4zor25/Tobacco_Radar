package hu.bme.aut.android.tobaccoradar.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.android.tobaccoradar.R
import hu.bme.aut.android.tobaccoradar.adapter.TobaccoShopRecyclerViewAdapter
import hu.bme.aut.android.tobaccoradar.network.model.TobaccoShopListModel
import hu.bme.aut.android.tobaccoradar.ui.activities.DetailsActivity
import kotlinx.android.synthetic.main.fragment_item_list.*

class TobaccoShopFragment : Fragment(), TobaccoShopRecyclerViewAdapter.TobaccoShopClickListener {

    private var columnCount = 1

    lateinit var recyclerViewAdapter: TobaccoShopRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = TobaccoShopRecyclerViewAdapter()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchButton.setOnClickListener {
            recyclerViewAdapter.search(searchText.text.toString())
        }
        list.adapter = recyclerViewAdapter
    }

    fun setupRecyclerView() {
        recyclerViewAdapter = TobaccoShopRecyclerViewAdapter()
        recyclerViewAdapter.itemClickListener = this
    }

    companion object {

        const val ARG_COLUMN_COUNT = "1"

        const val GET_SELECTED_SHOP_ID = "GET_SELECTED_SHOP_ID"

        const val GET_SELECTED_SHOP_BUNDLE = "GET_SELECTED_SHOP_BUNDLE"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            TobaccoShopFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    override fun onItemClick(tobaccoShopListModel: TobaccoShopListModel) {
        val intent = Intent(this.context, DetailsActivity::class.java)
        intent.putExtra(GET_SELECTED_SHOP_ID, tobaccoShopListModel.id)
        startActivity(intent)

    }
}