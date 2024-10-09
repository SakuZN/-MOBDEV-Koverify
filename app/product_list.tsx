import { View } from "react-native";
import { useState } from "react";
import { SafeAreaView } from "react-native-safe-area-context";
import { Button } from "@/components/ui/button";
import { MoveRight } from "@/lib/icons";
import { Text } from "@/components/ui/text";
import { LinearGradient } from "expo-linear-gradient";
import Animated, {
  useSharedValue,
  useAnimatedStyle,
  withTiming,
  interpolateColor,
  runOnJS,
} from "react-native-reanimated";
import { cn } from "@/lib/utils";
import { router } from "expo-router";
import { ArrowLeft, Factory, Filter, Frame, Search } from "lucide-react-native";
import { Input } from "@/components/ui/input";

export default function Index() {
  return (
    <SafeAreaView style={{ flex: 1 }}>
      <View className="flex flex-col gap-4 bg-primary p-3 px-5">
        <View className="flex flex-row items-center justify-between self-stretch">
          <ArrowLeft size={24} color={"#1a1a1a"} />
          <Text className="header-text">All Drug Products</Text>
          <Text className="display-text">K</Text>
        </View>
        <View className="flex flex-row items-start gap-2 self-stretch">
          <View className="searchbar flex flex-1 flex-row items-center gap-2 px-4 py-3">
            <Search strokeWidth={3} size={16} color={"#258C6A"} />
            <Input className="" placeholder="Search" />
          </View>
          <View className="panel flex items-center gap-2 self-stretch p-3">
            <Filter strokeWidth={2} size={20} color={"#258C6A"} />
          </View>
        </View>
        <View className="flex flex-col items-start gap-3 self-stretch">
          <View className="panel flex flex-col items-start gap-0.5 self-stretch p-3">
            <View className="flex flex-row items-center gap-1.5 self-stretch">
              <Text className="card-main-text">Actrapid</Text>
              <Text className="card-secondary-text">- Human Insulin</Text>
            </View>
            <View className="flex flex-row items-start justify-between self-stretch pr-6">
              <View className="flex flex-col items-start gap-0.5">
                <View className="flex flex-row items-center gap-1">
                  <Factory size={12} color={"#212121"} />
                  <Text className="card-label-text">MANUFACTURER</Text>
                </View>
                <Text className="card-item-text">Novo Nordisk</Text>
              </View>
              <View className="flex flex-col items-start gap-0.5">
                <View className="flex flex-row items-center gap-1">
                  <Frame size={12} color={"#212121"} />
                  <Text className="card-label-text">CLASSIFICATION</Text>
                </View>
                <Text className="card-item-text">OTC Drug</Text>
              </View>
            </View>
          </View>
        </View>
      </View>
    </SafeAreaView>
  );
}
