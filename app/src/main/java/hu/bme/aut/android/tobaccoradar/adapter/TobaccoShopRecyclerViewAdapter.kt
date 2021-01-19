package hu.bme.aut.android.tobaccoradar.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.tobaccoradar.R
import hu.bme.aut.android.tobaccoradar.model.TobaccoShop
import kotlinx.android.synthetic.main.row_item.view.*

class TobaccoShopRecyclerViewAdapter : RecyclerView.Adapter<TobaccoShopRecyclerViewAdapter.ViewHolder>() {

    private var shopList : MutableList<TobaccoShop> = mutableListOf<TobaccoShop>(
        TobaccoShop("Név1", "Cím1", false, "", ""),
        TobaccoShop("Név2", "Cím2", true, "", ""),
        TobaccoShop("Név3", "Cím3", false, "", "")
    )

     private var searchedShopList : MutableList<TobaccoShop> = mutableListOf()

    var itemClickListener : TobaccoShopClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TobaccoShopRecyclerViewAdapter.ViewHolder, position: Int) {
        val tobaccoShop = shopList[position]

        holder.tobaccoShop = tobaccoShop
        holder.tvName.text = tobaccoShop.name
        holder.tvAddress.text = tobaccoShop.address

        if(tobaccoShop.isOpen) {
            holder.tvIsOpen.text = "Nyitva"
            holder.tvIsOpen.setTextColor(Color.GREEN)
        }
        else {
            holder.tvIsOpen.text = "Zárva"
            holder.tvIsOpen.setTextColor(Color.RED)
        }

    }

    fun search(text : String){
        if(searchedShopList.size != 0){
            shopList = searchedShopList
            searchedShopList = mutableListOf()
        }
        for (s in shopList){
            if(s.name.contains(text))
                searchedShopList.add(s)
        }
        val backUp = shopList
        shopList = searchedShopList
        searchedShopList = backUp
        notifyDataSetChanged()
        //TODO ALL LOWER CASE
    }

    override fun getItemCount() = shopList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvName : TextView = itemView.tvName
        val tvAddress : TextView = itemView.tvAddress
        val tvIsOpen : TextView = itemView.tvIsOpen
        var tobaccoShop: TobaccoShop? = null

        init {
            itemView.setOnClickListener {
                tobaccoShop?.let { tobaccoShop -> itemClickListener?.onItemClick(tobaccoShop) }
            }
        }

    }

    interface TobaccoShopClickListener{
        fun onItemClick(tobaccoShop: TobaccoShop)
    }
}
