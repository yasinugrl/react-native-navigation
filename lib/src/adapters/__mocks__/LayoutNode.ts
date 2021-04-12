
export default class LayoutNode {
    readonly type: 'Component' | 'ExternalComponent' | 'Stack' | 'BottomTabs' | 'TopTabs' | 'SideMenuRoot' | 'SideMenuLeft' | 'SideMenuRight' | 'SideMenuCenter' | 'SplitView';
    readonly nodeId: string;
    readonly data: object;
    readonly children: LayoutNode[];

    constructor(layout: any) {
        this.type = layout.type;
        this.nodeId = layout.id;
        this.data = layout.data;
        this.children = layout.children.map((childLayout: any) => new LayoutNode(childLayout));
    }

    toString() {
        let childrenString = '';
        if (this.children.length)
            childrenString = `: [${this.children.map((layoutNode: LayoutNode) => layoutNode.toString())}]`;

        return `${this.nodeId}${childrenString}`;
    }
}