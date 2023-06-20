import { IUser } from '../user.interface';
export interface IAccessTokenDto {
  exp: number;
  iat: number;
  ususario: IUser;
}
