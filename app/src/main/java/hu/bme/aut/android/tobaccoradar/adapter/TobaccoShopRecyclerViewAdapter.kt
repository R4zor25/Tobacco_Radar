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

class TobaccoShopRecyclerViewAdapter :
    RecyclerView.Adapter<TobaccoShopRecyclerViewAdapter.ViewHolder>() {

    private var tobaccoShopModelList: MutableList<TobaccoShopListModel> =
        mutableListOf() //TODO MODIFY

    private var searchedTobaccoShopModelList: MutableList<TobaccoShopListModel> = mutableListOf()

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
        val tobaccoShop = tobaccoShopModelList[position]

        holder.tobaccoShopListModel = tobaccoShop
        holder.tvName.text = tobaccoShop.name
        holder.tvAddress.text = tobaccoShop.address

        if (tobaccoShop.isOpen) {
            holder.tvIsOpen.text = "Nyitva"
            holder.tvIsOpen.setTextColor(Color.GREEN)
        } else {
            holder.tvIsOpen.text = "Zárva"
            holder.tvIsOpen.setTextColor(Color.RED)
        }

    }

    fun search(text: String) {
        if (searchedTobaccoShopModelList.isNotEmpty()) {
            tobaccoShopModelList = searchedTobaccoShopModelList
            searchedTobaccoShopModelList = mutableListOf()
        }
        for (s in tobaccoShopModelList) {
            if (s.name.contains(text))
                searchedTobaccoShopModelList.add(s)
        }
        val backUp = tobaccoShopModelList
        tobaccoShopModelList = searchedTobaccoShopModelList
        searchedTobaccoShopModelList = backUp
        notifyDataSetChanged()
    }

    fun addAll(shops: MutableList<TobaccoShopListModel>) {
        tobaccoShopModelList.clear()
        tobaccoShopModelList.addAll(shops)
    }

    override fun getItemCount() = tobaccoShopModelList.size

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

