import { Component, OnInit } from '@angular/core';

import { ProjectService } from '@modules/admin/projects/services/project.service';
import { IProject } from '@data/interfaces/project.interface';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss'],
})
export class ListComponent implements OnInit {

  listaProyectos: IProject[] = [];

  constructor(
    private projectService: ProjectService
  ) { }

  ngOnInit(): void {
    this.obtenerProyectos();
  }

  private obtenerProyectos() {
    this.projectService
      .obtenerProyectos()
      .subscribe((data: IProject[]) => {
        this.listaProyectos = data;
      });
  }
}
