#import "PhoneselectorhintPlugin.h"
#if __has_include(<phoneselectorhint/phoneselectorhint-Swift.h>)
#import <phoneselectorhint/phoneselectorhint-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "phoneselectorhint-Swift.h"
#endif

@implementation PhoneselectorhintPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftPhoneselectorhintPlugin registerWithRegistrar:registrar];
}
@end
