import { Modal, Pressable, StyleSheet, View } from "react-native";
import React, { useState } from "react";
import { Text } from "@/components/ui/text";
import { Factory, Filter, Frame, FrameIcon, X } from "lucide-react-native";
import { Dropdown } from "react-native-element-dropdown";
import { Calendar } from "../ui/calendar";
import { Theme } from "@react-navigation/native";
import { Colors } from "react-native/Libraries/NewAppScreen";
import { Button } from "../ui/button";
import ProductDetailsPanel from "./ProductDetailsPanel";

const ProductDetails = () => {
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
    <View className="flex content-center items-center self-stretch">
      <Modal
        animationType="fade"
        transparent={false}
        visible={open}
        onRequestClose={() => {
          setOpen(false);
        }}
      >
        <View className="flex content-center items-center">
          <View className="panel m-24 flex w-11/12 flex-col items-center gap-6 rounded-md bg-background p-6">
            <Pressable
              className="absolute right-5 top-5"
              onPress={() => setOpen(false)}
            >
              <X size={18} />
            </Pressable>
            <View className="flex flex-col gap-1.5 self-stretch">
              <Text
                fontFamily="SFPRO_DISPLAY"
                fontVariant="Medium"
                className="modal-header !text-left"
              >
                Product Details
              </Text>
              <View className="flex w-full flex-row content-between items-start gap-5 self-stretch pr-6">
                {/* Column 1 */}
                <View className="flex flex-col items-start gap-2">
                  <View className="flex flex-col items-start gap-0.5">
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
                        REG DETAILS
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
                      BR-1057
                    </Text>
                  </View>
                  <View className="flex flex-col items-start gap-0.5">
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
                      Prescription Drug
                    </Text>
                  </View>
                  <View className="flex flex-col items-start gap-0.5">
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
                        BRAND NAME
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
                      Levemir FlexPen
                    </Text>
                  </View>
                  <View className="flex flex-col items-start gap-0.5">
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
                        DOSAGE FORM
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
                      Injection Solution
                    </Text>
                  </View>
                </View>
                {/* Column 2 */}
                <View className="flex flex-col items-start gap-2">
                  <View className="flex flex-col items-start gap-0.5">
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
                        SKU
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
                      -
                    </Text>
                  </View>
                  <View className="flex flex-col items-start gap-0.5">
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
                        CATEGORY
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
                      -
                    </Text>
                  </View>
                  <View className="flex flex-col items-start gap-0.5">
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
                        GENERIC NAME
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
                      Insulin Detemir
                    </Text>
                  </View>
                  <View className="flex flex-col items-start gap-0.5">
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
                        DOSAGE STRENGTH
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
                      100 U/mL
                    </Text>
                  </View>
                </View>
              </View>
            </View>

            <Button
              onPress={() => setOpen(false)}
              className="flex flex-1 content-center items-center gap-1.5 self-stretch rounded-3xl bg-[#258C6A] py-4"
            >
              <Text
                fontFamily="SFPRO_DISPLAY"
                fontVariant="Regular"
                className="text-base/4 color-white"
              >
                GO BACK
              </Text>
            </Button>
          </View>
        </View>
      </Modal>
      <Pressable className="w-full self-stretch" onPress={() => setOpen(true)}>
        <ProductDetailsPanel></ProductDetailsPanel>
      </Pressable>
    </View>
  );
};
export default ProductDetails;
const styles = StyleSheet.create({
  container: {
    backgroundColor: "white",
    padding: 16,
  },
  dropdown: {
    borderColor: "#535353",
    borderWidth: 2.25,
    borderRadius: 12,
    paddingHorizontal: 16,
    paddingVertical: 12,
    width: "100%",
  },
  label: {
    zIndex: 999,
    fontSize: 12,
    letterSpacing: 0.6,
    fontFamily: "SFPRO_DISPLAY",
  },
  placeholderStyle: {
    fontSize: 16,
  },
  selectedTextStyle: {
    fontSize: 16,
  },
  iconStyle: {
    width: 20,
    height: 20,
  },
  inputSearchStyle: {
    height: 40,
    fontSize: 16,
  },
});
