import { Component } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Router } from '@angular/router';

import { EmployeeService } from '../../../service/employee.service';
import { Employee } from 'src/app/model/employee';
import { DepartmentService } from 'src/app/service/department.service';
import { Department } from 'src/app/model/department';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent {
  public listEmployee : Employee[] = [] //danh sách nhân viên
  public listDepartment : Department[] = [] // danh sách phòng bàn
  public employeeName!: string; //tên nhân viên để tìm kiếm
  public departmentId: string = ""; // id phòng ban để tìm kiếm
  public totalPage !: number; // tổng số page
  public totalRecord !: number; // tông số bản ghi api trả về
  public pageArray : number[] = []; // mảng cách page
  public offset = 0; // bản ghi
  public conditionOrders : string[] = ["ASC","DESC"] //  sắp xếp tăng dân hoặc giảm dân
  public ordEmployeeName : string = this.conditionOrders[0] //  sắp xếp theo tên nhân viên
  public ordCertificationName : string = this.conditionOrders[0] // sắp xếp theo tên chứng chỉ
  public ordEndDate : string = this.conditionOrders[0] // sắp xếp theo ngày hết hạn chứng chỉ
  constructor(public http: HttpClient,
              private employeeService : EmployeeService,
              private departmentService : DepartmentService,
              private router : Router){}
  
  ngOnInit(): void {
    this.employeeService.getEmployee().subscribe((data) =>{
      console.log("tong so page",this.getTotalPage())
    
      this.totalRecord = data.totalRecords
      this.pageArray = Array.from({ length: this.getTotalPage() }, (_, index) => index + 1);

      this.listEmployee = data.object
    })
    this.departmentService.getDepartment().subscribe((data) =>{
      console.log(data)
      this.listDepartment = data.object
    })
  };
  //function lấy ra tổng số page từ số bản ghi trả về
  getTotalPage() {
    let totalPage = this.totalRecord / 20
    if((this.totalRecord % 20) != 0) {
      totalPage = Math.floor(this.totalRecord / 20) + 1
    } else {
      totalPage = this.totalRecord / 20
    }
    return totalPage
    
  }
  // kiểm tra page để disable các button
  checkPage() {
    return this.offset === (Math.max(...this.pageArray)-1)
  }
  // lấy ra dữ liệu phòng ban đã select để tìm kiếm
  onDepartmentChange(event: any) {
    const selectedValue = event.target.value;
    this.departmentId = selectedValue ? selectedValue : null;
  }
  // function gọi đến search Employee service để tìm kiếm
  searchEmployees() {
    this.employeeService
      .searchAndSortEmployee(this.employeeName, this.departmentId,this.ordEmployeeName,this.ordCertificationName,this.ordEndDate)
      .subscribe((data) => {
        this.totalRecord = data.totalRecords
        this.pageArray = Array.from({ length: this.getTotalPage() }, (_, index) => index + 1);
        this.listEmployee = data.object;
      });
  }
  // function gọi đến pagingEmployee service để phân trang
  pagingEmployees(page:number) {
    this.offset = page
    console.log(this.offset)
    this.employeeService
      .pagingEmployees(this.employeeName, this.departmentId,page)
      .subscribe((data) => {
        this.totalRecord = data.totalRecords
        this.listEmployee = data.object;
      });
  }
  // bắt sự kiện click button >
  upPageChange() {
    this.offset = this.offset + 1
    this.pagingEmployees(this.offset)
  }
  // bắt sự kiện click button <
  downPageChange() {
    this.offset = this.offset -1
    this.pagingEmployees(this.offset)
  }
  // khi click button employeeName
  sortListEmployeeName() {
    if(this.ordEmployeeName == this.conditionOrders[0]) {
      this.ordEmployeeName = this.conditionOrders[1]
    } else {
      this.ordEmployeeName = this.conditionOrders[0]
    }
    this.employeeService.searchAndSortEmployee(this.employeeName, this.departmentId,this.ordEmployeeName,this.ordCertificationName,this.ordEndDate)
                        .subscribe((data) => {
                          this.listEmployee = data.object
                        })
    
  }
  //function thực hiện khi click button sắp xếp tên chứng chỉ
  sortListCertification() {
    if(this.ordCertificationName == this.conditionOrders[0]) {
      this.ordCertificationName = this.conditionOrders[1]
    } else {
      this.ordCertificationName = this.conditionOrders[0]
    }
    this.employeeService.searchAndSortEmployee(this.employeeName, this.departmentId,this.ordEmployeeName,this.ordCertificationName,this.ordEndDate)
                        .subscribe((data) => {
                          this.listEmployee = data.object
                        })
    
  }
  // function thực hiện khi click button sắp xếp ngày hết hạn
  sortListEndDate() {
    if(this.ordEndDate == this.conditionOrders[0]) {
      this.ordEndDate = this.conditionOrders[1]
    } else {
      this.ordEndDate = this.conditionOrders[0]
    }
    this.employeeService.searchAndSortEmployee(this.employeeName, this.departmentId,this.ordEmployeeName,this.ordCertificationName,this.ordEndDate)
                        .subscribe((data) => {
                          this.listEmployee = data.object
                        })
    
  }
  isSortASC(ordSort:string) {
    return ordSort == "ASC"
  }
  viewDetailEmployee(employeeId:number) {
   this.router.navigate(["/user/detail"],{state : {data : employeeId}})
  }

}
