package com.global.ratio_chacker

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RatioChecker(private val activity: AppCompatActivity) {
    private val lk =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            q()
        }

    private val ratioHandler = Handler

    private var re: String = ""
    private var ht = ""

    fun startRatio(
        mainActivity: Class<out Activity>,
        data: String,
        key: String,
    ) {
        activity.apply {
            if (!isTaskRoot
                && intent?.hasCategory(Intent.CATEGORY_LAUNCHER) == true
                && intent.action?.equals(Intent.ACTION_MAIN) == true
            ) {
                finish()
                return
            }
        }

        this.re = data
        this.ht = key

        Handler.rg(mainActivity)

        with(activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val permission = Manifest.permission.POST_NOTIFICATIONS
                if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                    q()
                } else {
                    lk.launch(permission)
                }
            } else q()
        }
    }

    private fun q() {
        if (re.isBlank() || ht.isBlank()) return

        CoroutineScope(Dispatchers.Main).launch {
            val t = Handler.e(re, ht)

            val td = Handler.b(activity)

            if (t?.s == true || !Handler.ku(
                    activity
                ) || td.second
            ) {
                Handler.ht(activity)
                return@launch
            }

            if(t == null){
                delay(1500L)
                q()
                return@launch
            }

            Handler.gew(activity, t.r)

            val lp = if (!td.first.isNullOrBlank()) td.first
            else Handler.fg(activity, t)

            val ds = Intent(activity, PolicyHandler::class.java)

            ds.apply {
                putExtra("data", lp)
                putExtra("ex", t.q)
            }

            activity.startActivity(ds)
            activity.finish()
        }

    }

}