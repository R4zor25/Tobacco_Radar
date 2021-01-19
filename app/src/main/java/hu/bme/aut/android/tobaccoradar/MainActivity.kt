package hu.bme.aut.android.tobaccoradar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import hu.bme.aut.android.tobaccoradar.adapter.TobaccoShopRecyclerViewAdapter
import hu.bme.aut.android.tobaccoradar.fragments.TobaccoShopFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = TobaccoShopFragment.newInstance(1)
            supportFragmentManager.beginTransaction()
                .add(R.id.root_container, fragment)
                .commit()
        }

    override fun onBackPressed() {
        super.onBackPressed()
        System.exit(0)
    }

}