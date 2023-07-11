import { Component } from '@angular/core';

import { DepartmentService } from 'src/app/service/department.service';
import { Department } from 'src/app/model/department';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent {
  public listDepartment : Department[] = [] // danh sách phòng bàn
  public departmentId!: string; // id phòng ban để tìm kiếm
  constructor(private departmentService : DepartmentService){}
  ngOnInit() : void{
    this.departmentService.getDepartment().subscribe((data) => {
      console.log(data.object)
      this.listDepartment = data.object
    })
  }
}
