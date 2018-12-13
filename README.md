# flutter_gsyplayer

A flutter video player base on [GSYVideoPlayer](https://github.com/CarGuo/GSYVideoPlayer)

## Android is only supported for now
when report couldn't find "libflutter.so" in Android Release Mode
```
flutter build apk --release --target-platform android-arm64
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
