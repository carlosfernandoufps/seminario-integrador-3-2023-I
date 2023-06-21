import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProject } from '@data/interfaces/http/project.interface';
import { ProjectService } from '@modules/public/projects/services/project.service';

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

}
