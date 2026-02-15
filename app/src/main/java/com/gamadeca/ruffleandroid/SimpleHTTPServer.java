package com.gamadeca.ruffleandroid;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Simple HTTP server using NanoHTTPD to serve files from Android assets.
 * 
 * This server is used to serve the HTML page and SWF files to the WebView
 * because Ruffle requires files to be loaded via HTTP/HTTPS protocol.
 * 
 * The server:
 * - Listens on the specified port (default 8080)
 * - Serves all files from the Android assets directory
 * - Automatically determines MIME types based on file extensions
 * - Supports common web file types (HTML, JS, CSS, images, SWF, etc.)
 */
public class SimpleHTTPServer extends NanoHTTPD {
    private static final String TAG = "SimpleHTTPServer";
    private final AssetManager assetManager;
    
    // MIME type mappings
    private static final Map<String, String> MIME_TYPES = new HashMap<>();
    
    static {
        MIME_TYPES.put("html", "text/html");
        MIME_TYPES.put("htm", "text/html");
        MIME_TYPES.put("js", "application/javascript");
        MIME_TYPES.put("json", "application/json");
        MIME_TYPES.put("css", "text/css");
        MIME_TYPES.put("png", "image/png");
        MIME_TYPES.put("jpg", "image/jpeg");
        MIME_TYPES.put("jpeg", "image/jpeg");
        MIME_TYPES.put("gif", "image/gif");
        MIME_TYPES.put("svg", "image/svg+xml");
        MIME_TYPES.put("wasm", "application/wasm");
        MIME_TYPES.put("swf", "application/x-shockwave-flash");
        MIME_TYPES.put("txt", "text/plain");
    }

    /**
     * Creates a new HTTP server.
     * 
     * @param port The port to listen on
     * @param assetManager The Android AssetManager to access files
     * @throws IOException If the server cannot start
     */
    public SimpleHTTPServer(int port, AssetManager assetManager) throws IOException {
        super(port);
        this.assetManager = assetManager;
    }

    /**
     * Handles incoming HTTP requests and serves files from assets.
     * 
     * @param session The HTTP session containing request information
     * @return HTTP response with the requested file or 404 error
     */
    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        
        // Remove leading slash
        if (uri.startsWith("/")) {
            uri = uri.substring(1);
        }
        
        // Default to index.html
        if (uri.isEmpty() || uri.equals("/")) {
            uri = "index.html";
        }
        
        Log.d(TAG, "Serving: " + uri);
        
        try {
            // Try to load the file from assets
            InputStream inputStream = assetManager.open(uri);
            
            // Determine MIME type based on file extension
            String mimeType = getMimeType(uri);
            
            return newChunkedResponse(Response.Status.OK, mimeType, inputStream);
        } catch (IOException e) {
            Log.e(TAG, "File not found: " + uri, e);
            return newFixedLengthResponse(Response.Status.NOT_FOUND, MIME_PLAIN, "Error 404: File not found");
        }
    }
    
    /**
     * Determines the MIME type based on file extension.
     * 
     * @param filename The name of the file
     * @return The MIME type string
     */
    private String getMimeType(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            String extension = filename.substring(dotIndex + 1).toLowerCase();
            String mimeType = MIME_TYPES.get(extension);
            if (mimeType != null) {
                return mimeType;
            }
        }
        return "application/octet-stream";
    }
}
