import { Component, OnInit } from '@angular/core';

import { ProjectService } from 'src/app/services/project.service';
import { Project } from 'src/app/interface/project.interface';


@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

  title = 'ingemedia-app';
  listaProyectos: Project[] = [];

  constructor(
    private projectService: ProjectService
  ) { }

  ngOnInit(): void {
    this.obtenerProyectos();
  }

  private obtenerProyectos() {
    this.projectService.obtenerProyectos()
      .subscribe((data: Project[]) => {
        this.listaProyectos = data;
      });
  }

}
