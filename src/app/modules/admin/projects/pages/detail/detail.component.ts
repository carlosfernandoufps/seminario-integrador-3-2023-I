import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProject } from '@data/interfaces/http/project.interface';
import { ProjectService } from '@modules/admin/projects/services/project.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  public data: any = {};

  constructor(
    private route: ActivatedRoute,
    private projectService: ProjectService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.obtenerProyecto(Number(params['id']));
    });
  }

  private obtenerProyecto(id: number) {
    this.projectService.obtenerProyectoPorId(id)
      .subscribe((data: any) => {
        this.data = data;
      });
  }

  public agregarIntegrante() {
    const codigoIntegrante = (document.getElementById('codigoIntegrante') as HTMLInputElement).value;
    const idProyecto = this.data.proyecto.id;
    this.projectService
      .agregarIntegrante(codigoIntegrante, idProyecto)
      .subscribe((data: any) => {
        this.obtenerProyecto(idProyecto);
      });
    this.cerrarModal();
  }

  private cerrarModal() {
    const modal = document.getElementById('modalAgregarIntegrante') as HTMLElement;
    const modalBackdrop = document.getElementsByClassName('modal-backdrop')[0] as HTMLElement;
    modalBackdrop.classList.remove('show');
    modalBackdrop.style.display = 'none';
    modal.classList.remove('show');
    modal.style.display = 'none';
  }

  public isEmpty(): boolean {
    return !(Object.entries(this.data).length === 0);
  }

}
