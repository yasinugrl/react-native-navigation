import { ComponentDidDisappearEvent } from "react-native-navigation/interfaces/ComponentEvents";
import { ComponentDidAppearEvent, NavigationButtonPressedEvent } from "../index";

export const events = {
  navigationButtonPressed: (_event: NavigationButtonPressedEvent) => { },
  componentDidAppear: (_event: ComponentDidAppearEvent) => { },
  componentDidDisappear: (_event: ComponentDidDisappearEvent) => { }
}
