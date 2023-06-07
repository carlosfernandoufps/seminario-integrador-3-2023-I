export interface IProject {
  id: number;
  titulo: string;
  fecha: string;
  materia: string;
  semestre: string;
  descripcion: string;
  link: string;
  imagen: string;
  integrantes: Integrante[];
}

export interface Integrante {
  codigo: string;
  nombre: string;
  apellido: string;
  correo: string;
  rol: string;
  proyectos: string[];
}
