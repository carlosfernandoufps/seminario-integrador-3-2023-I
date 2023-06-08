import { Injectable } from '@angular/core';

import { ISidebar } from "@data/interfaces/ui/sidebar.interface";
import { MENU_ITEMS } from "@data/constants/sidebar.const";

@Injectable({
  providedIn: 'root'
})
export class SidebarService {

  constructor() { }

  public get menu(): ISidebar[] {
    return MENU_ITEMS;
  }

}
