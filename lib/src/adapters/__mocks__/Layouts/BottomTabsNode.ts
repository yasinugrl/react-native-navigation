import ParentNode from "./ParentNode";

export default class BottomTabsNode extends ParentNode {
    selectedIndex: number = 0;
    constructor(layout: any, parentNode?: ParentNode) {
        super(layout, 'BottomTabs', parentNode);
    }

    getVisibleLayout() {
        return this.children[this.selectedIndex].getVisibleLayout();
    }
}
