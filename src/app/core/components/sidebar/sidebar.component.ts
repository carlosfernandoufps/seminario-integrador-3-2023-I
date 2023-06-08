import { Component, OnInit, Input } from '@angular/core';

import { AuthService } from '@core/auth/auth.service';
import { ISidebar } from '@data/interfaces/ui/sidebar.interface';
import { ROLE } from '@data/enums/role.enum';
import { User } from '@data/models/User.model';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

  @Input() menuItems: ISidebar[];
  public currentUser: User;

  constructor(private authService: AuthService) {
    this.menuItems = [];
    this.currentUser = this.authService.getUser();
  }

  ngOnInit(): void { }

  public hasRole(roles: ROLE[]): boolean {
    if (!roles) return true;
    return this.authService.hasRole(roles);
  }

  public logOut(): void {
    this.authService.logOut();
  }

}
