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
export class DepartmentService {
  private REST_API_SERVER = "http://localhost:8085/department";
  constructor(private httpClient : HttpClient) { }
  getDepartment():Observable<any> {
    return this.httpClient.get<any>(this.REST_API_SERVER,httpOptions)
  }
  getDepartmentById(id:number):Observable<any> {
    const url = this.REST_API_SERVER + "/"+id
    return this.httpClient.get<any>(url,httpOptions)
  }
}
