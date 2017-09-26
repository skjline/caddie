package com.skjline.caddie.game.controller

import android.location.Location
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.skjline.caddie.R
import com.skjline.caddie.common.ViewController
import com.skjline.caddie.common.utils.bind

/**
 * Created on 9/9/17.
 */
class HoleViewController(host: View) : ViewController {

    val tvStroke by host.bind<TextView>(R.id.tv_stroke_count)
    val tvLat by host.bind<TextView>(R.id.tv_latitude)
    val tvLng by host.bind<TextView>(R.id.tv_longitude)

    val tvDistanceNext by host.bind<TextView>(R.id.tv_distance_forward)
    val tvDistancePrev by host.bind<TextView>(R.id.tv_distance_backward)


    val btnStroke by host.bind<Button>(R.id.btn_stroke)
    val btnDrop by host.bind<Button>(R.id.btn_drop)
    val btnHoleOut by host.bind<Button>(R.id.btn_hole_out)

    val listener = View.OnClickListener {
        tvStroke.text = "${Integer.parseInt(tvStroke.text.toString()) + 1}"
    }

    init {
        tvStroke.text = "0"

        btnStroke.setOnClickListener(listener)
        btnDrop.setOnClickListener(listener)
        btnHoleOut.setOnClickListener({
            tvStroke.text = "0"
        })
    }

    fun onPositionUpdated(location: Location) {

    }
}