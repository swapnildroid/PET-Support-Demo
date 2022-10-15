package com.swapnildroid.pet.support.ui.main.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.swapnildroid.pet.support.ui.main.interaction.WebBrowserInterface

class WebBrowserFragment : Fragment(), WebBrowserInterface {

    companion object {

        private const val EXTRA_URL = "EXTRA_URL"

        fun newInstance(url: String?) = WebBrowserFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_URL, url)
            }
        }

    }

    inner class WebBrowserClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            view?.loadUrl(request?.url?.toString())
            return false
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url)
            return false
        }
    }

    private lateinit var mWebView: WebView
    private var mUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mUrl = arguments?.getString(EXTRA_URL)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mWebView = WebView(context)
        return mWebView
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mWebView.settings.javaScriptEnabled = true
        mWebView.loadUrl(mUrl)
        mWebView.webViewClient = WebBrowserClient()
        mWebView.canGoBack()
    }

    override fun canGoBack(): Boolean = mWebView.canGoBack()
    override fun goBack() {
        if (mWebView.canGoBack()) mWebView.goBack()
    }

}