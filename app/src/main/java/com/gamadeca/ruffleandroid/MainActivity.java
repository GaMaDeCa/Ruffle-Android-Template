package com.gamadeca.ruffleandroid;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

/**
 * Main activity that hosts a WebView to display Flash content using Ruffle emulator.
 * 
 * This activity:
 * 1. Initializes a WebView with JavaScript enabled
 * 2. Starts a local NanoHTTPD server to serve assets
 * 3. Loads the HTML page that contains the Ruffle Flash player
 * 
 * The local HTTP server is necessary because Ruffle needs to load the SWF file
 * via HTTP/HTTPS protocol, not via file:// protocol.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int PORT = 8080;
    
    private WebView webView;
    private SimpleHTTPServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        
        // Configure WebView
        setupWebView();
        
        // Start local HTTP server
        startServer();
    }

    /**
     * Configures the WebView with settings required for Ruffle to work properly.
     * Enables JavaScript, DOM storage, and file access.
     */
    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "Page loaded: " + url);
            }
        });
        
        webView.setWebChromeClient(new WebChromeClient());
    }

    /**
     * Starts the local HTTP server and loads the game page.
     * The server serves files from the assets directory.
     */
    private void startServer() {
        try {
            server = new SimpleHTTPServer(PORT, getAssets());
            server.start();
            Log.d(TAG, "Server started on port " + PORT);
            
            // Load the game page
            String url = "http://localhost:" + PORT + "/index.html";
            webView.loadUrl(url);
            
            Toast.makeText(this, "Server started on port " + PORT, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e(TAG, "Error starting server", e);
            Toast.makeText(this, "Error starting server: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (server != null) {
            server.stop();
            Log.d(TAG, "Server stopped");
        }
    }

    /**
     * Allows WebView to handle back button navigation.
     */
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
