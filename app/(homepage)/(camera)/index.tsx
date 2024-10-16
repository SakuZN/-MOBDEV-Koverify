import React, { useEffect } from "react";
import { useState } from "react";
import {
  CameraView,
  CameraType,
  useCameraPermissions,
  CameraViewRef,
} from "expo-camera";
import { View, StyleSheet, Image, BackHandler } from "react-native";
import { router } from "expo-router";
import { scan as ScanAreaIcon } from "@/constants/Images";
import { SafeAreaView } from "react-native-safe-area-context";
const Index = () => {
  const [permission] = useCameraPermissions();
  const [facing, setFacing] = useState<CameraType>("back");

  if (!permission) {
    // Index permissions are still loading.
    console.log("loading");
    return <View />;
  }

  if (!permission.granted) {
    router.replace("/(homepage)/(camera)/permission");
  }

  return (
    <View
      style={{
        flex: 1,
        justifyContent: "center",
      }}
    >
      <Image source={ScanAreaIcon} style={styles.imageScan} />
      <CameraView
        style={{
          flex: 1,
        }}
        facing={facing}
        animateShutter
      />
    </View>
  );
};

export default Index;
const styles = StyleSheet.create({
  imageScan: {
    position: "absolute",
    top: 75,
    left: 0,
    right: 0,
    bottom: 0,
    justifyContent: "center",
    alignItems: "center",
    width: "100%",
    height: "75%",
    zIndex: 10,
  },
});
