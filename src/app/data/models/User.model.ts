import { IUser } from "@data/interfaces/http/user.interface";

export class User implements IUser {
  constructor(
    public id: number,
    public codigo: string,
    public nombre: string,
    public apellido: string,
    public correo: string,
    public rol: string,
    public password: string
  ) { }
}
