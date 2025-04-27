import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common'; // Import CommonModule for *ngIf and *ngFor
import { RouterModule } from '@angular/router'; // Import RouterModule for routerLink
import { ReactiveFormsModule } from '@angular/forms'; // Import ReactiveFormsModule for formGroup

@Component({
  selector: 'app-new-project',
  templateUrl: './new-project.component.html',
  styleUrls: ['./new-project.component.css'],
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule], // Add required modules here
})
export class NewProjectComponent implements OnInit {
  projectForm: FormGroup; // Form for project details
  managers: any[] = []; // List of available managers

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {
    // Initialize the form
    this.projectForm = this.fb.group({
      name: ['', [Validators.required]], // Project name is required
      description: ['', [Validators.required]], // Description is required
      startDate: ['', [Validators.required]], // Start date is required
      endDate: ['', [Validators.required]], // End date is required
      status: ['', [Validators.required]], // Status is required
      managerId: ['', [Validators.required]], // Manager selection is required
    });
  }

  ngOnInit(): void {
    this.fetchManagers(); // Fetch the list of managers using the new method
  }

  fetchManagers(): void {
    this.http.get('http://localhost:8080/api/project-managers/all').subscribe({
      next: (data: any[]) => {
        console.log('Fetched managers:', data);
        this.managers = data; // Assign the fetched managers to the component property
      },
      error: (err) => {
        console.error('Error fetching managers:', err); // Log the error
      }
    });
  }

  // Submit the form data
  onSubmit(): void {
    if (this.projectForm.valid) {
      const projectData = {
        name: this.projectForm.value.name,
        description: this.projectForm.value.description,
        startDate: new Date(this.projectForm.value.startDate).toISOString().split('T')[0], // Format as YYYY-MM-DD
        endDate: new Date(this.projectForm.value.endDate).toISOString().split('T')[0], // Format as YYYY-MM-DD
        status: this.projectForm.value.status,
        manager: {
          id: this.projectForm.value.managerId, // Use the selected manager ID
        },
      };

      console.log('Submitting project data:', projectData); // Log the data being sent

      this.http.post('http://localhost:8080/api/projects/create', projectData).subscribe({
        next: (response) => {
          console.log('Project created successfully:', response);
          this.router.navigate(['/dashboard']); // Navigate to the dashboard after success
        },
        error: (err) => {
          console.error('Error creating project:', err); // Log the error response
        },
      });
    } else {
      console.error('Form is invalid');
    }
  }
}
