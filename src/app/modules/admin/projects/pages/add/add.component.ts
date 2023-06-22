import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '@core/auth/auth.service';
import { ProjectService } from '@modules/admin/projects/services/project.service';
import { SubjectService } from '@modules/admin/projects/services/subject.service';
import { ISubject } from '@data/interfaces/http/subject.interface';
import { IValidationMessages } from '@data/interfaces/ui/validation-messages.interface';
import { VALIDACION_MENSAJES_PROYECTO } from '@data/constants/validation-messages.const';
import { User } from '@data/models/User.model';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit {

  private currentUser: User = this.authService.getCurrentUserSubject();
  private urlDefaultImage: string = 'https://cdn-icons-png.flaticon.com/512/4173/4173337.png';
  public imageURL: string;
  public newProject: FormGroup;
  public validationMessages: IValidationMessages = VALIDACION_MENSAJES_PROYECTO;
  public subjects: ISubject[] = [];

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private projectService: ProjectService,
    private subjectService: SubjectService,
    private router: Router
  ) {
    this.imageURL = this.urlDefaultImage;
    this.newProject = this.fb.group({
      titulo: new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(30)]),
      materia: new FormControl(null, [Validators.required]),
      descripcion: new FormControl('', [Validators.required, Validators.minLength(10), Validators.maxLength(100)]),
      link: new FormControl('', [Validators.required]),
      imagen: new FormControl('', [Validators.required])
    });
  }

  ngOnInit(): void {
    this.getSubjects();
  }

  public get f() { return this.newProject.controls; }

  public isFieldValid(field: string): boolean {
    return this.newProject.controls[field].dirty || this.newProject.controls[field].touched;
  }

  public showPreview(event: Event): void {
    const url = ((event.target as HTMLInputElement).value).trim();
    if (url) {
      this.imageURL = url;
    } else {
      this.imageURL = this.urlDefaultImage;
    }
  }

  public getSubjects(): void {
    this.subjectService
      .obtenerMaterias()
      .subscribe((subjects: ISubject[]) => {
        this.subjects = subjects;
      });
  }

  private getObtenerPeriodoSemestre(): string {
    const date = new Date();
    const month = date.getMonth() + 1;
    const year = date.getFullYear();
    const semester = month <= 6 ? '1' : '2';
    return `${year}-${semester}`;
  }

  public onBack(): void {
    this.router.navigate(['/admin/projects']);
  }

  public onSubmit(): void {
    if (!this.newProject.valid) {
      this.newProject.markAllAsTouched();
      return;
    }

    const project = {
      titulo: this.f['titulo'].value.trim(),
      fecha: new Date().toLocaleDateString('en-CA'),
      idMateria: this.f['materia'].value,
      semestre: this.getObtenerPeriodoSemestre(),
      descripcion: this.f['descripcion'].value.trim(),
      link: this.f['link'].value.trim(),
      imagen: this.f['imagen'].value.trim(),
      codigosIntegrantes: [this.currentUser.codigo]
    };

    this.projectService
      .crearProyecto(project)
      .subscribe((response: any) => {
        console.log(response);
        this.router.navigate(['/admin/projects/my-projects']);
      });
  }

}
