package hu.bme.aut.android.tobaccoradar.ui.activities

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import hu.bme.aut.android.tobaccoradar.R
import hu.bme.aut.android.tobaccoradar.network.APIConnection
import hu.bme.aut.android.tobaccoradar.network.model.TobaccoShopModel
import hu.bme.aut.android.tobaccoradar.ui.fragments.TobaccoShopFragment
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private lateinit var selectedTobaccoShop : TobaccoShopModel

    private lateinit var selectedTobaccoShopImage : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val id: Int = intent.getIntExtra(TobaccoShopFragment.GET_SELECTED_SHOP_ID, -1)

        if(id == -1)
            Toast.makeText(this,"Sikertelen betöltés", Toast.LENGTH_LONG).show()

        APIConnection.getSelectedTobaccoShop(id)
        APIConnection.getSelectedTobaccoShopImage(id)

        APIConnection.selectedTobaccoShopAPI.observe(this, Observer { shop ->
            tsName.text = shop.name
            tsAddress.text = shop.address
            tsDesc.text = shop.description
        })

        APIConnection.selectedTobaccoShopImageAPI.observe(this, Observer { image ->
            shop_image.setImageBitmap(image)
        })
       // selectedTobaccoShopImage = BitmapFactory.decodeResource(resources,R.drawable.black)
       // imageView.setImageBitmap(selectedTobaccoShopImage)
    }
}