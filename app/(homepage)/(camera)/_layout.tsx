import { Stack } from "expo-router";

import { StyleSheet, Text, View } from "react-native";
import React from "react";

const CameraLayout = () => {
  return (
    <Stack
      screenOptions={{
        headerTitleAlign: "center",
      }}
    >
      <Stack.Screen
        name={"index"}
        options={{
          headerShown: true,
          headerBackVisible: true,
          headerTransparent: true,
          headerTitle: "",
        }}
      />
      <Stack.Screen
        name={"permission"}
        options={{
          headerShown: false,
        }}
      />
    </Stack>
  );
};
export default CameraLayout;
const styles = StyleSheet.create({});
