import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
})
export class HeaderComponent {
  constructor(
    private router: Router,
  ) { }

  logout() {
    sessionStorage.removeItem('access_token');
    this.router.navigate(['login']);
    return false;
  }
  //Cuộn màn hình lên top
  scrollTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}