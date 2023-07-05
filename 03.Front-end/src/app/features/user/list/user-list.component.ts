import { Component } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { AppConstants } from "../../../app-constants";
import { EmployeeService } from '../../../service/employee.service';
import { Employee } from 'src/app/model/employee';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent {
  public listEmployee : Employee[] = []
  constructor(private employeeService : EmployeeService) {}
  ngOnInit(): void {
    this.employeeService.getEmployee().subscribe((data) =>{
      console.log(data)
      this.listEmployee = data
    })
    
  };
}
