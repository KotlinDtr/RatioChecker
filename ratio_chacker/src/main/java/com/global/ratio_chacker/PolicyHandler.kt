package com.global.ratio_chacker

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class PolicyHandler @Inject constructor(
) : AppCompatActivity() {
    private lateinit var rh: WebView

    var td: ValueCallback<Array<Uri?>?>? = null

    private val th = "http://"
    private val lr = "https://"

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        rh.restoreState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        rh.saveState(outState)
    }

    override fun onPause() {
        CookieManager.getInstance().flush()
        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rh = WebView(this)

        rh.apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            Handler.o(rh)
        }.also { webView ->
            setContentView(webView)
        }

        val ew = intent.getStringExtra("data") as String

        val hj: String = intent.getStringExtra("ex") as String

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (rh.canGoBack()) {
                        rh.goBack()
                    }
                }
            }
        )

        rh.webChromeClient = CustomWebChromeClient()

        rh.webViewClient = CustomWebViewClient(
            hj
        ){
            Handler.ht(this)
        }
        rh.loadUrl(ew)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123) {
            if (resultCode != RESULT_OK) {
                td?.onReceiveValue(null)
            }else{
                if (data == null) {
                    td?.onReceiveValue(null)
                }else{
                    if (data.data != null) {
                        val ew = data.data
                        if (ew != null) {
                            td?.onReceiveValue(arrayOf(ew))
                        }
                    } else {
                        td?.onReceiveValue(null)

                    }
                }
            }
        }
    }

    private inner class CustomWebViewClient(
        private val we: String,
        private val gr: () -> Unit,
    ) : android.webkit.WebViewClient() {

        override fun onPageFinished(ht: WebView?, kl: String?) {
            super.onPageFinished(ht, kl)

            if (
                kl?.contains(we) == true
            ) {
                gr()
            } else {
                if(kl.isNullOrBlank()) return

                CoroutineScope(Dispatchers.IO).launch {
                    Handler.y(this@PolicyHandler, kl)
                }
            }
        }

        override fun shouldOverrideUrlLoading(
            we: WebView?,
            hy: WebResourceRequest?
        ): Boolean {
            val kl = hy?.url?.toString() ?: return false

            try {
                if (kl.startsWith("${lr}t.me/joinchat")) {
                    Intent(Intent.ACTION_VIEW, Uri.parse(kl)).apply {
                        startActivity(this)
                    }

                    return false
                }


                return when (kl.startsWith(th) || kl.startsWith(lr)) {
                    true -> {
                        false
                    }

                    false -> {
                        if (kl.startsWith(WebView.SCHEME_MAILTO)) {
                            Intent(Intent.ACTION_SEND).apply {
                                type = "plain/text"
                                putExtra(
                                    Intent.EXTRA_EMAIL,
                                    kl.replace(WebView.SCHEME_MAILTO, "")
                                )
                                Intent.createChooser(this, "Mail")
                                    .run {
                                        startActivity(this)
                                    }
                            }
                        }

                        if (kl.startsWith(WebView.SCHEME_TEL)) {
                            Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse(kl)

                                Intent.createChooser(this, "Call")
                                    .run {
                                        startActivity(this)
                                    }
                            }
                        }

                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(kl))

                        startActivity(intent)

                        true
                    }
                }

            } catch (e: Exception) {
                return true
            }
        }
    }

    private inner class CustomWebChromeClient : WebChromeClient() {
        private var pg = ValueCallback<Array<Uri?>?> { }

        override fun onShowFileChooser(
            kt: WebView?,
            lp: ValueCallback<Array<Uri?>?>,
            df: FileChooserParams?
        ): Boolean {
            pg = lp

            val ki = listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
            )

            Dexter.withContext(this@PolicyHandler).withPermissions(ki)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                        td = pg

                        val rt = Intent(Intent.ACTION_GET_CONTENT)
                        rt.type = "image/*"

                        @Suppress("DEPRECATION")
                        startActivityForResult(rt, 123)
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                        p1: PermissionToken?
                    ) {
                        p1?.continuePermissionRequest()
                    }
                }).check()
            return true
        }
    }
}