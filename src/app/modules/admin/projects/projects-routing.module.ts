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
        path: 'my-projects',
        component: ListComponent,
        pathMatch: 'full',
        title: 'List of Projects',
        canActivate: [RoleGuard],
        data: { roles: [ROLE.ADMIN, ROLE.TEACHER, ROLE.STUDENT] }
      },
      {
        path: 'create-project',
        component: AddComponent,
        title: 'New Project',
        canActivate: [RoleGuard],
        data: { roles: [ROLE.ADMIN, ROLE.TEACHER, ROLE.STUDENT] }
      },
      {
        path: 'detail-project/:id',
        component: DetailComponent,
        title: 'Detail of Project',
        //canActivate: [RoleGuard],
        data: { roles: [ROLE.ADMIN, ROLE.TEACHER, ROLE.STUDENT] }
      },
      { path: '', redirectTo: 'my-projects', pathMatch: 'full' },
      { path: '**', redirectTo: 'my-projects' },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(PROJECTS_ROUTES)],
  exports: [RouterModule]
})
export class ProjectsRoutingModule { }
