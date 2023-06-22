import { Component, Input } from '@angular/core';

import { IProject } from '@data/interfaces/http/project.interface';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss']
})
export class CardComponent {

  @Input() data: any = {};

  constructor() { }

}
