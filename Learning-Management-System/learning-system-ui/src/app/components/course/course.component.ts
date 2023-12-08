// admin.component.ts
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CourseService } from 'path-to-course-service';

@Component({
    selector: 'app-admin',
    templateUrl: './admin.component.html',
    styleUrls: ['./admin.component.css']
})
export class AdminComponent {

    courseForm: FormGroup;

    constructor(private fb: FormBuilder, private courseService: CourseService) {
        this.courseForm = this.fb.group({
            name: ['', Validators.required],
            startDate: ['', Validators.required],
            endDate: ['', Validators.required],
        });
    }

    onCreateCourse() {
        if (this.courseForm.valid) {
            const courseData = this.courseForm.value;
            this.courseService.createCourse(courseData).subscribe(
                response => {
                    console.log('Course creation successful');
                    // Handle success, e.g., display a success message
                },
                error => {
                    console.error('Course creation failed:', error);
                    // Handle error, e.g., display an error message
                }
            );
        }
    }
}
