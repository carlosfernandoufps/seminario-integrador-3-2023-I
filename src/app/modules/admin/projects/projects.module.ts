import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { ProjectsRoutingModule } from './projects-routing.module';
import { SharedModule } from '@shared/shared.module';
import { AddComponent } from './pages/add/add.component';
import { CardComponent } from './components/card/card.component';
import { DetailComponent } from './pages/detail/detail.component';
import { ListComponent } from './pages/list/list.component';


@NgModule({
  declarations: [
    AddComponent,
    CardComponent,
    DetailComponent,
    ListComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ProjectsRoutingModule,
    SharedModule,
  ]
})
export class ProjectsModule { }
