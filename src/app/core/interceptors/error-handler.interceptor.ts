import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

import { AuthService } from '@core/auth/auth.service';
import { HttpError } from '@data/models/HttpError.model';

@Injectable()
export class ErrorHandlerInterceptor implements HttpInterceptor {

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request)
      .pipe(catchError((error: HttpErrorResponse) => {
        switch (error.status) {
          case 401:
            this.authService.logOut();
            this.router.navigate(['/auth/sign-in']);
            break;
          case 403 || 404 || 500:
            this.router.navigate(['/not-found']);
            break;
        }

        let customError: HttpError;
        try {
          customError = HttpError.fromJSON(error.error);
        } catch (e) {
          customError = new HttpError('Unknown', 'Unknown', 'Unknown');
        }
        return throwError(() => customError);
      }));
  }
}
