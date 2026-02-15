# Ruffle-Android-Template

This is an Android template project that integrates the Ruffle Flash emulator with NanoHTTPD to run Flash games inside a WebView.

## Features

- ✅ Ruffle Flash Emulator (loaded from CDN)
- ✅ NanoHTTPD local web server
- ✅ WebView integration
- ✅ Serves content from Android assets
- ✅ Full JavaScript support
- ✅ Ready to use template

## Project Structure

```
Ruffle-Android-Template/
├── app/
│   ├── build.gradle                    # App-level Gradle configuration
│   ├── proguard-rules.pro             # ProGuard rules
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml    # App manifest with permissions
│           ├── java/com/gamadeca/ruffleandroid/
│           │   ├── MainActivity.java       # Main activity with WebView
│           │   └── SimpleHTTPServer.java   # NanoHTTPD server implementation
│           ├── res/
│           │   ├── layout/
│           │   │   └── activity_main.xml   # Main activity layout
│           │   ├── values/
│           │   │   └── strings.xml         # String resources
│           │   └── drawable/
│           │       └── ic_launcher.xml     # App icon
│           └── assets/
│               ├── index.html              # HTML page with Ruffle integration
│               └── game.swf                # Your Flash game file (add your own)
├── build.gradle                       # Project-level Gradle configuration
├── settings.gradle                    # Project settings
└── gradle.properties                  # Gradle properties
```

## Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/GaMaDeCa/Ruffle-Android-Template.git
   cd Ruffle-Android-Template
   ```

2. **Add your Flash game:**
   - Place your `.swf` file in `app/src/main/assets/`
   - Name it `game.swf` or update the filename in `index.html`

3. **Open in Android Studio:**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory
   - Wait for Gradle sync to complete
   - Android Studio will automatically download the Gradle wrapper on first sync

4. **Build and Run:**
   - Connect an Android device or start an emulator
   - Click the "Run" button (or press Shift+F10)
   - The app will install and launch automatically

### Alternative: Command Line Build

If you prefer to build from the command line:

```bash
# On Linux/Mac
./gradlew assembleDebug

# On Windows
gradlew.bat assembleDebug

# Install on connected device
./gradlew installDebug
```

Note: On first build, Gradle will download the wrapper and all dependencies. This may take several minutes.

## How It Works

1. **MainActivity** initializes a WebView and starts a NanoHTTPD server on port 8080
2. **SimpleHTTPServer** serves files from the `assets` directory
3. The WebView loads `http://localhost:8080/index.html`
4. **index.html** loads Ruffle from CDN and initializes the Flash player
5. Ruffle loads and runs the SWF file specified in the HTML

## Customization

### Change the SWF file

Edit `app/src/main/assets/index.html` and update the `swfUrl` variable:

```javascript
const swfUrl = "your-game-name.swf";
```

### Change the server port

Edit `MainActivity.java` and update the `PORT` constant:

```java
private static final int PORT = 8080; // Change this to your desired port
```

### Customize the HTML/CSS

Edit `app/src/main/assets/index.html` to customize the appearance and behavior of the player.

### Use local Ruffle instead of CDN

1. Download Ruffle from https://ruffle.rs/
2. Extract the files to `app/src/main/assets/ruffle/`
3. Update `index.html` to load from local files:
   ```html
   <script src="ruffle/ruffle.js"></script>
   ```

## Requirements

- Android Studio Arctic Fox or later
- Android SDK 24 (Android 7.0) or higher
- Java 8 or later

## Dependencies

- AndroidX AppCompat
- Material Components
- ConstraintLayout
- NanoHTTPD 2.3.1

## Permissions

The app requires the following permissions:
- `INTERNET` - To load Ruffle from CDN and enable web content
- `ACCESS_NETWORK_STATE` - To check network connectivity

## Troubleshooting

### Game not loading

1. Ensure your `.swf` file is in the `assets` folder
2. Check the filename matches what's specified in `index.html`
3. Check Android Studio's Logcat for error messages

### Server not starting

1. Make sure no other app is using port 8080
2. Check Logcat for error messages
3. Try changing the port number

### WebView blank screen

1. Ensure JavaScript is enabled (it is by default)
2. Check for JavaScript errors in Chrome DevTools
3. Enable USB debugging and use `chrome://inspect` to debug the WebView

## License

This is a template project - feel free to use it for your own projects.

## Credits

- [Ruffle](https://ruffle.rs/) - Flash Player emulator
- [NanoHTTPD](https://github.com/NanoHttpd/nanohttpd) - Lightweight HTTP server
