import { Component, OnInit } from '@angular/core';

import { AuthService } from '@core/auth/auth.service';
import { User } from '@data/models/User.model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  public currentUser: User = {} as User;

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.getCurrrentUser();
  }

  private getCurrrentUser(): void {
    this.currentUser = this.authService.getCurrentUserSubject();
  }

}
