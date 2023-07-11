import { HttpClient, HttpHeaders,HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';


const httpOptions = {
  headers : new HttpHeaders ({
    'Content-Type':'application/json',
  }),
};

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private REST_API_SERVER = "http://localhost:8085/employee";
  constructor(private httpClient:HttpClient) { }
  public getEmployee():Observable<any> {
    const url = this.REST_API_SERVER
    return this.httpClient.get<any>(url,httpOptions)
  }
  public searchEmployees(
    employeeName: string,
    departmentId: string,
  ): Observable<any> {
    let params = new HttpParams();

    if (employeeName) {
      params = params.set('employee_name', employeeName);
    }

    if (departmentId) {
      params = params.set('department_id', departmentId);
    }
    

    return this.httpClient
      .get<any>(this.REST_API_SERVER, { params })
      
  }
  public pagingEmployees(
    employeeName: string,
    departmentId: string,
    pageNumber : number):Observable<any> {
    let params = new HttpParams();

    if (employeeName) {
      params = params.set('employee_name', employeeName);
    }

    if (departmentId) {
      params = params.set('department_id', departmentId);
    }
    if(pageNumber) {
      params = params.set('offset', pageNumber);
    }
    
    return this.httpClient
      .get<any>(this.REST_API_SERVER, { params })
      
  }

  public sortEmployee(
    employeeName: string,
    departmentId: string,
    ordEmployeeName : string,
    ordCertificationName : string,
    ordEndDate : string
  ):Observable<any> {
    let params = new HttpParams();

    if (employeeName) {
      params = params.set('employee_name', employeeName);
    }

    if (departmentId) {
      params = params.set('department_id', departmentId);
    }
    if(ordEmployeeName) {
      params = params.set('ord_employee_name', ordEmployeeName);
    }
    if(ordCertificationName) {
      params = params.set('ord_certification_name', ordCertificationName);
    }
    if(ordEndDate) {
      params = params.set('ord_end_date', ordEndDate);
    }
    return this.httpClient
      .get<any>(this.REST_API_SERVER, { params })
      
  }
  


  
}