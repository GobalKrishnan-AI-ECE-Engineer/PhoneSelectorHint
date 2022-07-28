import Flutter
import UIKit

public class SwiftPhoneselectorhintPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "phoneselectorhint", binaryMessenger: registrar.messenger())
    let instance = SwiftPhoneselectorhintPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }
}
