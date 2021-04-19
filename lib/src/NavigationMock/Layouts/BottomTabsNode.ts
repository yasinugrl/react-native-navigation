import _ from "lodash";
import { Options } from "../../index";
import LayoutStore from "../LayoutStore";
import ParentNode from "./ParentNode";

export default class BottomTabsNode extends ParentNode {
    selectedIndex: number = 0;
    constructor(layout: any, parentNode?: ParentNode) {
        super(layout, 'BottomTabs', parentNode);
    }

    mergeOptions(options: Options) {
        super.mergeOptions(options);
        if (options.bottomTabs?.currentTabIndex)
            this.selectedIndex = options.bottomTabs?.currentTabIndex;
        if (options.bottomTabs?.currentTabId) {
            this.selectedIndex = 1
            const l = LayoutStore.getters.getLayoutById(options.bottomTabs.currentTabId);
            if (l) this.selectedIndex = this.children.indexOf(l);
        }
    }

    getVisibleLayout() {
        return this.children[this.selectedIndex].getVisibleLayout();
    }
}
