import { ScrollView, View } from "react-native";
import React from "react";
import { SafeAreaView } from "react-native-safe-area-context";
import * as ICONS from "@/lib/icons";
import { ProductType } from "@/constants/types";
import ProductCategories from "@/components/Homepage/ProductCategories";

const DrugTypeList: ProductType[] = [
  {
    title: "All Drug Products",
    icon: ICONS.Pill,
    type: "all",
  },
  {
    title: "Human",
    icon: ICONS.Stethoscope,
    type: "human",
  },
  {
    title: "Veterinary",
    icon: ICONS.Cat,
    type: "vet",
  },
];

const FoodTypeList: ProductType[] = [
  {
    title: "All Food Products",
    icon: ICONS.Beef,
    type: "all",
  },
  {
    title: "High-Risk",
    icon: ICONS.OctagonAlert,
    type: "h_risk",
  },
  {
    title: "Medium-Risk",
    icon: ICONS.TriangleAlert,
    type: "m_risk",
  },
  {
    title: "Low-Risk",
    icon: ICONS.FileWarning,
    type: "l_risk",
  },
  {
    title: "Raw Materials",
    icon: ICONS.ChefHat,
    type: "raw",
  },
];

const Index = () => {
  return (
    <SafeAreaView className="h-full bg-primary">
      <ScrollView>
        <View className="flex flex-1 flex-col items-stretch gap-4 px-6 py-3">
          <View className="flex flex-col items-start gap-6 self-stretch">
            <ProductCategories
              title={"View Food Products"}
              products={FoodTypeList}
              route={"food"}
            />
            <ProductCategories
              title={"View Drug Products"}
              products={DrugTypeList}
              route={"drug"}
            />
          </View>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};
export default Index;
