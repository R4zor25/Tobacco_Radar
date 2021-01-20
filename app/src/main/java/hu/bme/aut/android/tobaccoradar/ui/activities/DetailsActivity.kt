package hu.bme.aut.android.tobaccoradar.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.android.tobaccoradar.R
import hu.bme.aut.android.tobaccoradar.network.model.TobaccoShopListModel
import hu.bme.aut.android.tobaccoradar.ui.fragments.TobaccoShopFragment

class DetailsActivity : AppCompatActivity() {

    private lateinit var selectedTobaccoShop : TobaccoShopListModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val bundle = intent.extras?.get(TobaccoShopFragment.GET_SELECTED_SHOP_BUNDLE) as Bundle
        selectedTobaccoShop = bundle.get(TobaccoShopFragment.GET_SELECTED_SHOP) as TobaccoShopListModel
    }
}