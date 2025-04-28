import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ErrorService } from './error.service';

@Component({
  selector: 'app-error',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {
  errorMessage: string = '';
  statusCode: number = 0;

  constructor(
    private router: Router,
    private errorService: ErrorService
  ) {}

  ngOnInit(): void {
    // Get error details from the error service
    this.errorMessage = this.errorService.errorMessage;
    this.statusCode = this.errorService.statusCode;
  }

  goBack(): void {
    this.router.navigate(['/vehicles']);
  }
}
