# Petal

A modern, open-source imageboard browser for Android.

Petal is a fork of [Clover](https://github.com/chandevel/Clover), modernized for Android 16 with Material You design, updated libraries, and improved performance.

## Features

- Browse 4chan and 8chan
- Material You dynamic theming (adapts to your wallpaper)
- Edge-to-edge display
- Inline replying and thread watching
- Image and video viewer with smooth transitions
- Background image preloading for instant viewing
- Album downloads
- Content filtering and search
- Multi-window and split-screen support

## What's New (vs Clover)

- **Android 16 support** (SDK 36)
- **Material 3 / Material You** dynamic color themes
- **Edge-to-edge** transparent system bars
- **Glide** image loading (replaces Volley)
- **Media3** video playback (replaces ExoPlayer 2)
- **Predictive back gesture** support
- **Modernized dependencies** (OkHttp 4, Gson 2.11, Jsoup 1.18, etc.)
- **Scoped storage** support for downloads on Android 10+

## Building

Requirements:
- Android Studio (latest)
- JDK 17
- Android SDK 36

```bash
git clone https://github.com/Kev1nLevin/Petal.git
cd Petal
./gradlew assembleDefaultDebug
```

The APK will be at `app/build/outputs/apk/default/debug/app-default-debug.apk`.

## License

Petal is free software, licensed under the [GNU General Public License v3](COPYING.txt).
