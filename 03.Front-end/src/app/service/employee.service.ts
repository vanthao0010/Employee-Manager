import { HttpClient, HttpHeaders} from '@angular/common/http';
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
  private REST_API_SERVER = "http://localhost:8085/employee/get";
  constructor(private httpClient:HttpClient) { }
  public getEmployee():Observable<any> {
    const url = this.REST_API_SERVER
    return this.httpClient.get<any>(url,httpOptions)
  }
  
}