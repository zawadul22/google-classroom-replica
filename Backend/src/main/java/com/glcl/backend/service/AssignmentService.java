package com.glcl.backend.service;

import com.glcl.backend.model.AssignmentCreateModel;
import com.glcl.backend.repository.AssignmentRepository;
import com.glcl.backend.repository.ClassroomRepository;
import com.glcl.backend.repository.SubmissionRepository;
import com.glcl.backend.repository.UserRepository;
import com.glcl.backend.utils.ClassroomUtils;
import lombok.RequiredArgsConstructor;
import com.glcl.backend.Entity.*;

import java.time.LocalDateTime;

import java.time.LocalDateTime;
import java.util.*;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
      if(!classroomUtils.deadlineValidator(assignmentCreateModel.getCreatedAt(), assignmentCreateModel.getDeadline())) {
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
      classroomEntity.setAssignments(Collections.singletonList(assignmentEntity));
      classroomRepository.save(classroomEntity);
      return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Assignment created successfully"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "Couldn't create assignment"));
    }
  }
}
