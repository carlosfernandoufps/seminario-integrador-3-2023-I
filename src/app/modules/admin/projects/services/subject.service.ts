import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ISubject } from '@data/interfaces/http/subject.interface';
import { environment } from '@env/environment';

@Injectable({
  providedIn: 'root'
})
export class SubjectService {

  private readonly URL = `${environment.baseUrlSubjects}`;

  constructor(private http: HttpClient) { }

  public obtenerMaterias(): Observable<ISubject[]> {
    return this.http.get<ISubject[]>(`${this.URL}`);
  }

}
