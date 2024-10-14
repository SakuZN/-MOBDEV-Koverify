import React from "react";
import { useState } from "react";
import { CameraView, CameraType, useCameraPermissions } from "expo-camera";
import { Button, View, Text, TouchableOpacity } from "react-native";

const Camera = () => {
  const [facing, setFacing] = useState<CameraType>("back");
  const [permission, requestPermission] = useCameraPermissions();

  if (!permission) {
    // Camera permissions are still loading.
    return <View />;
  }

  if (!permission.granted) {
    // Camera permissions are not granted yet.
    requestPermission();
    return (
      <View className="flex-1 justify-center">
        <Text className="pb-3 text-center">
          We need your permission to show the camera
        </Text>
        <Button onPress={requestPermission} title="grant permission" />
      </View>
    );
  }

  function toggleCameraFacing() {
    setFacing((current) => (current === "back" ? "front" : "back"));
  }

  return (
    <View className="flex-1 justify-center">
      <CameraView
        className="flex-1"
        facing={facing}
        barcodeScannerSettings={{
          barcodeTypes: ["ean13"],
        }}
        onBarcodeScanned={(data) => {
          // do stuff
        }}
      >
        <View className="m-[100%] flex-row bg-transparent"></View>
      </CameraView>
    </View>
  );
};

export default Camera;
