package com.glcl.backend.service;

import com.glcl.backend.model.AssignmentCreateModel;
import com.glcl.backend.repository.AssignmentRepository;
import com.glcl.backend.repository.ClassroomRepository;
import com.glcl.backend.repository.SubmissionRepository;
import com.glcl.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService {
  private final ClassroomRepository classroomRepository;
  private final UserRepository userRepository;
  private final AssignmentRepository assignmentRepository;
  private final SubmissionRepository submissionRepository;

  public ResponseEntity<Object> assignmentCreate(AssignmentCreateModel assignmentCreateModel) {
    
    return null;
  }
}
