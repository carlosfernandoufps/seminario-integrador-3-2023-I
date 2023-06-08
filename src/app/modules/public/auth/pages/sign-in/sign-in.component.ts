import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '@core/auth/auth.service';
import { IUser } from '@data/interfaces/http/user.interface';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent {

  user: IUser = {} as IUser;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  public onSignIn() {
    this.authService.signIn(this.user)
      .subscribe(() => {
        this.router.navigate(['admin/account/profile']);
      });
  }

}
