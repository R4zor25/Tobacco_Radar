package hu.bme.aut.android.tobaccoradar.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import hu.bme.aut.android.tobaccoradar.R
import hu.bme.aut.android.tobaccoradar.network.APIConnection
import hu.bme.aut.android.tobaccoradar.ui.fragments.MapsFragment
import hu.bme.aut.android.tobaccoradar.ui.fragments.TobaccoShopFragment


class MainActivity : AppCompatActivity() {

    private val listFragment: TobaccoShopFragment = TobaccoShopFragment.newInstance(1)

    private val mapFragment: MapsFragment = MapsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        APIConnection.tobaccoShopListAPI.observe(this, Observer { shops ->
            listFragment.recyclerViewAdapter.addAll(shops)
            listFragment.recyclerViewAdapter.notifyDataSetChanged()
        })

        listFragment.setupRecyclerView()

        supportFragmentManager.beginTransaction()
            .add(R.id.root_container, listFragment)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        System.exit(0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.switch_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.switch_view) {
            if (item.title == getString(R.string.list_view)) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.root_container, listFragment)
                    .commit()
                item.title = getString(R.string.map_view)
            } else {
                item.title = getString(R.string.list_view)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.root_container, mapFragment)
                    .commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}