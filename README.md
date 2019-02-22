# flutter_gsyplayer

A flutter video player base on [GSYVideoPlayer](https://github.com/CarGuo/GSYVideoPlayer)

Waiting for GSYVideoPlayer adaptation androidx, if u can't wait, use [flutter_ijkplayer_beta](https://github.com/crazecoder/flutter_ijkplayer_beta) 

## Android is only supported for now
when report couldn't find "libflutter.so" in Android Release Mode

64-bit
```
flutter build apk --release --target-platform android-arm64
```
32-bit
```
flutter build apk --release --target-platform android-arm
```
To get the 32-bit version running I need to use:
```
defaultConfig { ndk{ abiFilters "armeabi-v7a" } }
```

when report  "Invoke-customs are only supported starting with Android O (--min-api 26)"
 in build release,add below code in android/app/build.gradle
```
android {
    compileOptions {
        sourceCompatibility 1.8
        targetCompatibility 1.8
    }
}
```

## Usage

To use this plugin, add below code in your pubspec.yaml file.
```
flutter_gsyplayer:
   git:
     url: git://github.com/crazecoder/flutter_gsyplayer.git
```

## Example
```
import 'package:flutter_gsyplayer/flutter_gsyplayer.dart';

play(url: url);
```
