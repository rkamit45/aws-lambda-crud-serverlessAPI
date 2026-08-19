package org.example.service;

import org.example.entity.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private List<Course> courses = new ArrayList<>();

    //Add a new course
    public void addCourse(Course course){
        courses.add(course);
    }
    // Retrieve all courses
    public List<Course> getCourses(){
        return courses;
    }

    //Retrieve a Course by Id
    public Optional<Course> getCourseById(long courseId){
        return courses.stream().filter(course -> course.getCourseId()==courseId).findFirst();
    }

    //Update a Course
    public boolean updateCourse(Course newCourse, long courseId){
        return getCourseById(courseId).map(existingCourse -> {
            courses.remove(existingCourse);
            courses.add(newCourse);
            return true;
        }).orElse(false);
    }

    //Delete Course by courseId
    public boolean deleteCourse(long courseId){
        courses.remove(new Course());
        return courses.removeIf(course->course.getCourseId()==courseId);
    }

}
