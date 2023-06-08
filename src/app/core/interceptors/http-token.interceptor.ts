import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpHeaders, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable, BehaviorSubject, throwError } from 'rxjs';
import { catchError, finalize, switchMap, filter, take } from 'rxjs/operators';

import { HttpApi } from '@core/http/http-api';
import { AuthService } from '@core/auth/auth.service';

@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {

  private refreshTokenInProgress: boolean = false;
  private refreshTokenSubject: BehaviorSubject<boolean | null> = new BehaviorSubject<boolean | null>(null);

  constructor(
    private authService: AuthService
  ) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next
      .handle(this.performRequest(request))
      .pipe(
        catchError((error: HttpErrorResponse) => this.processRequestError(error, request, next)),
        finalize(() => this.processRequestFinally())
      );
  }

  private performRequest(request: HttpRequest<any>): HttpRequest<any> {
    let headers: HttpHeaders = new HttpHeaders({ 'Accept': 'application/json', 'Content-Type': 'application/json' });
    if (this.authService.isLoggedIn() && !this.isApiUrl(request.url)) {
      headers = headers.set('Authorization', `Bearer ${this.authService.accessToken}`);
    }
    return request.clone({ headers });
  }

  private isApiUrl(apiUrl: string): boolean {
    const blockedApiList = [HttpApi.oauth_Token, HttpApi.oauth_Refresh_Token, HttpApi.oauth_Verify_Token];
    return blockedApiList.some((url) => apiUrl.includes(url));
  }

  private processRequestError(error: HttpErrorResponse, req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (error.status === 401 && this.authService.isLoggedIn()) {
      return this.tryAgainWithRefresToken(req, next);
    }
    return throwError(() => error);
  }

  private tryAgainWithRefresToken(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!this.refreshTokenInProgress) {
      this.refreshTokenInProgress = true;
      this.refreshTokenSubject.next(null);
      return this.authService
        .loginWithRefreshToken()
        .pipe(
          switchMap((token: any) => {
            this.refreshTokenInProgress = false;
            this.refreshTokenSubject.next(token.access_token);
            return next.handle(this.performRequest(req));
          }),
          catchError((error: HttpErrorResponse) => {
            this.refreshTokenInProgress = false;
            this.authService.logOut();
            return throwError(() => error);
          }),
          finalize(() => this.processRequestFinally())
        );
    } else {
      return this.refreshTokenSubject
        .pipe(
          filter((token: any) => token != null),
          take(1),
          switchMap(() => next.handle(this.performRequest(req)))
        );
    }
  }

  private processRequestFinally(): void {
    this.refreshTokenInProgress = false;
  }

}
