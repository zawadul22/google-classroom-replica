package com.glcl.backend.service;

import com.glcl.backend.model.AssignmentCreateModel;
import com.glcl.backend.repository.AssignmentRepository;
import com.glcl.backend.repository.ClassroomRepository;
import com.glcl.backend.repository.SubmissionRepository;
import com.glcl.backend.repository.UserRepository;
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
      AssignmentEntity assignmentEntity = AssignmentEntity.builder()
          .title(assignmentCreateModel.getTitle())
          .classroom(classroomEntity)
          .deadline(LocalDateTime.parse(assignmentCreateModel.getDeadline()))
          .description(assignmentCreateModel.getDescription())
          .file(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()))
          .build();
      assignmentRepository.save(assignmentEntity);
      return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Assignment created successfully"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(Map.of("message", "Couldn't create assignment"));
    }
  }
}
