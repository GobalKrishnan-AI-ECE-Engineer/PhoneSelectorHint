import 'dart:async';

import 'package:flutter/services.dart';

class Phoneselectorhint {
  static const MethodChannel _channel = MethodChannel('phoneselectorhint');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String?> get hint async {
    final String? hint = await _channel.invokeMethod('pendingHintResult');
    print("-----#--- $hint");
    return hint;
  }
}
