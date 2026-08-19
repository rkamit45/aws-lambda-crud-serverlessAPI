package org.example.controller;

import org.example.dto.CourseDTO;
import org.example.entity.Course;
import org.example.service.CourseService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    final private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody CourseDTO courseDTO){
        Course course = new Course(courseDTO.getCourseId(), courseDTO.getCourseName(), courseDTO.getPrice());
        courseService.addCourse(course);
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses(){
        return new ResponseEntity<>(courseService.getCourses(), HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable long courseId){
        Optional<Course> courseOptional = courseService.getCourseById(courseId);
        return courseOptional.map(course -> new ResponseEntity<>(course,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Course> updateCourse(@RequestBody CourseDTO courseDTO){
        Course course = new Course(courseDTO.getCourseId(), courseDTO.getCourseName(), courseDTO.getPrice());
        boolean courseUpdated = courseService.updateCourse(course, courseDTO.getCourseId());
        return courseUpdated ? new ResponseEntity<>(course,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{courseId}", produces = "application/json")
    public ResponseEntity<Void> deleteCourse(@PathVariable long courseId){
        boolean deleted = courseService.deleteCourse(courseId);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
