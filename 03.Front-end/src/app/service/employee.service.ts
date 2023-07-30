import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { EmployeeDetail } from '../model/employeeDetail';
import { ApiResponse } from '../model/ApiResponse';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private REST_API_SERVER = "http://localhost:8085/employee";
  private error!: ApiResponse;
  constructor(private httpClient: HttpClient) { }
  public getEmployee(): Observable<any> {
    const url = this.REST_API_SERVER
    return this.httpClient.get<any>(url, httpOptions)
  }
  public pagingEmployees(
    employeeName: string,
    departmentId: string,
    pageNumber: number): Observable<any> {
    let params = new HttpParams();

    if (employeeName) {
      params = params.set('employee_name', employeeName);
    }

    if (departmentId) {
      params = params.set('department_id', departmentId);
    }
    if (pageNumber) {
      params = params.set('offset', pageNumber);
    }

    return this.httpClient
      .get<any>(this.REST_API_SERVER, { params })

  }

  public searchAndSortEmployee(
    employeeName: string,
    departmentId: string,
    ordEmployeeName: string,
    ordCertificationName: string,
    ordEndDate: string
  ): Observable<any> {
    let params = new HttpParams();

    if (employeeName) {
      params = params.set('employee_name', employeeName);
    }
    if (departmentId) {
      params = params.set('department_id', departmentId);
    }
    if (ordEmployeeName) {
      params = params.set('ord_employee_name', ordEmployeeName);
    }
    if (ordCertificationName) {
      params = params.set('ord_certification_name', ordCertificationName);
    }
    if (ordEndDate) {
      params = params.set('ord_end_date', ordEndDate);
    }
    return this.httpClient
      .get<any>(this.REST_API_SERVER, { params })
  }
  public addEmployee(data: EmployeeDetail): Observable<ApiResponse> {
    return this.httpClient.post<ApiResponse>(this.REST_API_SERVER, data, httpOptions)
  }

  public setError(data: any) {
    this.error = data;
  }

  public getError() {
    return this.error;
  }
}