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
export class CertificationService {
  private REST_API_SERVER = "http://localhost:8085/certifications";
  constructor(private httpClient:HttpClient) { }
  public getCertification():Observable<any> {
    const url = this.REST_API_SERVER
    return this.httpClient.get<any>(url,httpOptions)
  }
  getCertificationById(id:number):Observable<any> {
    const url = this.REST_API_SERVER + "/"+id
    return this.httpClient.get<any>(url,httpOptions)
  }
}
