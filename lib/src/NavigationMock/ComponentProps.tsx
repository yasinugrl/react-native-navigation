import ParentNode from './Layouts/ParentNode';

export interface ComponentProps {
  layoutNode: ParentNode;
  bottomTabs?: ParentNode;
  stack?: ParentNode;
  backButton?: boolean;
}
