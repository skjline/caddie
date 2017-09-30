package com.skjline.caddie.game.controller

import android.location.Location
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.skjline.caddie.R
import com.skjline.caddie.common.Const
import com.skjline.caddie.common.ViewController
import com.skjline.caddie.common.model.Stroke
import com.skjline.caddie.common.utils.bind
import com.skjline.caddie.common.utils.digitFormat
import com.skjline.caddie.common.utils.toLocation
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created on 9/9/17.
 */
class HoleViewController(host: View) : ViewController {

    private val tvStroke by host.bind<TextView>(R.id.tv_stroke_count)
    private val tvLat by host.bind<TextView>(R.id.tv_latitude)
    private val tvLng by host.bind<TextView>(R.id.tv_longitude)

    private val tvDistanceNext by host.bind<TextView>(R.id.tv_distance_forward)
    private val tvDistancePrev by host.bind<TextView>(R.id.tv_distance_backward)


    private val btnStroke by host.bind<Button>(R.id.btn_stroke)
    private val btnDrop by host.bind<Button>(R.id.btn_drop)
    private val btnHoleOut by host.bind<Button>(R.id.btn_hole_out)

    private var ref: Location? = null
    var presenter: MapViewController? = null

    private val listener = View.OnClickListener { btn ->
        val count = Integer.parseInt(tvStroke.text.toString()) + 1
        tvStroke.text = "$count"

        val stroke = Stroke(0, count, btn.id == R.id.btn_drop, 0.0, 0.0)

        presenter?.let {
            it.takeLocationSnapShot(stroke)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { s -> onPositionUpdated(s.toLocation()) }

            it.onLocationChanged()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { l -> onPositionUpdated(l) }
        }
    }

    init {
        tvStroke.text = "0"

        btnStroke.setOnClickListener(listener)
        btnDrop.setOnClickListener(listener)
        btnHoleOut.setOnClickListener({
            tvStroke.text = "0"
        })
    }


    private fun onPositionUpdated(location: Location) {
        if (location.extras.getInt(Const.POSITION_TYPE) == Const.POSITION_REF) {
            ref = location
        }

        val pos = ref ?: return

        val dist = pos.distanceTo(location).toDouble()

        tvDistanceNext.text = "${dist.digitFormat(2)} meters ${dist.times(Const.METERS_TO_YARDS).digitFormat(2)} yards"
    }
}