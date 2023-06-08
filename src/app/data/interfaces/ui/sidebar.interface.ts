import { ROLE } from '@data/enums/role.enum';

export interface ISidebar {
  title: string;
  icon?: string;
  link?: string;
  expanded?: boolean;
  children?: ISidebar[];
  roles?: ROLE[];
}
