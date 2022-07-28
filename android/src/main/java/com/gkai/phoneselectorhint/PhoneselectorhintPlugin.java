package com.gkai.phoneselectorhint;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.HintRequest;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.ActivityResultListener;

/** PhoneselectorhintPlugin */
public class PhoneselectorhintPlugin implements FlutterPlugin, ActivityAware, MethodCallHandler
         {
  private static final int CREDENTIAL_PICKER_REQUEST =120 ;

  private MethodChannel channel;
  private Activity activity;
  private Result pendingHintResult;
  private final PluginRegistry.ActivityResultListener activityResultListener =
          new ActivityResultListener(

          ) {

            @Override
            public boolean onActivityResult(int requestCode, int resultCode, Intent data) {

              if(requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == Activity.RESULT_OK  ){

                  Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                  final String phoneNumber = credential.getId();

                  pendingHintResult.success(phoneNumber);
                }else{
                  pendingHintResult.success(null);
                }
                return true;
              }


          };


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "phoneselectorhint");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

    switch (call.method){
      case "getPlatformVersion":result.success("Android ");break;
      case "pendingHintResult":
        pendingHintResult=result;
        phoneSelector();
        break;
      default: result.notImplemented();break;
    }

  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }



  private void phoneSelector(){

    /*if(!isSimSupport()){
      if(pendingHintResult!=null){
        pendingHintResult.success(null);
      }
      return;
    }*/

    HintRequest hintRequest = new HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build();


    PendingIntent intent = Credentials.getClient(activity).getHintPickerIntent(hintRequest);

    try
    {
      activity.startIntentSenderForResult(intent.getIntentSender(), CREDENTIAL_PICKER_REQUEST,
              null, 0, 0
              ,0,new Bundle());
    }
    catch (IntentSender.SendIntentException e)
    {
      e.printStackTrace();
    }
  }


  /*public boolean onActivityResult(int requestCode, int resultCode, Intent data) {

    //super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == CredentialsApi.ACTIVITY_RESULT_ADD_ACCOUNT)
    {
      // Obtain the phone number from the result
      Credential credentials = data.getParcelableExtra(Credential.EXTRA_KEY);





    }
    else if(requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == CredentialsApi.ACTIVITY_RESULT_OTHER_ACCOUNT){

    }

    else if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE)
    {
    }





    return true;
  }
*/

  public boolean isSimSupport(){
    TelephonyManager telephonyManager =(TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
    return !(telephonyManager.getSimState()==TelephonyManager.SIM_STATE_ABSENT);
  }
  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
      activity = binding.getActivity();
      binding.addActivityResultListener(activityResultListener);
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }
  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
    binding.addActivityResultListener(activityResultListener);
  }


  @Override
  public void onDetachedFromActivity() {

  }
}



