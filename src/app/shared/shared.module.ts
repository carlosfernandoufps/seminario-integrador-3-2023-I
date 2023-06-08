import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RolesDirective } from './directives/roles.directive';



@NgModule({
  declarations: [
    RolesDirective
  ],
  imports: [
    CommonModule
  ]
})
export class SharedModule { }
