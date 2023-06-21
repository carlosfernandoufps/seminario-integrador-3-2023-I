import { ISidebar } from "@data/interfaces/ui/sidebar.interface";
import { ROLE } from "@data/enums/role.enum";

export const MENU_ITEMS: ISidebar[] = [
  {
    title: "Principal",
    children: [
      {
        title: "Perfil",
        icon: "fas fa-user",
        link: "/admin/account/profile",
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
        expanded: true,
        children: [
          {
            title: 'Mis proyectos',
            link: '/admin/projects/my-projects',
            expanded: false,
            roles: [ROLE.STUDENT, ROLE.TEACHER, ROLE.ADMIN]
          },
          {
            title: 'Crear proyecto',
            link: '/admin/projects/create-project',
            expanded: false,
            roles: [ROLE.STUDENT, ROLE.TEACHER, ROLE.ADMIN]
          }
        ]
      }
    ]
  }
];
