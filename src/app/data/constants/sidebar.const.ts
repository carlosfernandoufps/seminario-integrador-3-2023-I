import { ISidebar } from "@data/interfaces/ui/sidebar.interface";
import { ROLE } from "@data/enums/role.enum";

export const MENU_ITEMS: ISidebar[] = [
  {
    title: "Principal",
    children: [
      {
        title: "Dashboard",
        icon: "fas fa-home",
        link: "/dashboard",
        expanded: false,
      },
    ],
  },
  {
    title: "Modulos",
    children: [
      {
        title: 'Academico',
        icon: 'fas fa-graduation-cap',
        roles: [ROLE.ADMIN, ROLE.TEACHER],
        expanded: true,
        children: [
          {
            title: 'Materias',
            link: '/academics/subjects',
            roles: [ROLE.ADMIN]
          },
          {
            title: 'Materia Periodo',
            link: '/academics/subject-period',
            roles: [ROLE.ADMIN]
          },
          {
            title: 'Cursos',
            link: '/academics/courses',
            roles: [ROLE.ADMIN, ROLE.TEACHER]
          }
        ],
      },
      {
        title: 'Proyectos',
        icon: 'fas fa-file-signature',
        link: '/projects',
        expanded: false,
        roles: [ROLE.STUDENT, ROLE.TEACHER, ROLE.ADMIN]
      },
      {
        title: 'Bases de Datos',
        icon: 'fas fa-database',
        link: '/databases',
        expanded: false,
        roles: [ROLE.STUDENT, ROLE.TEACHER, ROLE.ADMIN]
      },
      {
        title: 'Usuarios',
        icon: 'fas fa-users',
        link: '/users',
        expanded: false,
        roles: [ROLE.ADMIN]
      }
    ]
  }
];
