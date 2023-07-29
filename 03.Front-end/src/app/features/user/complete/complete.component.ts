import { Component,OnInit } from '@angular/core';

@Component({
  selector: 'app-complete',
  templateUrl: './complete.component.html',
  styleUrls: ['./complete.component.css']
})
export class CompleteComponent {
  message! : string
  ngOnInit(): void {
    this.message = history.state.data
  }
}
