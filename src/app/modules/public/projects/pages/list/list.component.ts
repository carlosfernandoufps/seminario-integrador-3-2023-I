import { Component, OnInit } from '@angular/core';

import { IProject } from '@data/interfaces/http/project.interface';
import { ProjectService } from '@modules/public/projects/services/project.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss'],
})
export class ListComponent implements OnInit {

  listaProyectos: IProject[] = [];
  CopylistaProyectos: IProject[] = [];
  Resultado: IProject[] = [];

  fecha: any = { min: null, max: null, currentStart: null, currentEnd: null };
  search: any = { subjects: true, semester: true, date: true };

  constructor(private projectService: ProjectService) { }

  ngOnInit(): void {
    this.obtenerProyectos();
  }

  private obtenerProyectos() {
    this.projectService.obtenerProyectos().subscribe((data: IProject[]) => {
      this.listaProyectos = data;
      this.CopylistaProyectos = data;
      this.getDateMinAndMax();
    });
  }

  private getDateMinAndMax() {
    const dates = this.listaProyectos.map((p: IProject) => new Date(p.fecha).getTime());
    const min = new Date(Math.min.apply(null, dates));
    const max = new Date(Math.max.apply(null, dates));
    max.setDate(max.getDate() + 1);
    this.fecha.min = min.toLocaleDateString('en-CA', { timeZone: 'America/Bogota' });
    this.fecha.max = max.toLocaleDateString('en-CA', { timeZone: 'America/Bogota' });
  }

  private filterBySubjects(materias: string[]): void {
    this.projectService
      .obtenerProyectosPorMaterias(materias)
      .subscribe((data: IProject[]) => {
        if (this.search.subjects) {
          this.Resultado.push(...data);
          this.search.subjects = false;
        }
      });
  }

  private filterByDate(): void {
    this.projectService
      .obtenerProyectosPorFecha(this.fecha.current)
      .subscribe((data: IProject[]) => {
        if (this.search.date) {
          this.Resultado.push(...data);
          this.search.date = false;
        }
      });
  }

  public onSearch() {
    this.filterByDate();
    this.filterBySubjects(['Programacion Web']);
    this.listaProyectos = this.Resultado;
  }

  public onClear() {
    this.listaProyectos = this.CopylistaProyectos;
  }

}
