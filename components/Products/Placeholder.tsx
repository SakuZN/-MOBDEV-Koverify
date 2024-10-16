import { View } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";
import { Text } from "@/components/ui/text";
import { ArrowLeft, Factory, Filter, Frame, Search } from "lucide-react-native";
import { Input } from "@/components/ui/input";
import FilterButton from "./FilterButton";

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
          <View className="panel flex flex-col items-start gap-0.5 self-stretch p-3">
            <View className="flex flex-row items-center gap-1.5 self-stretch">
              <Text
                fontFamily="Inter"
                fontVariant="Bold"
                style={{
                  fontSize: 13,
                }}
              >
                Actrapid
              </Text>
              <Text
                fontFamily="Inter"
                fontVariant="Medium"
                style={{
                  fontSize: 10,
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
                      fontSize: 10,
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
                    fontSize: 12,
                  }}
                >
                  Novo Nordisk
                </Text>
              </View>
              <View className="flex flex-col items-start gap-0.5">
                <View className="flex flex-row items-center gap-1">
                  <Frame size={12} color={"#212121"} />
                  <Text
                    fontFamily="SFMONO"
                    fontVariant="Medium"
                    style={{
                      fontSize: 10,
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
                    fontSize: 12,
                  }}
                >
                  OTC Drug
                </Text>
              </View>
            </View>
          </View>
        </View>
      </View>
    </SafeAreaView>
  );
}
