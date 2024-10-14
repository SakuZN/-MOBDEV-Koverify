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
import {
  useFonts as useGoogleFonts,
  Inter_100Thin,
  Inter_200ExtraLight,
  Inter_300Light,
  Inter_400Regular,
  Inter_500Medium,
  Inter_600SemiBold,
  Inter_700Bold,
  Inter_800ExtraBold,
  Inter_900Black,
} from "@expo-google-fonts/inter";
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
  let [googleFontsLoaded] = useGoogleFonts({
    Inter_100Thin,
    Inter_200ExtraLight,
    Inter_300Light,
    Inter_400Regular,
    Inter_500Medium,
    Inter_600SemiBold,
    Inter_700Bold,
    Inter_800ExtraBold,
    Inter_900Black,
  });
  const [loaded, error] = useFonts({
    SFPRO: require("../assets/fonts/SF-Pro.ttf"),
    SFPRO_DISPLAY_BLACK: require("../assets/fonts/SF-Pro-Display-Black.otf"),
    SFPRO_DISPLAY_BLACKITALIC: require("../assets/fonts/SF-Pro-Display-BlackItalic.otf"),
    SFPRO_DISPLAY_BOLD: require("../assets/fonts/SF-Pro-Display-Bold.otf"),
    SFPRO_DISPLAY_BOLDITALIC: require("../assets/fonts/SF-Pro-Display-BoldItalic.otf"),
    SFPRO_DISPLAY_HEAVY: require("../assets/fonts/SF-Pro-Display-Heavy.otf"),
    SFPRO_DISPLAY_HEAVYITALIC: require("../assets/fonts/SF-Pro-Display-HeavyItalic.otf"),
    SFPRO_DISPLAY_LIGHT: require("../assets/fonts/SF-Pro-Display-Light.otf"),
    SFPRO_DISPLAY_LIGHTITALIC: require("../assets/fonts/SF-Pro-Display-LightItalic.otf"),
    SFPRO_DISPLAY_MEDIUM: require("../assets/fonts/SF-Pro-Display-Medium.otf"),
    SFPRO_DISPLAY_MEDIUMITALIC: require("../assets/fonts/SF-Pro-Display-MediumItalic.otf"),
    SFPRO_DISPLAY_REGULAR: require("../assets/fonts/SF-Pro-Display-Regular.otf"),
    SFPRO_DISPLAY_REGULARITALIC: require("../assets/fonts/SF-Pro-Display-RegularItalic.otf"),
    SFPRO_DISPLAY_SEMIBOLD: require("../assets/fonts/SF-Pro-Display-Semibold.otf"),
    SFPRO_DISPLAY_SEMIBOLDITALIC: require("../assets/fonts/SF-Pro-Display-SemiboldItalic.otf"),
    SFPRO_DISPLAY_THIN: require("../assets/fonts/SF-Pro-Display-Thin.otf"),
    SFPRO_DISPLAY_THINITALIC: require("../assets/fonts/SF-Pro-Display-ThinItalic.otf"),
    SFPRO_DISPLAY_ULTRALIGHT: require("../assets/fonts/SF-Pro-Display-Ultralight.otf"),
    SFPRO_DISPLAY_ULTRALIGHTITALIC: require("../assets/fonts/SF-Pro-Display-UltralightItalic.otf"),
    SFPRO_ROUNDED_BLACK: require("../assets/fonts/SF-Pro-Rounded-Black.otf"),
    SFPRO_ROUNDED_BOLD: require("../assets/fonts/SF-Pro-Rounded-Bold.otf"),
    SFPRO_ROUNDED_HEAVY: require("../assets/fonts/SF-Pro-Rounded-Heavy.otf"),
    SFPRO_ROUNDED_LIGHT: require("../assets/fonts/SF-Pro-Rounded-Light.otf"),
    SFPRO_ROUNDED_MEDIUM: require("../assets/fonts/SF-Pro-Rounded-Medium.otf"),
    SFPRO_ROUNDED_REGULAR: require("../assets/fonts/SF-Pro-Rounded-Regular.otf"),
    SFPRO_ROUNDED_SEMIBOLD: require("../assets/fonts/SF-Pro-Rounded-Semibold.otf"),
    SFPRO_ROUNDED_THIN: require("../assets/fonts/SF-Pro-Rounded-Thin.otf"),
    SFPRO_ROUNDED_ULTRALIGHT: require("../assets/fonts/SF-Pro-Rounded-Ultralight.otf"),
    SFPRO_TEXT_BLACK: require("../assets/fonts/SF-Pro-Text-Black.otf"),
    SFPRO_TEXT_BLACKITALIC: require("../assets/fonts/SF-Pro-Text-BlackItalic.otf"),
    SFPRO_TEXT_BOLD: require("../assets/fonts/SF-Pro-Text-Bold.otf"),
    SFPRO_TEXT_BOLDITALIC: require("../assets/fonts/SF-Pro-Text-BoldItalic.otf"),
    SFPRO_TEXT_HEAVY: require("../assets/fonts/SF-Pro-Text-Heavy.otf"),
    SFPRO_TEXT_HEAVYITALIC: require("../assets/fonts/SF-Pro-Text-HeavyItalic.otf"),
    SFPRO_TEXT_LIGHT: require("../assets/fonts/SF-Pro-Text-Light.otf"),
    SFPRO_TEXT_LIGHTITALIC: require("../assets/fonts/SF-Pro-Text-LightItalic.otf"),
    SFPRO_TEXT_MEDIUM: require("../assets/fonts/SF-Pro-Text-Medium.otf"),
    SFPRO_TEXT_MEDIUMITALIC: require("../assets/fonts/SF-Pro-Text-MediumItalic.otf"),
    SFPRO_TEXT_REGULAR: require("../assets/fonts/SF-Pro-Text-Regular.otf"),
    SFPRO_TEXT_REGULARITALIC: require("../assets/fonts/SF-Pro-Text-RegularItalic.otf"),
    SFPRO_TEXT_SEMIBOLD: require("../assets/fonts/SF-Pro-Text-Semibold.otf"),
    SFPRO_TEXT_SEMIBOLDITALIC: require("../assets/fonts/SF-Pro-Text-SemiboldItalic.otf"),
    SFPRO_TEXT_THIN: require("../assets/fonts/SF-Pro-Text-Thin.otf"),
    SFPRO_TEXT_THINITALIC: require("../assets/fonts/SF-Pro-Text-ThinItalic.otf"),
    SFPRO_TEXT_ULTRALIGHT: require("../assets/fonts/SF-Pro-Text-Ultralight.otf"),
    SFPRO_TEXT_ULTRALIGHTITALIC: require("../assets/fonts/SF-Pro-Text-UltralightItalic.otf"),
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
      if (loaded && isColorSchemeLoaded && googleFontsLoaded) {
        console.log("Hiding splash screen");
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

  if (!isColorSchemeLoaded || !loaded || !googleFontsLoaded) {
    return null;
  }
  if (error) {
    console.error(error);
    throw error;
  }
  console.log(isDarkColorScheme);

  return (
    <ReactQueryProvider>
      <ThemeProvider value={LIGHT_THEME}>
        <StatusBar style={"light"} />
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
                <Stack.Screen
                  name="(homepage)"
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
