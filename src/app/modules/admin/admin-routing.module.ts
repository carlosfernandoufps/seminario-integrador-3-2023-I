import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from '@core/guards/auth.guard';

const ADMIN_ROUTES: Routes = [
  {
    path: 'account',
    loadChildren: () => import('@modules/admin/account/account.module').then((m) => m.AccountModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'projects',
    loadChildren: () => import('@modules/admin/projects/projects.module').then((m) => m.ProjectsModule),
    canActivate: [AuthGuard],
  },
  { path: '', redirectTo: 'account', pathMatch: 'full' },
  { path: '**', redirectTo: 'account' },
];

@NgModule({
  imports: [RouterModule.forChild(ADMIN_ROUTES)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
