import { Stack } from "expo-router";
import * as React from "react";
import { View } from "react-native";
import { Text } from "@/components/ui/text";
import { Button } from "@/components/ui/button";
import { Moon, Scan } from "lucide-react-native";
import { router } from "expo-router";

export default function HomepageLayout() {
  return (
    <Stack>
      <Stack.Screen
        name="index"
        options={{
          headerBackTitle: "Back",
          headerTitleAlign: "left",
          headerTitle(props) {
            return <Text className="display-text pl-3">Koverify</Text>;
          },
          headerRight: () => {
            return (
              <View className="flex flex-row items-start gap-1.5 pr-6">
                <Button className="flex items-center rounded bg-secondary p-2 text-secondary shadow-sm">
                  <Moon size={12} color={"#fff"} />
                </Button>

                <Button
                  className="flex items-center rounded bg-secondary p-2 text-secondary shadow-sm"
                  onPress={() => {
                    //@ts-ignore
                    router.push("/camera");
                  }}
                >
                  <Scan size={12} color={"#fff"} />
                </Button>
              </View>
            );
          },
        }}
      />
      <Stack.Screen
        name="drug/[type]"
        //@ts-ignore
        options={({ route }) => ({
          headerTitleAlign: "center",
          headerTitle() {
            return (
              <Text
                fontFamily="SFPRO_DISPLAY"
                fontVariant="Medium"
                style={{
                  fontSize: 16,
                }}
              >
                {
                  //@ts-ignore
                  StyleDrugScreen(route.params.type)
                }
              </Text>
            );
          },
          headerRight: () => {
            return <Text className="display-text pr-5">K</Text>;
          },
        })}
      />
      <Stack.Screen
        name="food/[type]"
        options={({ route }) => ({
          headerTitleAlign: "center",
          headerTitle() {
            return (
              <Text
                fontFamily="SFPRO_DISPLAY"
                fontVariant="Medium"
                style={{
                  fontSize: 16,
                }}
              >
                {
                  //@ts-ignore
                  StyleFoodScreen(route.params.type)
                }
              </Text>
            );
          },
          headerRight: () => {
            return <Text className="display-text pr-5">K</Text>;
          },
        })}
      />
      <Stack.Screen
        name="all-products"
        //@ts-ignore
        options={({ route }) => ({
          headerTitleAlign: "center",
          headerTitle() {
            return (
              <Text
                fontFamily="SFPRO_DISPLAY"
                fontVariant="Medium"
                style={{
                  fontSize: 16,
                }}
              >
                {"All Products"}
              </Text>
            );
          },
          headerRight: () => {
            return <Text className="display-text pr-5">K</Text>;
          },
        })}
      />
      <Stack.Screen
        name="(camera)"
        options={({ route }) => ({
          headerShown: false,
        })}
      />
    </Stack>
  );
}

function StyleDrugScreen(type: string): string {
  switch (type) {
    case "all":
      return "All Drug Products";
    case "human":
      return "Human Drug Products";
    case "vet":
      return "Veterinary Drug Products";
    default:
      return "Drug Products";
  }
}

function StyleFoodScreen(type: string): string {
  switch (type) {
    case "all":
      return "All Food Products";
    case "h_risk":
      return "High-Risk Food Products";
    case "m_risk":
      return "Medium-Risk Food Products";
    case "l_risk":
      return "Low-Risk Food Products";
    case "raw":
      return "Raw Food Products";
    default:
      return "Food Products";
  }
}
