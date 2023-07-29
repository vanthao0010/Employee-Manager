import { EmployeeCertification } from "./employeeCertification";

export interface EmployeeDetail {
    employeeId : number,
    employeeName : string,
    employeeNameKana : string,
    employeeBirthDate : string,
    departmentId : number,
    employeeEmail : string,
    employeeTelephone : string,
    employeeLoginId : string,
    employeeLoginPassword : string,
    certifications : EmployeeCertification[]
}