import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { ProjectsRoutingModule } from './projects-routing.module';
import { CardComponent } from './components/card/card.component';
import { AddComponent } from './pages/add/add.component';


@NgModule({
  declarations: [
    CardComponent,
    AddComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ProjectsRoutingModule
  ]
})
export class ProjectsModule { }
