import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RolesDirective } from './directives/roles.directive';
import { TruncateTextPipe } from './pipes/truncate-text.pipe';



@NgModule({
  declarations: [
    RolesDirective,
    TruncateTextPipe
  ],
  imports: [
    CommonModule
  ],
  exports: [
    RolesDirective,
    TruncateTextPipe
  ]
})
export class SharedModule { }
