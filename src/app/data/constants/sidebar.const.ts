import { ISidebar } from "@data/interfaces/ui/sidebar.interface";
import { ROLE } from "@data/enums/role.enum";

export const MENU_ITEMS: ISidebar[] = [
  {
    title: "Principal",
    children: [
      {
        title: "Perfil",
        icon: "fas fa-user",
        link: "/profile",
        expanded: false,
      },
    ],
  },
  {
    title: "Modulos",
    children: [
      {
        title: 'Proyectos',
        icon: 'fas fa-file-signature',
        link: '/projects',
        expanded: true,
        children: [
          {
            title: 'Mis proyectos',
            icon: 'fas fa-file-signature',
            link: '/projects/my-projects',
            expanded: false,
            roles: [ROLE.STUDENT, ROLE.TEACHER, ROLE.ADMIN]
          },
          {
            title: 'Crear proyecto',
            icon: 'fas fa-file-signature',
            link: '/projects/create-project',
            expanded: false,
            roles: [ROLE.STUDENT, ROLE.TEACHER, ROLE.ADMIN]
          }
        ]
      }
    ]
  }
];
