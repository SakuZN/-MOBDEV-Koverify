import "@/globals.css";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { Theme, ThemeProvider } from "@react-navigation/native";
import { SplashScreen, Stack } from "expo-router";
import { StatusBar } from "expo-status-bar";
import * as React from "react";
import { Platform } from "react-native";
import { NAV_THEME } from "@/lib/constants";
import { useColorScheme } from "@/lib/useColorScheme";
import { SafeAreaProvider } from "react-native-safe-area-context";
import { GestureHandlerRootView } from "react-native-gesture-handler";
import { PortalHost } from "@rn-primitives/portal";
import { useFonts } from "expo-font";
import { setAndroidNavigationBar } from "@/lib/android-navigation-bar";
import { ThemeToggle } from "@/components/ThemeToggle";
import { Text } from "@/components/ui/text";
import { LoadingProvider } from "@/components/Providers/LoaderSpinnerContext";
import LoadingSpinner from "@/components/LoadingSpinner";
import ReactQueryProvider from "@/components/Providers/ReactQueryProvider";
const LIGHT_THEME: Theme = {
  dark: false,
  colors: NAV_THEME.light,
};
const DARK_THEME: Theme = {
  dark: true,
  colors: NAV_THEME.dark,
};

export {
  // Catch any errors thrown by the Layout component.
  ErrorBoundary,
} from "expo-router";

// Prevent the splash screen from auto-hiding before getting the color scheme.
SplashScreen.preventAutoHideAsync();

export default function RootLayout() {
  const { colorScheme, setColorScheme, isDarkColorScheme } = useColorScheme();
  const [isColorSchemeLoaded, setIsColorSchemeLoaded] = React.useState(false);
  const [loaded, error] = useFonts({
    SFPRO: require("../assets/fonts/SF-Pro.ttf"),
    SFPRO_ITALIC: require("../assets/fonts/SF-Pro-Italic.ttf"),
    SFMONO_BOLD: require("../assets/fonts/SFMono-Bold.otf"),
    SFMONO_BOLDITALIC: require("../assets/fonts/SFMono-BoldItalic.otf"),
    SFMONO_HEAVY: require("../assets/fonts/SFMono-Heavy.otf"),
    SFMONO_HEAVYITALIC: require("../assets/fonts/SFMono-HeavyItalic.otf"),
    SFMONO_LIGHT: require("../assets/fonts/SFMono-Light.otf"),
    SFMONO_LIGHTITALIC: require("../assets/fonts/SFMono-LightItalic.otf"),
    SFMONO_MEDIUM: require("../assets/fonts/SFMono-Medium.otf"),
    SFMONO_MEDIUMITALIC: require("../assets/fonts/SFMono-MediumItalic.otf"),
    SFMONO_REGULAR: require("../assets/fonts/SFMono-Regular.otf"),
    SFMONO_REGULARITALIC: require("../assets/fonts/SFMono-RegularItalic.otf"),
    SFMONO_SEMIBOLD: require("../assets/fonts/SFMono-Semibold.otf"),
    SFMONO_SEMIBOLDITALIC: require("../assets/fonts/SFMono-SemiboldItalic.otf"),
  });

  React.useEffect(() => {
    (async () => {
      if ((loaded || error) && isColorSchemeLoaded) {
        await SplashScreen.hideAsync();
      }
      const theme = await AsyncStorage.getItem("theme");
      if (Platform.OS === "web") {
        document.documentElement.classList.add("bg-background");
      }
      if (!theme) {
        await setAndroidNavigationBar(colorScheme);
        await AsyncStorage.setItem("theme", colorScheme);
        setIsColorSchemeLoaded(true);
        return;
      }
      const colorTheme = theme === "dark" ? "dark" : "light";
      await setAndroidNavigationBar(colorTheme);
      if (colorTheme !== colorScheme) {
        setColorScheme(colorTheme);

        setIsColorSchemeLoaded(true);
        return;
      }
      setIsColorSchemeLoaded(true);
    })();
  }, [loaded, isColorSchemeLoaded]);

  if (!isColorSchemeLoaded || !(loaded || error)) {
    return null;
  }
  console.log(isDarkColorScheme);

  return (
    <ReactQueryProvider>
      <ThemeProvider value={isDarkColorScheme ? DARK_THEME : LIGHT_THEME}>
        <StatusBar style={isDarkColorScheme ? "light" : "dark"} />
        <SafeAreaProvider>
          <LoadingProvider>
            <GestureHandlerRootView style={{ flex: 1 }}>
              <Stack
                screenOptions={{
                  headerBackTitle: "Back",
                  headerTitleAlign: "center",
                  headerTitle(props) {
                    return (
                      <Text className="text-lg font-semibold">
                        {toOptions(props.children)}
                      </Text>
                    );
                  },
                  headerRight: () => <ThemeToggle />,
                }}
              >
                <Stack.Screen
                  name="index"
                  options={{
                    headerShown: false,
                  }}
                />
              </Stack>
              <LoadingSpinner />
              <PortalHost />
            </GestureHandlerRootView>
          </LoadingProvider>
        </SafeAreaProvider>
      </ThemeProvider>
    </ReactQueryProvider>
  );
}

function toOptions(name: string) {
  return name
    .split("-")
    .map(function (str: string) {
      return str.replace(/\b\w/g, function (char) {
        return char.toUpperCase();
      });
    })
    .join(" ");
}
