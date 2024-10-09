import { ScrollView, View } from "react-native";
import React, { useEffect } from "react";
import { SafeAreaView } from "react-native-safe-area-context";
import { Button } from "../../components/ui/button";
import { Link } from "expo-router";
import { Text } from "../../components/ui/text";
import {
  Cat,
  Circle,
  Diamond,
  Egg,
  Hexagon,
  Moon,
  Octagon,
  Pill,
  Scan,
  ScanLine,
  Stethoscope,
} from "lucide-react-native";

const Index = () => {
  return (
    <SafeAreaView className="h-full bg-primary">
      <ScrollView>
        <View className="flex flex-1 flex-col items-stretch gap-4 px-6 py-3">
          <View className="flex flex-row items-center justify-between self-stretch">
            <Text className="display-text">Koverify</Text>
            <View className="flex flex-row items-start gap-1.5">
              <Button className="flex items-center rounded bg-secondary p-2 text-secondary shadow-sm">
                <Moon size={12} color={"#fff"} />
              </Button>

              <Button className="flex items-center rounded bg-secondary p-2 text-secondary shadow-sm">
                <Scan size={12} color={"#fff"} />
              </Button>
            </View>
          </View>
          <View className="flex flex-col items-start gap-6 self-stretch">
            <View className="flex flex-col items-start gap-2.5 self-stretch">
              <Text className="header-text">Quick Actions</Text>
              <View className="flex flex-row items-start gap-2.5 self-stretch">
                <Button className="panel flex flex-1 flex-col items-end gap-3.5 p-5 pb-4">
                  <ScanLine size={40} color={"#248C69"} />
                  <Text className="secondary-text">Scan Barcode</Text>
                </Button>
                <Button className="panel flex flex-1 flex-col items-end gap-3.5 p-5 pb-4">
                  <Circle size={40} color={"#248C69"} />
                  <Text className="secondary-text">All Products</Text>
                </Button>
              </View>
            </View>
            <View className="flex flex-col items-start gap-2.5 self-stretch">
              <Text className="header-text">View Food Products</Text>
              <View className="flex flex-row items-start gap-2.5 self-stretch">
                <Button className="panel flex flex-1 flex-col items-end gap-3.5 p-5 pb-4">
                  <Egg size={40} color={"#248C69"} />
                  <Text className="secondary-text">All Food</Text>
                </Button>
                <Button className="panel flex flex-1 flex-col items-end gap-3.5 p-5 pb-4">
                  <Diamond size={40} color={"#248C69"} />
                  <Text className="secondary-text">Safe Food</Text>
                </Button>
              </View>
              <View className="flex flex-row items-start gap-2.5 self-stretch">
                <Button className="panel flex flex-1 flex-col items-end gap-3.5 p-5 pb-4">
                  <Hexagon size={40} color={"#248C69"} />
                  <Text className="secondary-text">Moderate Food</Text>
                </Button>
                <Button className="panel flex flex-1 flex-col items-end gap-3.5 p-5 pb-4">
                  <Octagon size={40} color={"#248C69"} />
                  <Text className="secondary-text">High-Risk Food</Text>
                </Button>
              </View>
            </View>
            <View className="flex flex-col items-start gap-2.5 self-stretch">
              <Text className="header-text">View Drug Products</Text>
              <View className="flex flex-row items-start gap-2.5 self-stretch">
                <Button className="panel flex flex-1 flex-col items-end gap-3.5 p-5 pb-4">
                  <Pill size={40} color={"#248C69"} />
                  <Text className="secondary-text">All Drugs</Text>
                </Button>
                <Button className="panel flex flex-1 flex-col items-end gap-3.5 p-5 pb-4">
                  <Stethoscope size={40} color={"#248C69"} />
                  <Text className="secondary-text">Human Drugs</Text>
                </Button>
              </View>
              <View className="flex flex-row items-start gap-2.5 self-stretch">
                <Button className="panel flex max-w-[48.5%] flex-1 flex-col items-end gap-3.5 p-5 pb-4">
                  <Cat size={40} color={"#248C69"} />
                  <Text className="secondary-text">Veterinary Drugs</Text>
                </Button>
              </View>
            </View>
          </View>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};
export default Index;
