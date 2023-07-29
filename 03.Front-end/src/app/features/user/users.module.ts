import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { UsersRoutingModule } from './users-routing.module';
import { UserListComponent } from './list/user-list.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { FormsModule } from '@angular/forms';
import { UserFormComponent } from './user-form/user-form.component';
import { ConfirmComponent } from './confirm/confirm.component';
import { DatePipe } from '@angular/common';
import { CompleteComponent } from './complete/complete.component';



@NgModule({
  declarations: [
    UserListComponent,
    UserFormComponent,
    ConfirmComponent,
    CompleteComponent,
  ],
  imports: [
    BrowserAnimationsModule,
    BsDatepickerModule.forRoot(),
    CommonModule,
    SharedModule,
    UsersRoutingModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers :  [
    DatePipe
  ]
})
export class UsersModule { }
