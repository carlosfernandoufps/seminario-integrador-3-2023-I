import { Component, OnInit } from '@angular/core';

import { AuthService } from '@core/auth/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styles: ['a { cursor: pointer; }']
})
export class NavbarComponent implements OnInit {

  public currentUser: any;

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void { }

  public logOut(): void { }

}
