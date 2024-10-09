import { View } from "react-native";
import { useEffect, useState } from "react";
import { SafeAreaView } from "react-native-safe-area-context";
import { Button } from "@/components/ui/button";
import { ArrowRight, Circle } from "lucide-react-native";
import { router } from "expo-router";
import { Text } from "@/components/ui/text";
import { LinearGradient } from "expo-linear-gradient";

export default function Index() {
  const [counter, setCounter] = useState(0);

  var header_messages = [
    "Welcome to Koverify",
    "Find your products’ nutritional data fast",
    "Scan a barcode or search for your item",
    "⚠️ Not made to replace professional medical help",
  ];
  var button_text = "Get Started";
  var header_message = header_messages[counter];

  const changeText = () => {
    setCounter(counter + 1);

    if (counter === 3) {
      //router.replace("/(home)/");
    }
  };

  return (
    <SafeAreaView
      style={{
        flex: 1,
      }}
    >
      <LinearGradient
        // Background Linear Gradient
        colors={["#88F07C", "#248C2C"]}
        start={[0, 0]}
        end={[1, 1]}
        className="flex-1 items-center justify-center"
        style={{
          padding: 20,
        }}
      >
        <View className="mb-64 flex flex-col items-end gap-10">
          <Text className="text-3xl font-bold text-white">
            {header_message}
          </Text>
          <Button
            onPress={changeText}
            className="flex-row items-center rounded-md bg-gray-100 p-4 shadow-lg"
          >
            <Text className="text-base font-semibold text-black">
              {counter === 3 ? "Get Started" : "Next"}
            </Text>
            <ArrowRight height={20} width={20} />
          </Button>
        </View>
        <View className="flex-row items-center justify-end gap-2 self-stretch">
          <Circle
            height={12}
            width={12}
            color={counter === 0 ? "#C8CADB" : "#9B9CA8"}
          />
          <Circle
            height={12}
            width={12}
            color={counter === 1 ? "#C8CADB" : "#9B9CA8"}
          />
          <Circle
            height={12}
            width={12}
            color={counter === 2 ? "#C8CADB" : "#9B9CA8"}
          />
          <Circle
            height={12}
            width={12}
            color={counter === 3 ? "#C8CADB" : "#9B9CA8"}
          />
        </View>
      </LinearGradient>
    </SafeAreaView>
  );
}
