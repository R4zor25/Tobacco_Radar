package hu.bme.aut.android.tobaccoradar.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.tobaccoradar.R
import hu.bme.aut.android.tobaccoradar.network.model.TobaccoShopListModel
import kotlinx.android.synthetic.main.row_item.view.*
import java.util.*

class TobaccoShopRecyclerViewAdapter :
    RecyclerView.Adapter<TobaccoShopRecyclerViewAdapter.ViewHolder>() {

    private var tobaccoShopListModelList: MutableList<TobaccoShopListModel> =
        mutableListOf()

    private var searchedTobaccoShopListModelList: MutableList<TobaccoShopListModel> =
        mutableListOf()

    var itemClickListener: TobaccoShopClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TobaccoShopRecyclerViewAdapter.ViewHolder,
        position: Int
    ) {
        val tobaccoShop = tobaccoShopListModelList[position]

        holder.tobaccoShopListModel = tobaccoShop
        holder.tvName.text = tobaccoShop.name
        holder.tvAddress.text = tobaccoShop.address

        if (tobaccoShop.isOpen) {
            holder.tvIsOpen.text = holder.itemView.context.getString(R.string.open)
            holder.tvIsOpen.setTextColor(Color.GREEN)
        } else {
            holder.tvIsOpen.text = holder.itemView.context.getString(R.string.closed)
            holder.tvIsOpen.setTextColor(Color.RED)
        }

    }

    fun search(searchedText: String) {
        if (searchedTobaccoShopListModelList.isNotEmpty()) {
            tobaccoShopListModelList = searchedTobaccoShopListModelList
            searchedTobaccoShopListModelList = mutableListOf()
        }
        for (s in tobaccoShopListModelList) {
            if (s.name.toLowerCase(Locale.ROOT).contains(searchedText.toLowerCase(Locale.ROOT)))
                searchedTobaccoShopListModelList.add(s)
        }
        val backUp = tobaccoShopListModelList
        tobaccoShopListModelList = searchedTobaccoShopListModelList
        searchedTobaccoShopListModelList = backUp
        notifyDataSetChanged()
    }

    fun addAll(shops: MutableList<TobaccoShopListModel>) {
        tobaccoShopListModelList.clear()
        tobaccoShopListModelList.addAll(shops)
    }

    override fun getItemCount() = tobaccoShopListModelList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.tvName
        val tvAddress: TextView = itemView.tvAddress
        val tvIsOpen: TextView = itemView.tvIsOpen
        var tobaccoShopListModel: TobaccoShopListModel? = null

        init {
            itemView.setOnClickListener {
                tobaccoShopListModel?.let { tobaccoShop ->
                    itemClickListener?.onItemClick(
                        tobaccoShop
                    )
                }
            }
        }

    }

    interface TobaccoShopClickListener {
        fun onItemClick(tobaccoShopListModel: TobaccoShopListModel)
    }
}

