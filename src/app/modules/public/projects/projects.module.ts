import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ProjectsRoutingModule } from './projects-routing.module';
import { SharedModule } from '@shared/shared.module';
import { ListComponent } from './pages/list/list.component';
import { DetailComponent } from './pages/detail/detail.component';
import { CardComponent } from './components/card/card.component';


@NgModule({
  declarations: [
    ListComponent,
    DetailComponent,
    CardComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ProjectsRoutingModule,
    SharedModule
  ],
  exports: [
    ListComponent
  ]
})
export class ProjectsModule { }
