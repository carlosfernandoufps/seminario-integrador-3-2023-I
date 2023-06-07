import { Component, OnInit } from '@angular/core';

import { IProject } from '@data/interfaces/project.interface';
import { ProjectService } from '@modules/public/projects/services/project.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

  listaProyectos: IProject[] = [];
  CopylistaProyectos: IProject[] = [];

  constructor(
    private projectService: ProjectService
  ) { }

  ngOnInit(): void {
    this.obtenerProyectos();
  }

  private obtenerProyectos() {
    this.projectService.obtenerProyectos()
      .subscribe((data: IProject[]) => {
        this.listaProyectos = data;
        this.CopylistaProyectos = data;
        console.log(this.listaProyectos);
      });
  }

}
