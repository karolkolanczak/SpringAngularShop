import {Component, Directive, Input, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';
import {HolidayService} from '../holiday.service';
import {Holiday} from '../holiday.model';
import {ActivatedRoute, Params} from '@angular/router';

@Component({
  selector: 'app-holiday-details',
  templateUrl: './holiday-details.component.html',
  styleUrls: ['./holiday-details.component.css']
})


export class HolidayDetailsComponent implements OnInit {

  @Input() holiday: Holiday;
  imageToShow: any;
  isImageLoading: boolean;
  listOfHolidays: Holiday[]=[];

  constructor(private holidayService: HolidayService, private route:ActivatedRoute) { }

  ngOnInit() {

    this.holidayService.getListOfHolidays().subscribe(data => {
      this.listOfHolidays=this.holidayService.convertData(data);
    });

    this.route.params
      .subscribe(
        (params: Params)=>{
          console.log("@@@@@@@@@@@: "+ params['id']);
          this.imageToShow=this.getImageFromService(params['id']);
        }
      );

  }



  getImageFromService(holidayId:number) {
    this.isImageLoading = true;
    this.holidayService.getImage(holidayId).subscribe(data => {
      this.createImageFromBlob(data);
      this.isImageLoading = false;
    }, error => {
      this.isImageLoading = false;
      console.log(error);
    });
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imageToShow = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }


}
