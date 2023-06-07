import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AdminLayoutComponent } from '@layouts/admin-layout/admin-layout.component';
import { AddComponent } from './pages/add/add.component';
import { DetailComponent } from './pages/detail/detail.component';
import { ListComponent } from './pages/list/list.component';

import { RoleGuard } from '@core/guards/role.guard';
import { ROLE } from "@data/enums/role.enum";

const PROJECTS_ROUTES: Routes = [
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      {
        path: '',
        component: ListComponent,
        pathMatch: 'full',
        title: 'List of Projects',
        canActivate: [RoleGuard],
        data: { roles: [ROLE.ADMIN, ROLE.SUPPORT, ROLE.TEACHER, ROLE.STUDENT] }
      },
      {
        path: 'new',
        component: AddComponent,
        title: 'New Project',
        canActivate: [RoleGuard],
        data: { roles: [ROLE.ADMIN, ROLE.SUPPORT, ROLE.TEACHER, ROLE.STUDENT] }
      },
      {
        path: ':id',
        component: DetailComponent,
        title: 'Detail of Project',
        canActivate: [RoleGuard],
        data: { roles: [ROLE.ADMIN, ROLE.SUPPORT, ROLE.TEACHER, ROLE.STUDENT] }
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(PROJECTS_ROUTES)],
  exports: [RouterModule]
})
export class ProjectsRoutingModule { }
