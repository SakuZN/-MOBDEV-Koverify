import { Modal, Pressable, StyleSheet, View } from "react-native";
import React, { useState } from "react";
import { Text } from "@/components/ui/text";
import { Factory, Filter, Frame, X } from "lucide-react-native";
import { Dropdown } from "react-native-element-dropdown";
import { Calendar } from "../ui/calendar";
import { Theme } from "@react-navigation/native";
import { Colors } from "react-native/Libraries/NewAppScreen";
import { Button } from "../ui/button";

const ProductDetailsPanel = () => {
  const [open, setOpen] = useState(false);
  const [value, setValue] = useState("");
  const [isFocus, setIsFocus] = useState(false);

  const data = [
    { label: "Item 1", value: "1" },
    { label: "Item 2", value: "2" },
    { label: "Item 3", value: "3" },
    { label: "Item 4", value: "4" },
    { label: "Item 5", value: "5" },
    { label: "Item 6", value: "6" },
    { label: "Item 7", value: "7" },
    { label: "Item 8", value: "8" },
  ];

  return (
    <View className="panel flex flex-col items-start gap-0.5 self-stretch p-4">
      <View className="flex flex-row items-center gap-1.5 self-stretch">
        <Text
          fontFamily="Inter"
          fontVariant="Bold"
          style={{
            fontSize: 17,
            lineHeight: 16,
          }}
        >
          Actrapid
        </Text>
        <Text
          fontFamily="Inter"
          fontVariant="Medium"
          style={{
            fontSize: 12,
            lineHeight: 16,
          }}
        >
          - Human Insulin
        </Text>
      </View>
      <View className="flex flex-row items-start justify-between self-stretch pr-6">
        <View className="flex flex-col items-start gap-0.5">
          <View className="flex flex-row items-center gap-1">
            <Factory size={12} color={"#212121"} />
            <Text
              fontFamily="SFMONO"
              fontVariant="Medium"
              style={{
                fontSize: 12,
                letterSpacing: 1.25,
              }}
            >
              MANUFACTURER
            </Text>
          </View>
          <Text
            fontFamily="Inter"
            fontVariant="SemiBold"
            style={{
              fontSize: 14,
              lineHeight: 6,
            }}
          >
            Novo Nordisk
          </Text>
        </View>
        <View className="flex flex-col items-start gap-0.5 pb-1.5">
          <View className="flex flex-row items-center gap-1">
            <Frame size={12} color={"#212121"} />
            <Text
              fontFamily="SFMONO"
              fontVariant="Medium"
              style={{
                fontSize: 12,
                letterSpacing: 1.25,
              }}
            >
              CLASSIFICATION
            </Text>
          </View>
          <Text
            fontFamily="Inter"
            fontVariant="SemiBold"
            style={{
              fontSize: 14,
              lineHeight: 6,
            }}
          >
            OTC Drug
          </Text>
        </View>
      </View>
    </View>
  );
};
export default ProductDetailsPanel;
