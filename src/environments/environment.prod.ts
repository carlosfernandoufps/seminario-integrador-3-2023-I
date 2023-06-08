const SERVER = {
  protocol: 'http',
  hostname: '3.15.147.189',
  port: 8090,
};

export const environment = {
  production: true,
  encrypt: true,
  keyEcrypt: 'Fr0nT3nD2023',
  baseUrlAuth: `${SERVER.protocol}://${SERVER.hostname}:${SERVER.port}/api/v1/auth`,
  baseUrlProjects: `${SERVER.protocol}://${SERVER.hostname}:${SERVER.port}/api/v1/proyectos`,
  baseUrlMembers: `${SERVER.protocol}://${SERVER.hostname}:${SERVER.port}/api/v1/integrantes`,
};
