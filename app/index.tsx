import { View } from "react-native";
import { Text } from "@/components/ui/text";
import { useLoadingContext } from "@/components/Providers/LoaderSpinnerContext";
import { useEffect } from "react";
import { SafeAreaView } from "react-native-safe-area-context";
import { Button } from "@/components/ui/button";

export default function Index() {
  const { setLoading, setText } = useLoadingContext();
  const triggerLoading = () => {
    setLoading(true);
    setText("Loading...");
    setTimeout(() => {
      setLoading(false);
    }, 3000);
  }
  return (
    <SafeAreaView
      className="bg-primary h-full"
      style={{ flex: 1, justifyContent: "center", alignItems: "center", gap: 20 }}
    >
      <Text>Edit app/index.tsx to edit this screen.</Text>
      <Button onPress={triggerLoading}>
        <Text>Press for fake loading</Text>
      </Button>
    </SafeAreaView>
  );
}
