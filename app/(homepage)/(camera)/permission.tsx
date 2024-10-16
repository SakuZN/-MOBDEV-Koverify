import React from "react";
import { Linking } from "react-native";
import { View, Image } from "react-native";
import { Text } from "@/components/ui/text";
import { ScrollView } from "react-native-gesture-handler";
import { Button } from "@/components/ui/button";
import { useCameraPermissions } from "expo-camera";
import { camera as CameraIcon } from "@/constants/Images";
import { router } from "expo-router";
import { SafeAreaView } from "react-native-safe-area-context";
import { LinearGradient } from "expo-linear-gradient";
const PermissionsPage = () => {
  const [permission, requestPermission] = useCameraPermissions();
  if (!permission) {
    // Index permissions are still loading.
    return <View />;
  }
  if (permission && permission.granted) {
    router.replace("/(homepage)/(camera)");
    return;
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
    <LinearGradient
      colors={["#88F07C", "#248C2C"]}
      start={[0, 0]}
      end={[1, 1]}
      className="flex-1 p-8"
    >
      <ScrollView
        className="mt-5 w-full"
        showsHorizontalScrollIndicator={false}
        showsVerticalScrollIndicator={false}
        contentContainerClassName="flex-1 items-center justify-center"
      >
        <View className="my-4 w-full flex-1 flex-col items-center justify-center gap-4 px-8">
          <Text
            fontFamily="SFMONO"
            fontVariant="Bold"
            className="text-center text-4xl text-primary"
          >
            Welcome KoveriUser!
          </Text>
          <Image
            source={CameraIcon}
            className="h-[300px] w-full"
            resizeMode="contain"
          />

          <View className="mt-10 flex flex-col gap-4">
            <Text variant="lead" className="text-center text-primary/80">
              Koverify needs access to your camera to scan QR codes.
            </Text>
            <Button className="bg-primary-foreground" size="lg">
              <Text
                fontFamily="SFPRO_DISPLAY"
                fontVariant="SemiBold"
                className="text-primary"
                onPress={requestCameraPermission}
              >
                Grant Camera Access
              </Text>
            </Button>
          </View>
        </View>
      </ScrollView>
    </LinearGradient>
  );
};

export default PermissionsPage;
