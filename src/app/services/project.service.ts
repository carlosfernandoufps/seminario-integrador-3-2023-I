import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Project } from 'src/app/interface/project.interface';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private readonly URL = "http://137.184.150.100:8091/api/v1/proyectos";

  constructor(
    private http: HttpClient
  ) { }

  private get currentValue(): number {
    return Math.floor(Math.random() * 3) + 1;
  }

  public obtenerProyectos(): Observable<Project[]> {
    return this.http.get<Project[]>(this.URL)
    .pipe(
      map((project: Project[]) => {
        return project.map((p: Project) => {
          p.imagen = `assets/img/tarjeta${this.currentValue}.jpg`;
          return p;
        });
      })
    );
  }

}
