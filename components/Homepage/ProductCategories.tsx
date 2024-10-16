import { View } from "react-native";
import React from "react";
import { ProductType } from "@/constants/types";
import { Button } from "@/components/ui/button";
import { Text } from "@/components/ui/text";
import { router } from "expo-router";
import { cn } from "@/lib/utils";
interface Props {
  title: string;
  products: ProductType[];
  route: string;
}
//make sure products are in pairs
const ProductPairList = (products: ProductType[]): ProductType[][] => {
  let pairList = [];
  for (let i = 0; i < products.length; i += 2) {
    pairList.push(products.slice(i, i + 2));
  }
  return pairList;
};

const ProductCategories = ({ title, products, route }: Props) => {
  const productPairs = ProductPairList(products);
  return (
    <View className="flex flex-col items-start gap-2.5 self-stretch">
      <Text
        fontFamily="SFPRO_DISPLAY"
        fontVariant="SemiBold"
        className="text-primary-foreground"
      >
        {title}
      </Text>
      {productPairs.map((pair, index) => (
        <View
          className="flex flex-row items-start gap-2.5 self-stretch"
          key={`${index}-${title}`}
        >
          {pair.map((product) => (
            <Button
              key={product.title}
              className={cn(
                "panel flex flex-1 flex-col items-end gap-3.5 p-5 pb-4",
                pair.length === 1 ? "max-w-[48.5%]" : "",
              )}
              onPress={() => {
                if (route === "quick-actions") {
                  //@ts-ignore
                  router.push(`/(homepage)/${product.type}`);
                } else {
                  router.push({
                    //@ts-ignore
                    pathname: `/${route}/[type]`,
                    params: { type: product.type },
                  });
                }
              }}
            >
              <product.icon size={40} color={"#248C69"} />
              <Text className="secondary-text">{product.title}</Text>
            </Button>
          ))}
        </View>
      ))}
    </View>
  );
};
export default ProductCategories;
