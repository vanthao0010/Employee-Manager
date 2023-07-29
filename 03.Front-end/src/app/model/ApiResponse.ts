export interface ApiResponse {
    code : string
    employeeId? : number
    message : {
        params : string[]
    }
}