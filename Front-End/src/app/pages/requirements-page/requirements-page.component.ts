import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common'; // Import CommonModule for *ngIf and *ngFor
import { RouterModule } from '@angular/router'; // Import RouterModule for routerLink

@Component({
  selector: 'app-requirements-page',
  templateUrl: './requirements-page.component.html',
  styleUrls: ['./requirements-page.component.css'],
  standalone: true, // Ensure this is a standalone component
  imports: [CommonModule, RouterModule] // Add required modules here
})
export class RequirementsPageComponent implements OnInit {
  projectId: string | null = null;
  projectDetails: any = null; // To store the fetched project details
  requirements: any[] = []; // To store the filtered requirements
  functionalRequirements: any[] = []; // To store functional requirements
  nonFunctionalRequirements: any[] = []; // To store non-functional requirements
  newRequirement: any = null; // To store the new requirement
  showNonFunctionalForm: boolean = false; // To toggle the non-functional form

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit(): void {
    // Retrieve the project ID from the route parameters
    this.projectId = this.route.snapshot.paramMap.get('id');
    console.log('Project ID from route:', this.projectId); // Log the project ID
    if (this.projectId) {
      this.fetchProjectDetails(this.projectId);
      this.fetchAllRequirements(); // Fetch and filter requirements
    }
  }

  fetchProjectDetails(id: string): void {
    this.http.get(`http://localhost:8080/api/projects/${id}`).subscribe({
      next: (data) => {
        console.log('Fetched Project Details:', data); // Log the fetched data
        this.projectDetails = data; // Assign the fetched project details
      },
      error: (err) => {
        console.error('Error fetching project details:', err); // Log any errors
      }
    });
  }

  fetchAllRequirements(): void {
    console.log(`Fetching all requirements for Project id ${this.projectId}`); // Log the project ID

    this.http.get<any[]>(`http://localhost:8080/api/project-requirements`).subscribe({
      next: (data: any[]) => {
        console.log('All Requirements API Response:', data); // Log the API response
        // Convert projectId to a number for comparison
        const projectIdNumber = Number(this.projectId);

        // Filter requirements by project ID
        const filteredRequirements = data.filter(requirement => requirement.project.id === projectIdNumber);

        // Split requirements into functional and non-functional
        this.functionalRequirements = filteredRequirements.filter(req => req.type === 0);
        this.nonFunctionalRequirements = filteredRequirements.filter(req => req.type === 1);

        console.log('Functional Requirements:', this.functionalRequirements); // Log functional requirements
        console.log('Non-Functional Requirements:', this.nonFunctionalRequirements); // Log non-functional requirements
      },
      error: (err) => {
        console.error('Error fetching requirements:', err); // Log any errors
      }
    });
  }

  addRequirement(type: number): void {
    this.newRequirement = {
      id: 0,
      description: '', // Placeholder for the requirement description
      priority: 0, // Default priority
      type: type, // 0 for functional, 1 for non-functional
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
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
      }
    };

    // Send the POST request to the backend
    this.http.post('http://localhost:8080/api/project-requirements', this.newRequirement).subscribe({
      next: (response) => {
        console.log('Requirement added successfully:', response);
        this.fetchAllRequirements(); // Refresh the requirements list
      },
      error: (err) => {
        console.error('Error adding requirement:', err);
      }
    });
  }

  toggleNonFunctionalForm(): void {
    this.showNonFunctionalForm = !this.showNonFunctionalForm;
    if (this.showNonFunctionalForm) {
      this.newRequirement = {
        id: 0,
        description: '', // Placeholder for the requirement description
        priority: 0, // Default priority
        type: 1, // Non-functional requirement
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString(),
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
        }
      };
    } else {
      this.newRequirement = null;
    }
  }
}
