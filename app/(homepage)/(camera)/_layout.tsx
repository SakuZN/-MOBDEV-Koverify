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
          title: "Camera",
        }}
      />
      <Stack.Screen
        name={"permission"}
        options={{
          title: "Index Permission",
        }}
      />
    </Stack>
  );
};
export default CameraLayout;
const styles = StyleSheet.create({});
