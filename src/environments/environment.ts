// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

const SERVER = {
  protocol: 'http',
  hostname: 'localhost',
  port: 3000,
};

const SERVER_PROD = {
  protocol: 'http',
  hostname: '3.15.147.189',
  port: 8090,
};

export const environment = {
  production: false,
  encrypt: true,
  keyEcrypt: 'Fr0nT3nD2023',
  baseUrlAuth: `${SERVER_PROD.protocol}://${SERVER_PROD.hostname}:${SERVER_PROD.port}/api/v1/auth`,
  baseUrlProjects: `${SERVER_PROD.protocol}://${SERVER_PROD.hostname}:${SERVER_PROD.port}/api/v1/proyectos`,
  baseUrlMembers: `${SERVER_PROD.protocol}://${SERVER_PROD.hostname}:${SERVER_PROD.port}/api/v1/integrantes`,
  baseUrlSubjects: `${SERVER_PROD.protocol}://${SERVER_PROD.hostname}:${SERVER_PROD.port}/api/v1/materias`,
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
