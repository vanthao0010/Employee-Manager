import { Component,OnInit  } from '@angular/core';
import { EmployeeDetail } from 'src/app/model/employeeDetail';
import { CertificationService } from 'src/app/service/certification.service';
import { DepartmentService } from 'src/app/service/department.service';
import { Router } from '@angular/router';
import { EmployeeService } from 'src/app/service/employee.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ApiResponse } from 'src/app/model/ApiResponse';


@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.css']
})
export class ConfirmComponent {
  employeeDetail! : EmployeeDetail
  employeeLoginId! : string
  departmentName! : string
  employeeName! : string
  employeeNameKana! : string
  employeeBirthDate! : string
  employeeEmail! : string
  employeeTelephone! : string
  certificationName! : string
  certificationStartDate! : string
  certificationEndDate! : string
  certificationScore! : number
  errorMessage ! : ApiResponse
  constructor(private departmentService : DepartmentService,
              private certificationService : CertificationService,
              private employeeService : EmployeeService,
              private router : Router){}
  ngOnInit() : void {
    // lấy dữ liệu từ state trong router
    this.employeeDetail = history.state.data
    console.log(this.employeeDetail)
    // lấy department theo id gửi trong router
    if(this.employeeDetail) {
      this.employeeLoginId = this.employeeDetail.employeeLoginId
      this.employeeName = this.employeeDetail.employeeName
      this.employeeNameKana = this.employeeDetail.employeeNameKana
      this.employeeBirthDate = this.employeeDetail.employeeBirthDate
      this.employeeEmail = this.employeeDetail.employeeEmail
      this.employeeTelephone = this.employeeDetail.employeeTelephone
      this.departmentService.getDepartmentById(this.employeeDetail.departmentId).subscribe((data) =>{
        this.departmentName = data.departmentName
      })
      //lấy certification theo id gửi trong router
      if(this.employeeDetail.certifications.length != 0) {
        this.certificationService.getCertificationById(this.employeeDetail.certifications[0].certificationId).subscribe((data) =>{
        this.certificationName = data.certificationName
        this.certificationEndDate = this.employeeDetail.certifications[0].certificationEndDate
        this.certificationStartDate = this.employeeDetail.certifications[0].certificationStartDate
        this.certificationScore = this.employeeDetail.certifications[0].employeeCertificationScore
        })
      }
    }
  }
  //function bắt sự kiện back về ADM004
  backToFormAddOrEdit() {
    this.router.navigate(["/user/add"],{state : {data : this.employeeDetail}})
  }
  addEmployee() {
    this.employeeService.addEmployee(this.employeeDetail).subscribe({
        next: (data) => {
          this.router.navigate(['/uer/complete'],{state : {data : "thành công"}})
        },
        error: (error) => {
          this.router.navigate(['/user/add'],{state : {data : error.error.message.params}})
        }
      }
    )
  
     
  }


  
}
