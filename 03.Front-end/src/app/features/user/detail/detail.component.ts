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
  employeeName : string = ''
  employeeBirthDate : string = ''
  departmentName : string = ''
  employeeEmail : string = ''
  employeeTelephone : string = ''
  employeeNameKana : string = ''
  employeeLoginId : string = ''
  certificationName : string = ''
  startDate : string = ''
  endDate : string = ''
  score! : number
  constructor(private employeeService : EmployeeService,private router : Router) {}
  ngOnInit():void {
    if(history.state.data) {
      this.employeeId = history.state.data
      this.employeeService.viewEmployeeDetail(this.employeeId).subscribe((data) => {
        this.employeeLoginId = data.employeeLoginId
        this.departmentName = data.departmentName
        this.employeeName = data.employeeName
        this.employeeNameKana = data.employeeNameKana
        this.employeeBirthDate = data.employeeBirthDate
        this.employeeEmail = data.employeeEmail
        this.employeeTelephone = data.employeeTelephone
        if(data.certifications.length != 0) {
          this.certificationName = data.certifications[0].certificationName
          this.startDate = data.certifications[0].startDate
          this.endDate = data.certifications[0].endDate
          this.score = data.certifications[0].score
        }

      })
    }
  }
  editEmployee() {
    this.router.navigate(["/user/add"],{state : {data : this.employeeId}})
  }
}
