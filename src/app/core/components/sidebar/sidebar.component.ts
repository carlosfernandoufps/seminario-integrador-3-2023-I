import { Component, OnInit, Input } from '@angular/core';

import { AuthService } from '@core/services/auth/auth.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

  @Input() menuItems!: any[];
  public currentUser: any;

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void { }

  public hasRole(roles: any[]): boolean {
    if (!roles) return true;
    return true;
    //return this.authService.hasRole(roles);
  }

  public logOut(): void {
    //this.authService.logout();
  }

}
