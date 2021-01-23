package hu.bme.aut.android.tobaccoradar.ui.activities

import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import hu.bme.aut.android.tobaccoradar.R
import hu.bme.aut.android.tobaccoradar.network.APIConnection
import hu.bme.aut.android.tobaccoradar.network.model.TobaccoShopModel
import hu.bme.aut.android.tobaccoradar.ui.fragments.TobaccoShopFragment
import kotlinx.android.synthetic.main.activity_details.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailsActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val id: Int = intent.getIntExtra(TobaccoShopFragment.GET_SELECTED_SHOP_ID, -1)

        if (id == -1)
            Toast.makeText(this, "Sikertelen betöltés", Toast.LENGTH_LONG).show()

        APIConnection.getSelectedTobaccoShop(id)
        APIConnection.getSelectedTobaccoShopImage(id)

        APIConnection.selectedTobaccoShopAPI.observe(this, Observer { shop ->
            tsName.text = shop.name
            tsAddress.text = shop.address
            tsDesc.text = shop.description
            tsIsOpen.text = setOpenText(shop)
            tsOpenSchedule.text = setScheduleText(shop)
        })

        APIConnection.selectedTobaccoShopImageAPI.observe(this, Observer { image ->
            shop_image.setImageBitmap(image)
        })

        tsDesc.movementMethod = ScrollingMovementMethod()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setScheduleText(shop: TobaccoShopModel): String {
        val scheduleText: StringBuilder = StringBuilder()
        val dayList: List<String> = listOf("H", "K", "Sze", "Cs", "P", "Szo", "V")
        val dateFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

        for (i in 0..6) {
            scheduleText.append(dayList[i] + ": ")

            val openDateTime = LocalDateTime.parse(
                shop.openHours[i].openTime,
                dateFormatter
            )
            val closeDateTime = LocalDateTime.parse(
                shop.openHours[i].closeTime,
                dateFormatter
            )
            scheduleText.append(openDateTime.hour.toString() + ":"
                    + openDateTime.minute.toString() + "0 - "
                    + closeDateTime.hour.toString() + ":"
                    + closeDateTime.minute.toString() + "0"
                    + "\n")
        }
        return scheduleText.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setOpenText(shop: TobaccoShopModel): String {
        val openText: StringBuilder = StringBuilder()
        if (shop.isOpen) {
            openText.append(getString(R.string.open))

        } else { //Shop is Closed
            val dateFormatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            openText.append(getString(R.string.closed) + "     " + getString(R.string.next_open_time) + ": ")
            val currentDateTime: LocalDateTime =
                LocalDateTime.now() //currentDateTime is GMT+0

            val openDateTime = LocalDateTime.parse(
                shop.openHours[currentDateTime.dayOfWeek.value - 1].openTime,
                dateFormatter
            )

            if (currentDateTime.hour + 1 < openDateTime.hour) { // Shop is closed but opens today
                openText.append(
                    getString(R.string.today) + ", "
                            + openDateTime.hour.toString() + ":"
                            + openDateTime.minute.toString() + "0"
                )

            } else {
                if (currentDateTime.dayOfWeek.value != 7) { // Shop is closed, opening tomorrow, it's not sunday
                    val tomorrowOpenDateTime = LocalDateTime.parse(
                        shop.openHours[currentDateTime.dayOfWeek.value].openTime,
                        dateFormatter
                    )
                    openText.append(
                        getString(R.string.tomorrow) + ", "
                                + tomorrowOpenDateTime.hour.toString() + ":"
                                + tomorrowOpenDateTime.minute.toString() + "0"
                    )
                } else { // Shop is closed, opening tomorrow, it's sunday
                    val mondayOpenDateTime = LocalDateTime.parse(
                        shop.openHours[0].openTime,
                        dateFormatter
                    )
                    openText.append(
                        getString(R.string.tomorrow) + ", "
                                + mondayOpenDateTime.hour.toString() + ":"
                                + mondayOpenDateTime.minute.toString() + "0"
                    )
                }
            }
        }
        return openText.toString()
    }
}