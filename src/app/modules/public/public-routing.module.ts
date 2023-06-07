import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { NoAuthGuard } from '@core/guards/no-auth.guard';

const PUBLIC_ROUTES: Routes = [
  {
    path: 'home',
    loadChildren: () => import('@modules/public/home/home.module').then((m) => m.HomeModule),
    canActivate: [NoAuthGuard],
  },
  {
    path: 'auth',
    loadChildren: () => import('@modules/public/auth/auth.module').then((m) => m.AuthModule),
    canActivate: [NoAuthGuard],
  },
  {
    path: 'projects',
    loadChildren: () => import('@modules/public/projects/projects.module').then((m) => m.ProjectsModule),
    canActivate: [NoAuthGuard],
  },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', redirectTo: 'home' },
];

@NgModule({
  imports: [RouterModule.forChild(PUBLIC_ROUTES)],
  exports: [RouterModule]
})
export class PublicRoutingModule { }
