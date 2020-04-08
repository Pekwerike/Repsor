package com.example.covid_19nalert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import androidx.databinding.DataBindingUtil
import com.example.covid_19nalert.databinding.ActivityNcdcTwitterBinding

class NcdcTwitterActivity : AppCompatActivity() {
    private lateinit var allWebView : WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityNcdcTwitterBinding>(this, R.layout.activity_ncdc_twitter)



        val toolBar =  binding.toolBar
        setSupportActionBar(toolBar)
        val recievedUrl = intent.getStringExtra("ncdcTwitterHandle")
        toolBar.title = "NCDC"
        toolBar.subtitle = "Nigeria Centre for Disease Control"
        toolBar.setNavigationOnClickListener {
            finish()
        }


        allWebView = binding.myWebView
        allWebView.webViewClient = MyWebViewClient(binding.progressBar)
        allWebView.loadUrl(recievedUrl)
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
        menuInflater.inflate(R.menu.twitter_web_view_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            allWebView.reload()
            return true

    }
}
