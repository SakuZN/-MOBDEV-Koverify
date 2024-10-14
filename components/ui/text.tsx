import * as Slot from "@rn-primitives/slot";
import { SlottableTextProps, TextRef } from "@rn-primitives/types";
import * as React from "react";
import { Text as RNText } from "react-native";
import { cn } from "@/lib/utils";
import { cva, type VariantProps } from "class-variance-authority";

const textVariants = cva("web:select-text", {
  variants: {
    variant: {
      default: "text-base text-primary-foreground",
      h1: "web:scroll-m-20 text-4xl text-foreground font-extrabold tracking-tight lg:text-5xl",
      h2: "web:scroll-m-20 border-b border-border pb-2 text-3xl text-foreground font-semibold tracking-tight first:mt-0 web:select-text",
      h3: "web:scroll-m-20 text-2xl text-foreground font-semibold tracking-tight web:select-text",
      h4: "web:scroll-m-20 text-xl text-foreground font-semibold tracking-tight web:select-text",
      blockquote:
        "mt-6 native:mt-4 border-l-2 border-border pl-6 native:pl-3 text-base text-foreground italic web:select-text",
      code: "relative rounded-md bg-muted px-[0.3rem] py-[0.2rem] text-sm text-foreground font-semibold web:select-text",
      lead: "text-xl text-muted-foreground web:select-text",
      large: "text-xl text-foreground font-semibold web:select-text",
      small: "text-sm text-foreground font-medium leading-none web:select-text",
      muted: "text-sm text-muted-foreground web:select-text",
    },
  },
  defaultVariants: {
    variant: "default",
  },
});

const TextClassContext = React.createContext<string | undefined>(undefined);

type FontFamily =
  | "SFPRO"
  | "SFPRO_DISPLAY"
  | "SFPRO_ROUNDED"
  | "SFPRO_TEXT"
  | "SFMONO"
  | "Inter";

type FontVariant =
  | "default"
  | "Italic"
  | "Black"
  | "BlackItalic"
  | "Bold"
  | "BoldItalic"
  | "Heavy"
  | "HeavyItalic"
  | "Light"
  | "LightItalic"
  | "Medium"
  | "MediumItalic"
  | "Regular"
  | "RegularItalic"
  | "SemiBold"
  | "SemiBoldItalic"
  | "Thin"
  | "ThinItalic"
  | "Ultralight"
  | "UltralightItalic"
  | "ExtraBold"
  | "ExtraLight";

type TextProps = VariantProps<typeof textVariants> &
  SlottableTextProps & {
    fontFamily?: FontFamily;
    fontVariant?: FontVariant;
  };

function capitalizeAllLetters(str: string | null | undefined) {
  if (!str) return "";
  return str.toUpperCase();
}

function parseInterVariant(variant: FontVariant | undefined) {
  switch (variant) {
    case "Thin":
      return "100Thin";
    case "ExtraLight":
      return "200ExtraLight";
    case "Light":
      return "300Light";
    case "Regular":
      return "400Regular";
    case "Medium":
      return "500Medium";
    case "SemiBold":
      return "600SemiBold";
    case "Bold":
      return "700Bold";
    case "ExtraBold":
      return "800ExtraBold";
    case "Black":
      return "900Black";
    default:
      return "400Regular";
  }
}

// Mapping function for font families and variants
const getFontFamilyStyle = (
  fontFamily?: FontFamily,
  fontVariant?: FontVariant,
) => {
  switch (fontFamily) {
    case "SFPRO":
      return fontVariant === "Italic" ? "SFPRO_ITALIC" : "SFPRO";
    case "SFPRO_DISPLAY":
      return (
        `SFPRO_DISPLAY_${capitalizeAllLetters(fontVariant)}` ||
        "SFPRO_DISPLAY_REGULAR"
      );
    case "SFPRO_ROUNDED":
      return (
        `SFPRO_ROUNDED_${capitalizeAllLetters(fontVariant)}` ||
        "SFPRO_ROUNDED_REGULAR"
      );
    case "SFPRO_TEXT":
      return (
        `SFPRO_TEXT_${capitalizeAllLetters(fontVariant)}` ||
        "SFPRO_TEXT_REGULAR"
      );
    case "Inter":
      return `Inter_${parseInterVariant(fontVariant)}`;
    case "SFMONO":
      switch (fontVariant) {
        case "Bold":
          return "SFMONO_BOLD";
        case "BoldItalic":
          return "SFMONO_BOLDITALIC";
        case "Heavy":
          return "SFMONO_HEAVY";
        case "HeavyItalic":
          return "SFMONO_HEAVYITALIC";
        case "Light":
          return "SFMONO_LIGHT";
        case "LightItalic":
          return "SFMONO_LIGHTITALIC";
        case "Medium":
          return "SFMONO_MEDIUM";
        case "MediumItalic":
          return "SFMONO_MEDIUMITALIC";
        case "Regular":
          return "SFMONO_REGULAR";
        case "RegularItalic":
          return "SFMONO_REGULARITALIC";
        case "SemiBold":
          return "SFMONO_SEMIBOLD";
        case "SemiBoldItalic":
          return "SFMONO_SEMIBOLDITALIC";
        default:
          return "SFMONO_REGULAR";
      }
    default:
      return undefined;
  }
};

const Text = React.forwardRef<TextRef, TextProps>(
  (
    {
      className,
      asChild = false,
      variant,
      style,
      fontFamily = "SFPRO",
      fontVariant,
      ...props
    },
    ref,
  ) => {
    const textClass = React.useContext(TextClassContext);
    const Component = asChild ? Slot.Text : RNText;
    const selectedFontFamily = getFontFamilyStyle(fontFamily, fontVariant);

    return (
      <Component
        className={cn(textVariants({ variant }), textClass, className)}
        ref={ref}
        style={[
          style,
          selectedFontFamily ? { fontFamily: selectedFontFamily } : {},
        ]}
        {...props}
      />
    );
  },
);
Text.displayName = "Text";

export { Text, TextClassContext };
