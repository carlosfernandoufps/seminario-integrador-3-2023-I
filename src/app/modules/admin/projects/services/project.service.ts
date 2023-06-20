import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IProject } from '@data/interfaces/http/project.interface';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private readonly URL = "http://137.184.150.100:8091/api/v1/proyectos";

  constructor(
    private http: HttpClient
  ) { }

  public obtenerProyectos(): Observable<IProject[]> {
    return this.http.get<IProject[]>(this.URL).pipe(
      map((project: IProject[]) => {
        return project.map((p: IProject) => {
          p.fecha = new Date(p.fecha).toLocaleDateString('en-CA');
          return p;
        });
      })
    );
  }

  public obtenerProyectoPorId(id: number): Observable<IProject> {
    return this.http.get<IProject>(`${this.URL}/${id}`);
  }

  public crearProyecto(proyecto: IProject): Observable<IProject> {
    return this.http.post<IProject>(this.URL, proyecto);
  }

}
