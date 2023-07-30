import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';


import { DepartmentService } from 'src/app/service/department.service';
import { Department } from 'src/app/model/department';
import { EmployeeDetail } from 'src/app/model/employeeDetail';
import { EmployeeCertification } from 'src/app/model/employeeCertification';
import { CertificationService } from 'src/app/service/certification.service';
import { Certification } from 'src/app/model/certification';
import { AppConstants } from "../../../app-constants";
import { EmployeeService } from 'src/app/service/employee.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent {
  public listDepartment : Department[] = [] // danh sách phòng bàn
  public listCertification : Certification[] = []
  public departmentId!: string; // id phòng ban để tìm kiếm
  public employeeDetail! : EmployeeDetail
  public checkCertificationIsExist : boolean = false
  public messageLoginId : string = ''
  public checkIsValidLoginId: boolean = false
  public messageDepartment : string = ''
  public checkIsValidDepartment : boolean = false
  public messageEmployeeName : string = ''
  public checkIsValidEmployeeName : boolean = false
  public messageEmployeeNameKana : string = ''
  public checkIsValidEmployeeNameKana : boolean = false
  public messageEmployeeBirthDate : string = ''
  public checkIsValidEmployeeBirthDate : boolean = false
  public messageEmployeeEmail : string = ''
  public checkIsValidEmployeeEmail : boolean = false
  public messageEmployeePhone : string = ''
  public checkIsValidEmployeePhone : boolean = false
  public messageEmployeeLoginPassword : string = ''
  public checkIsValidEmployeeLoginPassword : boolean = false
  public messageRePassword : string = ''
  public checkRePassword : boolean = false
  public messageCertificationStartDate : string = ''
  public checkCertificationStartDate : boolean = false
  public messageCertificationEndDate : string = ''
  public checkCertificationEndDate : boolean = false
  public messageCertificationScore : string = ''
  public checkCertificationScore : boolean = false
  public messageEndDateGreaterStart : string = ''
  public checkEndDateStartDate : boolean = false
  public error!:String;
  public employeeForm : any = new FormGroup ({
    employeeName : new FormControl('') ,
    employeeNameKana : new FormControl(''),
    employeeBirthDate : new FormControl(),
    departmentId : new FormControl(''),
    employeeEmail : new FormControl(''),
    employeeTelephone : new FormControl(''),
    employeeLoginId : new FormControl(''),
    employeeLoginPassword : new FormControl(''),
    rePassword : new FormControl(''),
    certifications : new FormGroup ({
      certificationId : new FormControl(''),
      certificationStartDate : new FormControl({value:'',disabled:true}),
      certificationEndDate : new FormControl({value:'',disabled:true}),
      employeeCertificationScore : new FormControl({value:'',disabled:true})
    })
  })

  constructor(private departmentService : DepartmentService,
              private certificationService : CertificationService,
              private employeeService :EmployeeService,
              private router : Router,
              private datePipe:DatePipe){}
  ngOnInit() : void{ 
    // lấy ra list department
    this.departmentService.getDepartment().subscribe((data) =>{
      this.listDepartment = data.object
    })
    //lấy ra list certification
    this.certificationService.getCertification().subscribe((data) =>{
      this.listCertification = data.object
    })
    
    // kiểm tra state trong router có rỗng hay không nếu không thì gán giá trị các formControlName để hiển thị lên template
    if(history.state.data) {
      if(history.state.data instanceof String) {
        this.error = history.state.data
      } else {
        this.employeeDetail = history.state.data
        console.log(this.employeeDetail)
        this.employeeForm.get('employeeLoginId').setValue(this.employeeDetail.employeeLoginId)
        this.employeeForm.get('employeeName').setValue(this.employeeDetail.employeeName)
        this.employeeForm.get('departmentId').setValue(this.employeeDetail.departmentId)
        this.employeeForm.get('employeeNameKana').setValue(this.employeeDetail.employeeNameKana)
        this.employeeForm.get('employeeEmail').setValue(this.employeeDetail.employeeEmail)
        this.employeeForm.get('employeeTelephone').setValue(this.employeeDetail.employeeTelephone)
        this.employeeForm.get('employeeBirthDate').setValue(new Date(this.employeeDetail.employeeBirthDate))
        this.employeeForm.get('employeeLoginPassword').setValue(this.employeeDetail.employeeLoginPassword)
        if(this.employeeDetail.certifications.length != 0) {
          this.employeeForm.get('certifications.certificationId').setValue(this.employeeDetail.certifications[0].certificationId)
          this.employeeForm.get('certifications.certificationStartDate').setValue(new Date(this.employeeDetail.certifications[0].certificationStartDate))
          this.employeeForm.get('certifications.certificationEndDate').setValue(new Date(this.employeeDetail.certifications[0].certificationStartDate))
          this.employeeForm.get('certifications.employeeCertificationScore').setValue(this.employeeDetail.certifications[0].employeeCertificationScore)
        }
      }
    } 
  }

  //check validate employeeLoginId
  isValidLoginId()  {
    const loginId = this.employeeForm.get('employeeLoginId').value
    let pattern = /^[a-zA-Z][a-zA-Z0-9]*$/;
    if(loginId === '') {
      this.checkIsValidLoginId = false
      this.messageLoginId = '「アカウント名」を入力してください'
    }else if(!pattern.test(loginId)) {
      this.checkIsValidLoginId = false
      this.messageLoginId = '[アカウント名]は(a-z, A-Z, 0-9 と _)の桁のみです。最初の桁は数字ではない。'
    }else if(loginId.length > 50) {
      this.checkIsValidLoginId = false
      this.messageLoginId = '50桁以内の「アカウント名」を入力してください'
    }else {
      this.checkIsValidLoginId = true
      this.messageLoginId = ''
    }

  }
  // check đã chọn phòng ban chưa
  isValidDepartment() {
    const departmentId = this.employeeForm.get('departmentId').value
    if(departmentId === '') {
      this.checkIsValidDepartment = false
      this.messageDepartment = '「グループ」を入力してください'
    } else {
      this.checkIsValidDepartment = true
      this.messageDepartment = ''
    }
  }
  //check validate employeeName
  isValidEmployeeName() {
    const employeeName = this.employeeForm.get('employeeName').value
    if(employeeName === '') {
      this.checkIsValidEmployeeName = false
      this.messageEmployeeName = '「氏名」を入力してください'
    }else if(employeeName.length > 125) {
      this.checkIsValidEmployeeName = false
      this.messageEmployeeName = '125桁以内の「氏名」を入力してください'
    } else {
      this.checkIsValidEmployeeName = true
      this.messageEmployeeName = ''
    }
  }
  //check validate EmployeeNameKana
  isValidEmployeeNameKana() {
    const employeeNameKana = this.employeeForm.get('employeeNameKana').value
    const pattern = /^[ァ-ヶー・]+$/;
    if(employeeNameKana === '') {
      this.checkIsValidEmployeeNameKana = false
      this.messageEmployeeNameKana = '「カタカナ氏名」を入力してください'
    } else if(employeeNameKana.length > 125) {
      this.checkIsValidEmployeeNameKana = false
      this.messageEmployeeNameKana = '125桁以内の「カタカナ氏名」を入力してください'
    } else if(!pattern.test(employeeNameKana)) {
      this.checkIsValidEmployeeNameKana = false
      this.messageEmployeeNameKana = '「カタカナ氏名」をカタカナで入力してください'
    } else {
      this.checkIsValidEmployeeNameKana = true
      this.messageEmployeeNameKana = ''
    }
  }
  //check validate employeeBirthDate
  isValidEmployeeBirthDate() {
    const employeeBirthDate  = this.employeeForm.get('employeeBirthDate').value
    console.log(employeeBirthDate)
    if(employeeBirthDate === null) {
      this.checkIsValidEmployeeBirthDate = false
      this.messageEmployeeBirthDate = '「生年月日」を入力してください'
    } else {
      this.checkIsValidEmployeeBirthDate = true
      this.messageEmployeeBirthDate = ''
    }
  }
  
  
  //check validate employeeEmail
  isValidEmployeeEmail() {
    const employeeEmail = this.employeeForm.get('employeeEmail').value
    const pattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if(employeeEmail === '') {
      this.checkIsValidEmployeeEmail = false
      this.messageEmployeeEmail = '「メールアドレス」を入力してください'
    } else if(employeeEmail.length > 125) {
      this.checkIsValidEmployeeEmail = false
      this.messageEmployeeEmail = '125桁以内の「メールアドレス」を入力してください'
    } else if(!pattern.test(employeeEmail)) {
      this.checkIsValidEmployeeEmail = false
      this.messageEmployeeEmail = AppConstants.ER005
    } else {
      this.checkIsValidEmployeeEmail = true
      this.messageEmployeeEmail = ''
    }
  }
  //check validate employeePhone
  isValidEmployeePhone() {
    const employeePhone = this.employeeForm.get('employeeTelephone').value
    const pattern = /^[ -~]\d+$/;
    if(employeePhone === '') {
      this.checkIsValidEmployeePhone = false
      this.messageEmployeePhone = '「電話番号」を入力してください'
    } else if(employeePhone.length > 50) {
      this.checkIsValidEmployeePhone = true
      this.messageEmployeePhone = '50桁以内の「電話番号」を入力してください'
    }else if(!pattern.test(employeePhone)) {
      this.checkIsValidEmployeePhone = false
      this.messageEmployeePhone = '「電話番号」に半角英数を入力してください'
    } else {
      this.checkIsValidEmployeePhone = true
      this.messageEmployeePhone = ''
    }
  }
  //check validate employeeLoginPassword
  isValidEmployeeLoginPassword() {
    const employeeLoginPassword = this.employeeForm.get('employeeLoginPassword').value
    if(employeeLoginPassword.length < 8) {
      this.checkIsValidEmployeeLoginPassword = false
      this.messageEmployeeLoginPassword = '「パスワード」を入力してください'
    }else if(employeeLoginPassword.length > 50) {
      this.checkIsValidEmployeeLoginPassword = false
      this.messageEmployeeLoginPassword = '「パスワード」を8＜＝桁数、＜＝50桁で入力してください'
    } else {
      this.checkIsValidEmployeeLoginPassword = true
      this.messageEmployeeLoginPassword = ''
    }
  }
  //check repassword
  isValidRepassword () {
    const employeeLoginPassword = this.employeeForm.get('employeeLoginPassword').value
    const rePassword = this.employeeForm.get('rePassword').value
    if(this.checkIsValidEmployeeLoginPassword) {
      if(rePassword === employeeLoginPassword) {
        this.checkRePassword = true
        this.messageRePassword = ''
      }else {
        this.checkRePassword = false
        this.messageRePassword = AppConstants.ER017
      }
    }
  }
  //check validate end date 
  isValidEndDate() {
    const certificationId = this.employeeForm.get('certifications.certificationId').value
    if(certificationId != '') {
      const endDate = this.employeeForm.get('certifications.certificationEndDate').value
      console.log(endDate)
      if(endDate === null) {
        this.checkCertificationEndDate = false
        this.messageCertificationEndDate = '「失効日」を入力してください'
      }else if(endDate != ''){
        this.checkCertificationEndDate = true
        this.messageCertificationEndDate = ''
      }
      if(this.checkCertificationStartDate && this.checkCertificationEndDate) {
        const startDate = this.employeeForm.get('certifications.certificationStartDate').value
        const endDate = this.employeeForm.get('certifications.certificationEndDate').value
        if(endDate < startDate) {
          this.checkEndDateStartDate = false
          this.messageEndDateGreaterStart = '「失効日」は「資格交付日」より未来の日で入力してください。';
        }else {
          this.messageEndDateGreaterStart = ''
        }

      }
    }

  }
  //check validate startdate
  isValidStartDate() {
    const certificationId = this.employeeForm.get('certifications.certificationId').value
    if(certificationId != '') {
      const startDate = this.employeeForm.get('certifications.certificationStartDate').value
      if(startDate === null) {
        this.checkCertificationStartDate = false
        this.messageCertificationStartDate = '「資格交付日」を入力してください'
      } else {
        this.checkCertificationStartDate = true
        this.messageCertificationStartDate = ''
      }
    }
  }
  isValidScore() {
    const certificationId = this.employeeForm.get('certifications.certificationId').value
    if(certificationId != '') {
      const score = this.employeeForm.get('certifications.employeeCertificationScore').value
      const pattern = /^\d+$/;
      if(score === '') {
        this.checkCertificationScore = false
        this.messageCertificationScore = '「点数」を入力してください'
      }else if(!pattern.test(score)) {
        this.checkCertificationScore = false
        this.messageCertificationScore = '「点数」に半角英数を入力してください'
      } else {
        this.checkCertificationScore = true
        this.messageCertificationScore = ''
      }
    }
  }
  onSelectChange(event : any) {
    const certificationId = this.employeeForm.get('certifications.certificationId').value
    if(certificationId === '') {
      this.employeeForm.get('certifications.certificationStartDate').disable()
      this.employeeForm.get('certifications.certificationEndDate').disable()
      this.employeeForm.get('certifications.employeeCertificationScore').disable()
      this.employeeForm.get('certifications.certificationStartDate').setValue("")
      this.employeeForm.get('certifications.certificationEndDate').setValue("")
      this.employeeForm.get('certifications.employeeCertificationScore').setValue("")
    } else {
      this.employeeForm.get('certifications.certificationStartDate').enable()
      this.employeeForm.get('certifications.certificationEndDate').enable()
      this.employeeForm.get('certifications.employeeCertificationScore').enable()
    }
  }
  // bắt sự kiện khi click button confirm lấy ra các giá trị formControlName vào truyền vào state của router
  onSubmit() {
    const certificationId = this.employeeForm.get('certifications.certificationId').value
    var certification : EmployeeCertification[] = []
    if(certificationId) {
      const startDate = this.employeeForm.get('certifications.certificationStartDate').value
      const endDate = this.employeeForm.get('certifications.certificationEndDate').value
      certification  = [{
        certificationId : this.employeeForm.get('certifications.certificationId').value,
        certificationStartDate : this.datePipe.transform(startDate,'yyyy/MM/dd') as string,
        certificationEndDate : this.datePipe.transform(endDate,'yyyy/MM/dd') as string,
        employeeCertificationScore : this.employeeForm.get('certifications.employeeCertificationScore').value
      }]
      console.log(certification)
    }
    const dateBirth = this.employeeForm.get('employeeBirthDate')?.value
    const loginId = this.employeeForm.get('employeeLoginId').value
    const departmentId = this.employeeForm.get('departmentId').value
    const employeeName = this.employeeForm.get('employeeName').value
    const employeeNameKana = this.employeeForm.get('employeeNameKana').value
    const employeeBirthDate = this.datePipe.transform(dateBirth,'yyyy/MM/dd') as string
    const employeeEmail = this.employeeForm.get('employeeEmail').value
    const employeeTelephone = this.employeeForm.get('employeeTelephone').value
    const employeeLoginPassword = this.employeeForm.get('employeeLoginPassword').value
    if(this.checkIsValidLoginId && this.checkIsValidDepartment && this.checkIsValidEmployeeName &&
       this.checkIsValidEmployeeNameKana && this.checkIsValidEmployeeBirthDate && this.checkIsValidEmployeeEmail &&
       this.checkIsValidEmployeePhone && this.checkIsValidEmployeeLoginPassword && this.checkRePassword
       ) {
        
        if(certification.length != 0) {
          if(this.checkCertificationStartDate && this.checkCertificationEndDate && this.checkCertificationScore) {
            const employeeDetail : EmployeeDetail = {
              employeeName : employeeName,
              employeeNameKana : employeeNameKana,
              employeeBirthDate : employeeBirthDate,
              departmentId : departmentId,
              employeeEmail : employeeEmail,
              employeeTelephone : employeeTelephone,
              employeeLoginId : loginId,
              employeeLoginPassword : employeeLoginPassword,
              certifications : certification
            }
            this.router.navigate(['/user/confirm'],{state : {data : employeeDetail}})
          }
        }
        
        const employeeDetail : EmployeeDetail = {
          employeeId:0,
          employeeName : employeeName,
          employeeNameKana : employeeNameKana,
          employeeBirthDate : employeeBirthDate,
          departmentId : departmentId,
          employeeEmail : employeeEmail,
          employeeTelephone : employeeTelephone,
          employeeLoginId : loginId,
          employeeLoginPassword : employeeLoginPassword,
          certifications : certification
        }
        this.router.navigate(['/user/confirm'],{state : {data : employeeDetail}})
    } else {
      this.isValidLoginId()
      this.isValidDepartment()
      this.isValidEmployeeName()
      this.isValidEmployeeNameKana()
      this.isValidEmployeeBirthDate()
      this.isValidEmployeeEmail()
      this.isValidEmployeePhone()
      this.isValidEmployeeLoginPassword()
      this.isValidRepassword()
      this.isValidStartDate()
      this.isValidEndDate()
      this.isValidScore()
    }
    
  }
  
 
}
