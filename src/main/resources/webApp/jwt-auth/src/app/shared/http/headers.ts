import { Observable, of } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';

export class Utils{

  get token():string {

    return !localStorage.getItem("token") ? null : localStorage.getItem("token");

}

public createTokenHeader(): HttpHeaders {

    let reqOptions = new HttpHeaders().set( 'Content-Type', 'application/json' )

    if(this.token) {

        reqOptions = new HttpHeaders().set( 'Content-Type', 'application/json' ).set('Authorization', 'Bearer ' + this.token);

    }

    return reqOptions;

}

public handleError<T> (operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {

    // TODO: send the error to remote logging infrastructure
    console.error(error); // log to console instead

    // TODO: better job of transforming error for user consumption
    this.log(`${operation} failed: ${error.message}`);

    // Let the app keep running by returning an empty result.
    return of(result as T);
  };
}

/** Log a HeroService message with the MessageService */
private log(message: string) {
 console.log(message);
}

}
