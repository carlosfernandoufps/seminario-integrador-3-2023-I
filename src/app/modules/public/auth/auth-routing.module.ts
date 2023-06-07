import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SignInComponent } from './pages/sign-in/sign-in.component';
import { SignUpComponent } from './pages/sign-up/sign-up.component';

const AUTH_ROUTES: Routes = [
  {
    path: '',
    children: [
      { path: 'sign-in', component: SignInComponent, title: 'Sign In' },
      { path: 'sign-up', component: SignUpComponent, title: 'Sign Up' },
      { path: '**', redirectTo: 'sign-in', pathMatch: 'full' },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(AUTH_ROUTES)],
  exports: [RouterModule],
})
export class AuthRoutingModule { }
