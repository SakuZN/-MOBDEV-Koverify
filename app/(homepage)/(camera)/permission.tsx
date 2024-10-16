import React, { useCallback, useEffect, useState } from "react";
import { Linking } from "react-native";
import { StyleSheet, View, Image } from "react-native";
import { Text } from "@/components/ui/text";
import { ScrollView } from "react-native-gesture-handler";
import { Button } from "@/components/ui/button";
import { useCameraPermissions } from "expo-camera";
import { camera as CameraIcon } from "@/constants/Images";
import { router, useFocusEffect } from "expo-router";
import { SafeAreaView } from "react-native-safe-area-context";
const PermissionsPage = () => {
  const [permission, requestPermission] = useCameraPermissions();
  if (!permission) {
    // Index permissions are still loading.
    return <View />;
  }
  if (permission && permission.granted) {
    router.replace("/(homepage)/(camera)");
  }

  const requestCameraPermission = async () => {
    console.log("Requesting (camera) permission...");
    const permission = await requestPermission();
    console.log(`Camera permission status: ${permission}`);

    if (permission.status === "denied") await Linking.openSettings();
    //@ts-ignore
    else if (permission.granted) router.replace("/(homepage)/(camera)");
  };

  return (
    <SafeAreaView
      className="bg-primary"
      style={{
        flex: 1,
        alignItems: "center",
      }}
    >
      <ScrollView
        className="mt-5 w-full"
        showsHorizontalScrollIndicator={false}
        showsVerticalScrollIndicator={false}
      >
        <View className="my-4 flex w-full flex-col items-center gap-4 px-8">
          <Text fontFamily="SFMONO" fontVariant="Bold" className="text-2xl">
            Welcome KoveriUser!
          </Text>
          <Image
            source={CameraIcon}
            className="h-[200px] w-full"
            resizeMode="contain"
          />

          <View className="mt-10 flex flex-col gap-4">
            <Text variant="lead" className="text-center">
              Koverify needs access to your camera to scan QR codes.
            </Text>
            <Button className="bg-primary-foreground" size="lg">
              <Text
                fontFamily="SFPRO_DISPLAY"
                fontVariant="SemiBold"
                className="text-white"
              >
                Grant Camera Access
              </Text>
            </Button>
          </View>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

export default PermissionsPage;
