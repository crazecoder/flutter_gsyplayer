import 'package:flutter/material.dart';
import 'package:flutter_gsyplayer/flutter_gsyplayer.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  @override
  void initState() {
    super.initState();
//    play(url: "https://www4.yuboyun.com/hls/2018/12/19/TPJezSha/playlist.m3u8",cache: false);
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: GSYPlayer(
            url:
                "https://cdn-4.haku99.com/hls/2019/02/13/z3KVXvYN/playlist.m3u8",
            autoPlay: true,
            onBackPress: (){
              print("back");
            },
          ),
        ),
      ),
    );
  }
}
