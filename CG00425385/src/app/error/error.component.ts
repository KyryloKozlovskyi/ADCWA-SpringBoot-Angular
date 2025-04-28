import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
// Service for handling application errors
import { ErrorService } from './error.service';

/**
 * Component for displaying application errors to users
 */
@Component({
  selector: 'app-error',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css'],
})
export class ErrorComponent implements OnInit {
  // Properties to store error information
  errorMessage: string = '';
  statusCode: number = 0;

  constructor(private router: Router, private errorService: ErrorService) {}

  ngOnInit(): void {
    // Get error details from the error service
    this.errorMessage = this.errorService.errorMessage;
    this.statusCode = this.errorService.statusCode;
  }

  // Navigate back to vehicles list
  goBack(): void {
    this.router.navigate(['/vehicles']);
  }
}
