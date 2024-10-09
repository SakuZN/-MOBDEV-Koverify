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

export default function Index() {
  const [counter, setCounter] = useState(0);
  const progress = useSharedValue(0);
  const translateX = useSharedValue(0);

  const headerMessages = [
    "Welcome to Koverify",
    "Find your products’ nutritional data fast",
    "Scan a barcode or search for your item",
    "⚠️ Not made to replace professional medical help",
  ];
  const headerMessage = headerMessages[counter];
  const buttonText = counter === 0 ? "Get Started" : "Continue";

  const updateCounter = () => {
    setCounter((prev) => (prev === 3 ? 0 : prev + 1));
  };

  const changeText = () => {
    // Slide out to the left
    translateX.value = withTiming(-300, { duration: 300 }, () => {
      // Once the slide-out animation is complete, use runOnJS to call updateCounter.
      runOnJS(updateCounter)();

      // Reset translateX to appear from the right.
      translateX.value = 300;

      // Animate back to center smoothly.
      translateX.value = withTiming(0, { duration: 300 });
    });

    progress.value = withTiming(progress.value === 3 ? 0 : progress.value + 1, {
      duration: 300,
    });
  };

  const animatedStyle = useAnimatedStyle(() => ({
    transform: [{ translateX: translateX.value }],
  }));

  const circleAnimatedStyle = (index: number) =>
    useAnimatedStyle(() => {
      const backgroundColor = interpolateColor(
        progress.value,
        [index - 1, index, index + 1],
        ["#9B9CA8", "#C8CADB", "#9B9CA8"],
      );

      return {
        backgroundColor,
      };
    });

  return (
    <SafeAreaView style={{ flex: 1 }}>
      <LinearGradient
        colors={["#88F07C", "#248C2C"]}
        start={[0, 0]}
        end={[1, 1]}
        className="flex-1 items-center justify-center p-10"
      >
        <View className="flex flex-col items-end gap-10">
          <Animated.View style={animatedStyle}>
            <Text
              fontFamily="SFMONO"
              fontVariant="Bold"
              className="text-4xl text-white"
            >
              {headerMessage}
            </Text>
          </Animated.View>

          <Button
            onPress={changeText}
            className="flex flex-row items-center gap-2 rounded-md bg-gray-100 shadow-lg"
          >
            <Text
              fontFamily="SFMONO"
              fontVariant="Semibold"
              className="text-base text-black"
            >
              {buttonText}
            </Text>
            <MoveRight height={20} width={20} className="mt-1 text-black" />
          </Button>
        </View>
        <View className="absolute bottom-16 right-16 flex flex-row items-center justify-end gap-2 self-stretch p-5">
          {[0, 1, 2, 3].map((index) => (
            <Animated.View
              key={index}
              style={[
                {
                  width: 12,
                  height: 12,
                  borderRadius: 6,
                },
                circleAnimatedStyle(index),
              ]}
            />
          ))}
        </View>
      </LinearGradient>
    </SafeAreaView>
  );
}
