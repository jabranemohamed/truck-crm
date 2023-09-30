import {Component, OnDestroy, OnInit} from '@angular/core';
import {TruckService} from "../service/truck.service";
import {interval, Observable, Subscription} from "rxjs";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  allActiveTruckJourny$!: Observable<any>;
  generatedSubsc!: Subscription;
  workflowSubsc!: Subscription;
  truckMovingCount: number = 0;
  truckFillingCount: number = 0;
  truckUnlodingCount: number = 0;
  truckPauseCount: number = 0;
  truckCustomsCount: number = 0;


  constructor(private truckService: TruckService) {
  }

  ngOnInit(): void {
    interval(3000).subscribe(n =>
      this.allActiveTruckJourny$ = this.truckService.getAllTruckJourney$()
    );

    interval(5000).subscribe(n =>
      this.truckService.getStatistics$().subscribe((response) => {
        this.truckCustomsCount = response["AT_CUSTOMS"];
        this.truckMovingCount = response["DRIVING"];
        this.truckFillingCount = response["LOADING"];
        this.truckUnlodingCount = response["UNLOADING"];
        this.truckPauseCount = response["PAUSE"];
      })
    );

  }


  generateData() {
    this.generatedSubsc = this.truckService.generateData$().subscribe();
  }

  ngOnDestroy() {
    this.generatedSubsc.unsubscribe();
  }

  simulateWorkflow() {
  this.workflowSubsc = this.truckService.simulateWorkflow$().subscribe();
  }
  computePercentage(a:number,b:number): string{
    return parseFloat( String((a / b) * 100)).toFixed(2) + "%";
  }
  getStyle(a:number,b:number): any{
    let aa = parseFloat( String((a / b) * 100)).toFixed(2) +"%";
    let myStyles = {
      'width': aa
    };
    return myStyles;
  }
}
