import { Component, OnInit } from '@angular/core';

import { AuthService } from '@core/auth/auth.service';
import { ProjectService } from '@modules/admin/projects/services/project.service';
import { IProject } from '@data/interfaces/http/project.interface';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss'],
})
export class ListComponent implements OnInit {

  listaProyectos: any[] = [];

  constructor(
    private authService: AuthService,
    private projectService: ProjectService
  ) { }

  ngOnInit(): void {
    this.obtenerProyectos();
  }

  private obtenerProyectos() {
    const code = this.authService.codigoUsuario;
    this.projectService
      .obtenerProyectosPorUsuario(code)
      .subscribe((data: any[]) => {
        this.listaProyectos = data;
      });
  }

}
