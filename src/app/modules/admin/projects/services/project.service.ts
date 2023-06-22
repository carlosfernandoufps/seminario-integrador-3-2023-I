import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IProject } from '@data/interfaces/http/project.interface';
import { environment } from '@env/environment';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private readonly URL = `${environment.baseUrlProjects}`;

  constructor(
    private http: HttpClient
  ) { }

  public obtenerProyectosPorUsuario(code: string): Observable<IProject[]> {
    return this.http.get<IProject[]>(`${this.URL}/integrante/${code}`)
      .pipe(
        map((project: any[]) => {
          return project.map((p: any) => {
            p.fecha = new Date(p.proyecto.fecha).toLocaleDateString('en-CA');
            return p;
          });
        })
      );
  }

  public obtenerProyectoPorId(id: number): Observable<IProject> {
    return this.http.get<IProject>(`${this.URL}/${id}`);
  }

  public crearProyecto(proyecto: any): Observable<any> {
    return this.http.post<any>(this.URL, JSON.stringify(proyecto));
  }

  public agregarIntegrante(codigoIntegrante: string, idProyecto: string): Observable<any> {
    return this.http.put<any>(`${this.URL}/integrante/${codigoIntegrante}/proyecto/${idProyecto}`, {});
  }

}
