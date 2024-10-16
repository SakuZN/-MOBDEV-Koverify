import React from "react";
import { useState } from "react";
import { CameraView, CameraType, useCameraPermissions } from "expo-camera";
import { Button, View, Text, TouchableOpacity } from "react-native";
import { router } from "expo-router";

const Index = () => {
  const [facing, setFacing] = useState<CameraType>("back");
  const [permission, requestPermission] = useCameraPermissions();

  if (!permission) {
    // Index permissions are still loading.
    return <View />;
  }

  if (!permission.granted) {
    router.replace("/(homepage)/(camera)/permission");
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

export default Index;
