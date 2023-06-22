import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '@core/auth/auth.service';
import { IUser } from '@data/interfaces/http/user.interface';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent {

  user: IUser = {} as IUser;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  public onSignUp() {
    const user = { correo: 'admin@mail.com', password: 'admin' };
    this.authService
      .signIn(user)
      .subscribe(() => {
        this.authService
          .signUp(this.user)
          .subscribe(() => {
            this.router.navigate(['/auth/sign-in']);
          });
      });
  }

}
