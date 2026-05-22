package com.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .systemBarsPadding()
        ) {
          WebViewScreen()
        }
      }
    }
  }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen() {
  var webViewRef: WebView? = null

  BackHandler {
    if (webViewRef?.canGoBack() == true) {
      webViewRef?.goBack()
    } else {
      // Allow default back behavior (close app if no history)
    }
  }

  AndroidView(
    factory = { context ->
      WebView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT
        )
        webViewClient = object : WebViewClient() {
          // Keep navigation inside the webview itself
        }
        webChromeClient = WebChromeClient()
        setBackgroundColor(android.graphics.Color.BLACK)
        
        settings.apply {
          javaScriptEnabled = true
          domStorageEnabled = true
          databaseEnabled = true
          allowFileAccess = true
          allowContentAccess = true
          mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
          useWideViewPort = true
          loadWithOverviewMode = true
        }
        
        loadUrl("file:///android_asset/index.html")
        webViewRef = this
      }
    },
    modifier = Modifier.fillMaxSize(),
    update = { webView ->
      webViewRef = webView
    }
  )
}

