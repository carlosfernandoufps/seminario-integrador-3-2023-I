import { Component, Input } from '@angular/core';

import { Project } from 'src/app/interface/project.interface';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss']
})
export class CardComponent {

  @Input() project!: Project;

  constructor() { }

}
