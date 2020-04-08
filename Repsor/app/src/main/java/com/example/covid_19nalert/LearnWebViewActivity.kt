package com.example.covid_19nalert

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.covid_19nalert.databinding.ActivityLearnWebViewBinding
import com.example.covid_19nalert.learn.LearnEntity

/**WEBVIEW activity for the learn resources**/
@Suppress("DEPRECATION")
class LearnWebViewActivity : AppCompatActivity() {
    private lateinit var allWebView : WebView
    private lateinit var recievedLearningResource : LearnEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_PROGRESS)
        val binding = DataBindingUtil.setContentView<ActivityLearnWebViewBinding>(this, R.layout.activity_learn_web_view)
        window.setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON)


        val toolBar =  binding.toolBar
        setSupportActionBar(toolBar)
        recievedLearningResource = intent.getParcelableExtra<LearnEntity>("learnResource")
        toolBar.title = recievedLearningResource.learningResourceTitle.take(20) + "...."
        toolBar.subtitle = recievedLearningResource.learningResourceShortDescription.take(30) + "...."
        toolBar.setNavigationOnClickListener {
            finish()
        }


        allWebView = binding.myWebView
        allWebView.webViewClient = MyWebViewClient(binding.progreeRn)
        allWebView.webChromeClient = WebChromeClient()
        allWebView.loadUrl(recievedLearningResource.urlToLearningResource)
        allWebView.settings.javaScriptEnabled = true
        allWebView.settings.setSupportZoom(true)
        allWebView.settings.allowUniversalAccessFromFileURLs = true
        allWebView.settings.allowFileAccessFromFileURLs = true
        allWebView.settings.loadsImagesAutomatically = true
        allWebView.settings.displayZoomControls = true

 }

    override fun onBackPressed() {
        if(allWebView.canGoBack()) allWebView.goBack()
        else super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        allWebView.goBack()
        return true
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
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_TEXT, recievedLearningResource.urlToLearningResource)
            startActivity(intent)
            return true
        }
    }
}

