import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';

const MethodChannel _channel = const MethodChannel('flutter_gsyplayer');

Future<Null> play({String url, String title, bool cache}) async {
  assert(url != null);
  Map<String, Object> map = {
    "url": url,
    "cache": cache,
    "title": title,
  };
  await _channel.invokeMethod(
    'play',
    map,
  );
}

//typedef FullScreenCallBack = Function(bool);
class GSYPlayer extends StatefulWidget {
  final String url;
  final bool autoPlay;
  final bool cache;

  final Function onBackPress;

//  final FullScreenCallBack onFullScreen;

//  GsyplayerController({
//    this.onBackPress,
////    this.onFullScreen,
//  }) {
//    _listenOnBackPress();
////    _listenOnFullScreen();
//  }

  Future<Null> _listenOnBackPress() async {
    await _channel.invokeMethod('onBackPress');
    onBackPress ?? onBackPress();
  }

//  Future<Null> _listenOnFullScreen() async {
//    bool isFullScreen = await _channel.invokeMethod('onFullScreen');
//    onFullScreen ?? onFullScreen(isFullScreen);
//  }

  GSYPlayer({
    this.url,
    this.autoPlay: false,
    this.onBackPress,
    this.cache: true,
  }) {
    _listenOnBackPress();
  }

  @override
  State<StatefulWidget> createState() => _GSYPlayerState();
}

class _GSYPlayerState extends State<GSYPlayer> {
  @override
  Widget build(BuildContext context) {
    return Stack(
      children: <Widget>[
        AndroidView(
          viewType: "gsyplayer",
          creationParamsCodec: const StandardMessageCodec(),
          creationParams: {
            "url": widget.url,
            "autoPlay": widget.autoPlay,
            "cache": widget.cache,
          },
        ),
//        Center(
//          child: GestureDetector(
//            child: CircleAvatar(
//              child: Icon(Icons.play_arrow),
//            ),
//            onTap: () {
//              play(url: widget.url, cache: widget.cache);
//            },
//          ),
//        )
      ],
    );
  }
}
