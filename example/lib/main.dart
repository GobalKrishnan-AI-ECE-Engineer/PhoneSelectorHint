import 'dart:async';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:phoneselectorhint/phoneselectorhint.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  String _hint = '';
  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  String? hint;
  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;

    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      platformVersion = await Phoneselectorhint.platformVersion ?? 'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
      hint = _hint;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Phone Selector Hint'),
          actions: [],
        ),
        persistentFooterButtons: [
          Text(
            "Done by Gobal Krishnan V, Flutter Developer & Engineer",
            style: TextStyle(fontSize: 8),
          )
        ],
        body: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text("Note: Click on the Hint button to pick mobile number"),
            Center(
              child: Text(
                'Mobile : $hint\n',
                style: TextStyle(fontSize: 23),
              ),
            ),
          ],
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () async {
            if (Platform.isAndroid) {
              try {
                _hint = await Phoneselectorhint.hint ?? '';
                setState(() {
                  hint = _hint;
                });
                print("phne - $_hint");
              } on PlatformException {
                _hint = 'phone number is not working';
              }
            } else {}
          },
          child: Text("Hint"),
        ),
      ),
    );
  }
}
