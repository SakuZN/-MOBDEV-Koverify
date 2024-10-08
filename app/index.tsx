import { View } from "react-native";
import { useEffect, useState } from "react";
import { SafeAreaView } from "react-native-safe-area-context";
import { Button } from "@/components/ui/button";
import { ArrowRight, Circle } from "lucide-react-native";
import { router } from "expo-router";

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

    if (counter == 3) {
      router.replace("/(home)/");
    }
  };

  return (
    <SafeAreaView className="start-container">
      <View className="start-main">
        <h1>{header_message}</h1>
        <Button onPress={changeText}>
          <span>{button_text}</span>
          <ArrowRight height={20} width={20} />
        </Button>
      </View>
      <div className="start-progress-buttons">
        <Circle height={12} width={12} color="#C8CADB" />
        <Circle height={12} width={12} color="#9B9CA8" />
        <Circle height={12} width={12} color="#9B9CA8" />
        <Circle height={12} width={12} color="#9B9CA8" />
      </div>
    </SafeAreaView>
  );
}
