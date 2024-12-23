package com.glcl.backend.service;

import com.glcl.backend.model.assignmentModel.AssignmentCreateModel;
import com.glcl.backend.model.assignmentModel.AssignmentSubmitModel;
import com.glcl.backend.model.assignmentModel.DeleteSubmissionModel;
import com.glcl.backend.repository.AssignmentRepository;
import com.glcl.backend.repository.ClassroomRepository;
import com.glcl.backend.repository.SubmissionRepository;
import com.glcl.backend.repository.UserRepository;
import com.glcl.backend.utils.ClassroomUtils;
import lombok.RequiredArgsConstructor;
import com.glcl.backend.Entity.*;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.*;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AssignmentService {
  private final ClassroomRepository classroomRepository;
  private final UserRepository userRepository;
  private final AssignmentRepository assignmentRepository;
  private final SubmissionRepository submissionRepository;
  private final ClassroomUtils classroomUtils;

  public ResponseEntity<Object> assignmentCreate(AssignmentCreateModel assignmentCreateModel, MultipartFile multipartFile) {
    try {
      Optional<UserEntity> userEntityOptional = userRepository.findByEmail(assignmentCreateModel.getCreatedBy());
      if (userEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
      }
      UserEntity userEntity = userEntityOptional.get();
      Optional<ClassroomEntity> classroomEntityOptional = classroomRepository
              .findById(assignmentCreateModel.getClassroomId());
      if (classroomEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
      }

      ClassroomEntity classroomEntity = classroomEntityOptional.get();
      if (!classroomEntity.getTeachers().contains(userEntity)) {
        return ResponseEntity.badRequest().body(Map.of("message", "Only teachers can create assignment"));
      }
      if (!classroomUtils.dateValidator(assignmentCreateModel.getCreatedAt()) || !classroomUtils.dateValidator(assignmentCreateModel.getDeadline())) {
        return ResponseEntity.badRequest().body(Map.of("message", "Invalid date format"));
      }
      if (!classroomUtils.deadlineValidator(assignmentCreateModel.getCreatedAt(), assignmentCreateModel.getDeadline())) {
        return ResponseEntity.badRequest().body(Map.of("message", "Deadline should be after created date"));
      }
      AssignmentEntity assignmentEntity = AssignmentEntity.builder()
              .title(assignmentCreateModel.getTitle())
              .classroom(classroomEntity)
              .createdAt(assignmentCreateModel.getCreatedAt())
              .deadline(assignmentCreateModel.getDeadline())
              .description(assignmentCreateModel.getDescription())
              .file(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()))
              .createdBy(userEntity)
              .build();
      assignmentRepository.save(assignmentEntity);
      classroomEntity.getAssignments().add(assignmentEntity);
      classroomRepository.save(classroomEntity);
      return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Assignment created successfully"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "Couldn't create assignment"));
    }
  }

  public ResponseEntity<Object> assignmentSubmit(AssignmentSubmitModel assignmentSubmitModel, MultipartFile multipartFile) {
    try {
      Optional<UserEntity> userEntityOptional = userRepository.findByEmail(assignmentSubmitModel.getStudentEmail());
      if (userEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
      }
      UserEntity userEntity = userEntityOptional.get();
      Optional<ClassroomEntity> classroomEntityOptional = classroomRepository.findById(assignmentSubmitModel.getClassroomId());
      if (classroomEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
      }
      ClassroomEntity classroomEntity = classroomEntityOptional.get();
      if (classroomEntity.getTeachers().contains(userEntity)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Only students can submit assignment"));
      }
      Optional<AssignmentEntity> assignmentEntityOptional = assignmentRepository.findById(assignmentSubmitModel.getAssignmentId());
      if (assignmentEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Assignment not found"));
      }
      AssignmentEntity assignmentEntity = assignmentEntityOptional.get();
      DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
      LocalDateTime deadline = LocalDateTime.parse(assignmentEntity.getDeadline(), formatter);
      boolean isLate = LocalDateTime.now().isAfter(deadline);
      SubmissionEntity submissionEntity = SubmissionEntity.builder()
              .user(userEntity)
              .assignment(assignmentEntity)
              .submissionDate(LocalDateTime.now())
              .lateSubmission(isLate)
              .file(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()))
              .build();
      submissionRepository.save(submissionEntity);
      assignmentEntity.getSubmissions().add(submissionEntity);
      assignmentRepository.save(assignmentEntity);
      return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Assignment submitted successfully"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Couldn't submit assignment"));
    }
  }

  public ResponseEntity<Object> deleteAssignment(AssignmentSubmitModel assignmentSubmitModel) {
    try {
      Optional<UserEntity> userEntityOptional = userRepository.findByEmail(assignmentSubmitModel.getStudentEmail());
      if (userEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
      }
      UserEntity userEntity = userEntityOptional.get();
      Optional<ClassroomEntity> classroomEntityOptional = classroomRepository.findById(assignmentSubmitModel.getClassroomId());
      if (classroomEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
      }
      ClassroomEntity classroomEntity = classroomEntityOptional.get();
      if (classroomEntity.getStudents().contains(userEntity)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Only teachers can delete assignment"));
      }
      Optional<AssignmentEntity> assignmentEntityOptional = assignmentRepository.findById(assignmentSubmitModel.getAssignmentId());
      if (assignmentEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Assignment not found"));
      }
      AssignmentEntity assignmentEntity = assignmentEntityOptional.get();
      submissionRepository.deleteAll(assignmentEntity.getSubmissions());
      classroomEntity.getAssignments().remove(assignmentEntity);
      classroomRepository.save(classroomEntity);
      assignmentRepository.delete(assignmentEntity);
      return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Assignment deleted successfully"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Couldn't delete assignment"));
    }
  }

  public ResponseEntity<Object> deleteSubmission(DeleteSubmissionModel deleteSubmissionModel) {
    try {
      Optional<UserEntity> userEntityOptional = userRepository.findByEmail(deleteSubmissionModel.getUserEmail());
      if (userEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
      }
      UserEntity userEntity = userEntityOptional.get();
      Optional<SubmissionEntity> submissionEntityOptional = submissionRepository.findById(deleteSubmissionModel.getSubmissionId());
      if (submissionEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Submission not found"));
      }
      SubmissionEntity submissionEntity = submissionEntityOptional.get();
      if (!submissionEntity.getUser().equals(userEntity)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Only students can delete their own assignment"));
      }
      AssignmentEntity assignmentEntity = submissionEntity.getAssignment();
      assignmentEntity.getSubmissions().remove(submissionEntity);
      assignmentRepository.save(assignmentEntity);
      submissionRepository.delete(submissionEntity);
      return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Submission deleted successfully"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Couldn't delete submission"));
    }
  }
}
