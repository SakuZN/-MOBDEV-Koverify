import { View } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";
import { Text } from "@/components/ui/text";
import { ArrowLeft, Factory, Filter, Frame, Search } from "lucide-react-native";
import { Input } from "@/components/ui/input";
import FilterButton from "./FilterButton";
import ProductDetails from "./ProductDetails";

export default function Placeholder() {
  return (
    <SafeAreaView style={{ flex: 1 }}>
      <View className="flex flex-col gap-4 bg-primary p-3 px-5">
        <View className="flex flex-row items-start gap-2 self-stretch">
          <View className="searchbar flex flex-1 flex-row items-center gap-2 px-4 py-3">
            <Search strokeWidth={3} size={16} color={"#258C6A"} />
            <Input className="" placeholder="Search" />
          </View>
          <FilterButton />
        </View>
        <View className="flex flex-col items-start gap-3 self-stretch">
          <ProductDetails></ProductDetails>
          <ProductDetails></ProductDetails>
        </View>
      </View>
    </SafeAreaView>
  );
}
