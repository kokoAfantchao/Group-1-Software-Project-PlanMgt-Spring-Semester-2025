import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Import FormsModule for ngModel

@Component({
  selector: 'app-effort-tracking-summary-page',
  templateUrl: './effort-tracking-summary-page.component.html',
  styleUrls: ['./effort-tracking-summary-page.component.css'],
  standalone: true,
  imports: [RouterModule, CommonModule, FormsModule] // Add FormsModule here
})
export class EffortTrackingSummaryPageComponent implements OnInit {
  projectId: string | null = null; // To store the current project ID
  effortTrackingData: any[] = []; // To store the filtered effort tracking data
  newEffort: any = null; // To store the new effort being added
  showNewEffortForm: boolean = false; // To control the visibility of the new effort form

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit(): void {
    // Retrieve the project ID from the route parameters
    this.projectId = this.route.snapshot.paramMap.get('id');
    console.log('Project ID from route:', this.projectId); // Log the project ID
    if (this.projectId) {
      this.fetchAllEffortTrackingData(); // Fetch and filter effort tracking data
    }
  }

  fetchAllEffortTrackingData(): void {
    console.log(`Fetching all effort tracking data for Project ID: ${this.projectId}`); // Log the project ID

    this.http.get<any[]>(`http://localhost:8080/api/project-efforts/all`).subscribe({
      next: (data: any[]) => {
        console.log('All Effort Tracking API Response:', data); // Log the API response
        // Filter effort tracking data by project ID
        this.effortTrackingData = data.filter(effort => effort.project.id === Number(this.projectId));
        console.log('Filtered Effort Tracking Data:', this.effortTrackingData); // Log the filtered data
      },
      error: (err) => {
        console.error('Error fetching effort tracking data:', err); // Log any errors
      }
    });
  }

  // Toggle the visibility of the new effort form
  toggleNewEffortForm(): void {
    this.showNewEffortForm = !this.showNewEffortForm;
    if (this.showNewEffortForm) {
      this.newEffort = {
        id: 0, // Default ID for a new effort
        project: {
          id: Number(this.projectId),
          name: '', // Placeholder for project name
          description: '', // Placeholder for project description
          startDate: '', // Placeholder for project start date
          endDate: '', // Placeholder for project end date
          status: '', // Placeholder for project status
          manager: {
            id: 0, // Placeholder for manager ID
            email: '', // Placeholder for manager email
            userName: '', // Placeholder for manager username
            passWord: '', // Placeholder for manager password
            createdAt: new Date().toISOString(),
            updatedAt: new Date().toISOString()
          }
        },
        entryDate: new Date().toISOString().split('T')[0], // Default to today's date
        reqsAnalysis: 0,
        design: 0,
        coding: 0,
        testing: 0,
        projMgmt: 0
      };
    } else {
      this.newEffort = null;
    }
  }

  // Save the new effort to the backend
  saveNewEffort(): void {
    if (this.newEffort) {
      console.log('Saving new effort:', this.newEffort); // Log the newEffort object
      this.http.post('http://localhost:8080/api/project-efforts', this.newEffort).subscribe({
        next: (response) => {
          console.log('New effort saved successfully:', response);
          this.fetchAllEffortTrackingData(); // Refresh the effort tracking data
          this.toggleNewEffortForm(); // Hide the form after saving
        },
        error: (err) => {
          console.error('Error saving new effort:', err);
        }
      });
    }
  }

  // Calculate the total for a specific field
  getTotal(field: string): number {
    return this.effortTrackingData.reduce((total, effort) => total + (effort[field] || 0), 0);
  }
}
