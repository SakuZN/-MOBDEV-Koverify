import { StyleSheet, View } from "react-native";
import React from "react";
import { Button } from "../ui/button";
import { Text } from "../ui/text";
import { router } from "expo-router";
import { LucideIcon } from "lucide-react-native";

interface Props {
  label: string;
  icon: LucideIcon;
  route: string;
}

const ProductCategories = ({ label, icon, route }: Props) => {
  return (
    <>
      <H2>{title}</H2>
      <View className="flex flex-row flex-wrap gap-4">
        {products.map((product) => (
          <Button
            key={product.title}
            variant={"outline"}
            className="native:h-32 h-32 rounded-md px-8"
            style={styles.buttons}
            onPress={() => {
              //@ts-ignore
              router.push({
                pathname: `/${route}/[type]`,
                params: { type: product.type },
              });
            }}
          >
            <View
              className="flex items-center justify-center bg-muted"
              style={styles.iconbg}
            >
              <product.icon className="text-muted-foreground" size={40} />
            </View>

            <Text className="text-center font-semibold">{product.title}</Text>
          </Button>
        ))}
      </View>
    </>
  );
};
export default ProductCategories;
const styles = StyleSheet.create({
  buttons: {
    width: "45%",
  },
  iconbg: {
    borderRadius: 16,
    width: 52,
    height: 52,
  },
});
