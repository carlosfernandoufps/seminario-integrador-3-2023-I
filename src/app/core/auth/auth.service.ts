import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Router } from '@angular/router';

import { CookieService } from 'ngx-cookie-service';
import { environment } from '@env/environment';
import { HttpApi } from '@core/http/http-api';
import { JwtHelperService } from '@auth0/angular-jwt';
import { IAccessTokenDto } from '@app/data/interfaces/http/dto/access-token-dto.interface';
import { IUserDto } from '@data/interfaces/http/dto/user-dto.interface';
import { ITokenDto } from '@data/interfaces/http/dto/token-dto.interface';
import { LocalStorageService } from '../storage/local-storage.service';
import { ROLE } from '@data/enums/role.enum';
import { User } from '@data/models/User.model';
import { IUser } from '@app/data/interfaces/http/user.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly url = `${environment.baseUrlAuth}`;
  private headers: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': `Bearer ${this.accessToken}` });
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;
  private jwtHelper: JwtHelperService;
  private token: IAccessTokenDto;

  constructor(
    private http: HttpClient,
    private _cookieService: CookieService,
    private _localStorageService: LocalStorageService,
    private router: Router,
  ) {
    this.currentUserSubject = new BehaviorSubject<User>(((this.getUser()) ? this.getUser() : '{}') as User);
    this.currentUser = this.currentUserSubject.asObservable();
    this.jwtHelper = new JwtHelperService();
    this.token = {} as IAccessTokenDto;
  }

  setUser(user: User): void {
    this._localStorageService.setItem('currentUser', user);
  }

  getUser(): User {
    return (this._localStorageService.getItem<User>('currentUser')!);
  }

  public getCurrentUserSubject(): User {
    return this.currentUserSubject.getValue();
  }

  public getCurrentUser(): Observable<User> {
    return this.currentUser;
  }

  public set currentUserValue(user: User) {
    this.currentUserSubject.next(user);
  }

  public get codigoUsuario(): string {
    return this.getCurrentUserSubject().codigo;
  }

  public isLoggedIn(): boolean {
    return this._cookieService.check('access');
  }

  get accessToken() {
    return this._cookieService.get('access');
  }

  get refreshToken() {
    return this._cookieService.get('refresh');
  }

  public signIn(data: IUserDto): Observable<ITokenDto> {
    return this.http.post<ITokenDto>(`${this.url}/${HttpApi.oauth_Token}`, data)
      .pipe(
        tap((res: ITokenDto) => {
          this.token = this.jwtHelper.decodeToken(res.token)!;
          this._cookieService.set('access', res.token, new Date(this.token.exp * 1000), '/');
          this._cookieService.set('refresh', res.token, new Date(this.token.exp * 1000), '/');
          this.setUser(this.token.usuario);
          this.currentUserSubject.next(this.getUser());
        }),
        catchError(this.handleError)
      );
  }

  public signUp(data: IUser): Observable<ITokenDto> {
    const user: IUserDto = { correo: 'admin@mail.com', password: 'admin' } as IUserDto;
    this.signIn(user).subscribe();
    data.rol = ROLE.STUDENT;
    return this.http.post<ITokenDto>(`${environment.baseUrlMembers}`, data, { headers: this.headers })
  }

  public loginWithRefreshToken(): Observable<ITokenDto> {
    const data = { refresh: this.refreshToken };
    return this.http.post<ITokenDto>(`${this.url}/${HttpApi.oauth_Refresh_Token}/`, JSON.stringify(data));
  }

  public logOut(): void {
    this._localStorageService.clear();
    this._cookieService.delete('access', '/');
    this._cookieService.delete('refresh', '/');
    this.currentUserSubject = new BehaviorSubject<User>({} as User);
    this.currentUser = this.currentUserSubject.asObservable();
    this.router.navigate(['/auth']);
  }

  public hasRole(roles: ROLE[]): boolean {
    return roles.some((role: ROLE) => this.currentUserSubject.getValue().rol === role);
  }

  public hasRoles(roles: string[]): boolean {
    return roles.some((role: string) => this.currentUserSubject.getValue().rol === role);
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    return throwError(() => error);
  }

}
