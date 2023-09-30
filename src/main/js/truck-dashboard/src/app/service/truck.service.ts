import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TruckService {

  private readonly server: string = environment.API_BASE_URL;

  constructor(private http: HttpClient) {
  }

  getAllTruckJourney$ = () => <Observable<any>>
    this.http.get(`${this.server}/api/v1/journey/all`)
      .pipe(
        tap(console.log),
      );

  generateData$ = () => <Observable<any>>
    this.http.post<any>
    (`${this.server}/api/v1/journey/random_data`, null)
      .pipe(
        tap(console.log),
      );

  getStatistics$ = () => <Observable<any>>
    this.http.get(`${this.server}/api/v1/journey/statistics`)
      .pipe(
        tap(console.log),
      );

  simulateWorkflow$ = () => <Observable<any>>
    this.http.post<any>
    (`${this.server}/api/v1/journey/simulate_workflow`, null)
      .pipe(
        tap(console.log),
      );

}
