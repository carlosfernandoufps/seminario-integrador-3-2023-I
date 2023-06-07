import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IProject } from '@data/interfaces/project.interface';
import { environment } from '@env/environment';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private readonly URL = `${environment.baseUrlProjects}`;

  constructor(
    private http: HttpClient
  ) { }

  private get currentValue(): number {
    return Math.floor(Math.random() * 3) + 1;
  }

  public obtenerProyectos(): Observable<IProject[]> {
    return this.http.get<IProject[]>(this.URL)
      .pipe(
        map((project: IProject[]) => {
          return project.map((p: IProject) => {
            p.fecha = new Date(p.fecha).toLocaleDateString('en-CA');
            p.imagen = `assets/img/tarjeta${this.currentValue}.jpg`;
            return p;
          });
        })
      );
  }

  public obtenerProyectoPorId(id: number): Observable<IProject> {
    return this.http.get<IProject>(`${this.URL}/${id}`)
      .pipe(
        map((project: IProject) => {
          project.imagen = `assets/img/tarjeta${this.currentValue}.jpg`;
          return project;
        })
      );
  }

}