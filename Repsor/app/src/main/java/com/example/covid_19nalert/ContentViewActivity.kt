package com.example.covid_19nalert

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.EXTRA_TEXT
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.covid_19nalert.databinding.ActivityContentViewBinding
import com.example.covid_19nalert.domain.NewsDomainModel
import com.example.covid_19nalert.learn.LearnEntity

/**WebView atcivity to display the selected news**/
class ContentViewActivity : AppCompatActivity() {
    private lateinit var binding : ActivityContentViewBinding
    private lateinit var allWebView : WebView
    private lateinit var allArticleUrl : NewsDomainModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityContentViewBinding>(this,  R.layout.activity_content_view)

        val toolBar =  binding.toolBar
        setSupportActionBar(toolBar)
        allArticleUrl = intent.getParcelableExtra<NewsDomainModel>("newsArticle")
        toolBar.title = allArticleUrl.title?.take(30) + "...."
        toolBar.subtitle = allArticleUrl.source
        toolBar.setNavigationOnClickListener {
            finish()
        }
        allWebView = binding.myWebView
        allWebView.webViewClient = MyWebViewClient(binding.progressNow)
        allWebView.loadUrl(allArticleUrl.url)
        allWebView.settings.javaScriptEnabled = true
        allWebView.settings.setSupportZoom(true)
        allWebView.settings.allowUniversalAccessFromFileURLs = true
        allWebView.settings.allowFileAccessFromFileURLs = true
        allWebView.settings.loadsImagesAutomatically = true
        allWebView.settings.displayZoomControls = true

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.refresh_menu){
            allWebView.reload()
            return true
        }
        else{
            val intent = Intent(ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(EXTRA_TEXT, allArticleUrl.url)
            startActivity(intent)
            return true
        }
    }

    override fun onBackPressed() {
        if(allWebView.canGoBack()) allWebView.goBack()
        else super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        allWebView.goBack()
        return true
    }
}

/**WEBVIEW client for all the webViews in the application**/
class MyWebViewClient(val progressBar: ProgressBar) : WebViewClient() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        view?.loadUrl(request?.url.toString())


        return true
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        progressBar.visibility = View.VISIBLE

    }

    override fun onPageFinished(view: WebView?, url: String?) {
        progressBar.visibility = View.GONE
        super.onPageFinished(view, url)
    }

    //to support lower Api levels
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        view?.loadUrl(url)
        return true
    }
}

