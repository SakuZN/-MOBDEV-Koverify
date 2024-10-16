import { Modal, Pressable, StyleSheet, View } from "react-native";
import React, { useState } from "react";
import { Text } from "@/components/ui/text";
import { Filter, X } from "lucide-react-native";
import { Dropdown } from "react-native-element-dropdown";
import { Calendar } from "../ui/calendar";
import { Theme } from "@react-navigation/native";
import { Colors } from "react-native/Libraries/NewAppScreen";
import { Button } from "../ui/button";

const FilterButton = () => {
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
    <View className="flex content-center items-center">
      <Modal
        animationType="fade"
        transparent={false}
        visible={open}
        onRequestClose={() => {
          setOpen(false);
        }}
      >
        <View className="flex content-center items-center">
          <View className="panel m-24 flex w-11/12 flex-col items-center gap-4 rounded-md bg-background p-6">
            <Pressable
              className="absolute right-5 top-5"
              onPress={() => setOpen(false)}
            >
              <X size={18} />
            </Pressable>
            <View className="flex flex-col items-center gap-1.5 self-stretch">
              <Text
                fontFamily="SFPRO_DISPLAY"
                fontVariant="Medium"
                className="modal-header"
              >
                Filters
              </Text>
              <View className="flex flex-col items-start gap-1.5 self-stretch">
                <Text
                  fontFamily="SFPRO_DISPLAY"
                  fontVariant="SemiBold"
                  className="text-primary-foreground"
                >
                  Country of Origin
                </Text>
                <Dropdown
                  style={[styles.dropdown, isFocus && { borderColor: "blue" }]}
                  data={data}
                  search={false}
                  labelField={"label"}
                  valueField={"value"}
                  placeholder={!isFocus ? "Select a category" : "..."}
                  searchPlaceholder="Search..."
                  value={value}
                  onFocus={() => setIsFocus(true)}
                  onBlur={() => setIsFocus(false)}
                  onChange={(item) => {
                    setValue(item.value);
                    setIsFocus(false);
                  }}
                />
              </View>
              <View className="flex flex-col items-start gap-1.5 self-stretch">
                <Text
                  fontFamily="SFPRO_DISPLAY"
                  fontVariant="SemiBold"
                  className="text-primary-foreground"
                >
                  Classification
                </Text>
                <Dropdown
                  style={[styles.dropdown, isFocus && { borderColor: "blue" }]}
                  data={data}
                  search={false}
                  labelField={"label"}
                  valueField={"value"}
                  placeholder={!isFocus ? "Select a category" : "..."}
                  searchPlaceholder="Search..."
                  value={value}
                  onFocus={() => setIsFocus(true)}
                  onBlur={() => setIsFocus(false)}
                  onChange={(item) => {
                    setValue(item.value);
                    setIsFocus(false);
                  }}
                />
              </View>
              <View className="flex flex-row gap-2 self-stretch">
                <View className="flex flex-col items-start gap-1.5 self-stretch">
                  <Text
                    fontFamily="SFPRO_DISPLAY"
                    fontVariant="SemiBold"
                    className="text-primary-foreground"
                  >
                    Issuance Date
                  </Text>
                  <Calendar className="border-standard w-full px-4 py-2.5" />
                </View>
                <View className="flex flex-col items-start gap-1.5 self-stretch">
                  <Text
                    fontFamily="SFPRO_DISPLAY"
                    fontVariant="SemiBold"
                    className="text-primary-foreground"
                  >
                    Expiry Date
                  </Text>
                  <Calendar className="border-standard w-full px-4 py-2.5" />
                </View>
              </View>
            </View>
            <View className="flex flex-row content-center items-start gap-2 self-stretch">
              <Button className="flex flex-1 content-center items-center gap-1.5 rounded-3xl bg-[#A7A9AB] py-4">
                <Text
                  fontFamily="SFPRO_DISPLAY"
                  fontVariant="Regular"
                  className="text-base/4 color-white"
                >
                  CANCEL
                </Text>
              </Button>
              <Button className="flex flex-1 content-center items-center gap-1.5 rounded-3xl bg-[#258C6A] py-4">
                <Text
                  fontFamily="SFPRO_DISPLAY"
                  fontVariant="Regular"
                  className="text-base/4 color-white"
                >
                  SEARCH
                </Text>
              </Button>
            </View>
          </View>
        </View>
      </Modal>
      <Pressable
        className="panel flex items-center justify-center gap-2 self-stretch p-3"
        onPress={() => setOpen(true)}
      >
        <Filter strokeWidth={2} size={20} color={"#258C6A"} />
      </Pressable>
    </View>
  );
};
export default FilterButton;
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
