import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { EmployeeService } from 'src/app/service/employee.service';


@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent {
  employeeId! : number
  employeeDetail : any
  constructor(private employeeService : EmployeeService,private router : Router) {}
  ngOnInit():void {
    if(history.state.data) {
      this.employeeId = history.state.data
      this.employeeService.viewEmployeeDetail(this.employeeId).subscribe((data) => {
        this.employeeDetail = data
        console.log(data.certifications)
      })
    }
  }
  editEmployee() {
    this.router.navigate(["/user/add"],{state : {data : this.employeeId}})
  }
}
