package com.skjline.caddie.core.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.skjline.caddie.App
import com.skjline.caddie.R
import com.skjline.caddie.common.utils.FragmentRouter
import com.skjline.caddie.common.utils.hasPermission
import com.skjline.caddie.common.utils.requestPermission
import javax.inject.Inject


class ScoreActivity : AppCompatActivity() {

    companion object {
        @JvmStatic val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    @Inject
    lateinit var router: FragmentRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        App.component.inject(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

//        val fab = findViewById<FloatingActionButton>(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_score, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        if (hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            router.route(this, R.layout.game_fragment)
            return
        }

        requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    router.route(this, R.layout.game_fragment)
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }
    }


}
