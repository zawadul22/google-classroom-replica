package com.glcl.backend.service;

import com.glcl.backend.Entity.ClassroomEntity;
import com.glcl.backend.Entity.PostEntity;
import com.glcl.backend.Entity.UserEntity;
import com.glcl.backend.model.classroomModel.ClassroomCreateModel;
import com.glcl.backend.model.classroomModel.JoinByCodeModel;
import com.glcl.backend.model.classroomModel.LeaveClassroomModel;
import com.glcl.backend.model.postModel.CreatePostModel;
import com.glcl.backend.model.postModel.GetPostModel;
import com.glcl.backend.model.postModel.UpdatePostModel;
import com.glcl.backend.model.userModel.AddDeleteUserModel;
import com.glcl.backend.repository.*;
import com.glcl.backend.utils.ClassroomUtils;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ClassroomService {
  private final ClassroomRepository classroomRepository;
  private final UserRepository userRepository;
  private final ClassroomUtils classroomUtils;
  private final SubmissionRepository submissionRepository;
  private final AssignmentRepository assignmentRepository;
  private final PostRepository postRepository;
  private final GridFsTemplate gridFsTemplate;
  private final GridFSBucket gridFSBucket;

  public ResponseEntity<Object> createClass(ClassroomCreateModel classroomModel) {
    try {
      Optional<UserEntity> userEntityOptional = userRepository.findByEmail(classroomModel.getTeacherName());
      assert userEntityOptional.isPresent();
      UserEntity userEntity = userEntityOptional.get();
      List<UserEntity> teacher = new ArrayList<>();
      teacher.add(userEntity);
      ClassroomEntity classroomEntity = ClassroomEntity.builder()
              .classroomName(classroomModel.getClassroomName())
              .teachers(teacher)
              .code(classroomUtils.codeGenerate()) // Generate code for joining to classroom
              .creator(userEntity)
              .build();
      classroomRepository.save(classroomEntity);
      ClassroomEntity tempClassroomEntity = classroomRepository.findByClassroomName(classroomModel.getClassroomName());
      List<ClassroomEntity> classroomEntities = userEntity.getClassroom();
      classroomEntities.add(tempClassroomEntity);
      userEntity.setClassroom(classroomEntities);
      userRepository.save(userEntity);
      return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Classroom created successfully"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "Error creating classroom"));
    }
  }

  public ResponseEntity<Object> addUser(AddDeleteUserModel addDeleteUserModel) {
    try {
      int flag = 0;
      Optional<ClassroomEntity> classroomEntityOptional = classroomRepository
              .findById(addDeleteUserModel.getClassroomId());
      ClassroomEntity classroomEntity = new ClassroomEntity();
      if (classroomEntityOptional.isPresent()) {
        classroomEntity = classroomEntityOptional.get();
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
      }
      Iterator<String> iterator1 = addDeleteUserModel.getEmail().iterator();
      List<UserEntity> users = new ArrayList<>();
      while (iterator1.hasNext()) {
        String email = iterator1.next();
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        // Check if the user exists in the system
        if (userEntityOptional.isPresent()) {
          UserEntity userEntity = userEntityOptional.get();
          List<ClassroomEntity> temp = new ArrayList<>(userEntity.getClassroom());
          // Check if the user exists in the classroom
          if (!temp.contains(classroomEntity)) {
            users.add(userEntityOptional.get());
          } else {
            flag = 1;
            break;
          }
        } else {
          flag = 2;
          break;
        }
      }
      if (flag == 1) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Couldn't add because user/s already exist/s in classroom"));
      } else if (flag == 2) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "User/s don't exist"));
      }
      List<UserEntity> prevUsers = new ArrayList<>();
      // Check if the user is a teacher or student and update classroom accordingly
      if (addDeleteUserModel.getType().equalsIgnoreCase("student")) {
        prevUsers = classroomEntity.getStudents();
        prevUsers.addAll(users);
        classroomEntity.setStudents(prevUsers);
        classroomRepository.save(classroomEntity);
        ClassroomEntity finalClassroomEntity = classroomEntity;
        users.forEach(user -> {
          List<ClassroomEntity> prevClasses = user.getClassroom();
          prevClasses.add(finalClassroomEntity);
          user.setClassroom(prevClasses);
          userRepository.save(user);
        });
      } else {
        prevUsers = classroomEntity.getTeachers();
        prevUsers.addAll(users);
        classroomEntity.setTeachers(prevUsers);
        classroomRepository.save(classroomEntity);
        ClassroomEntity finalClassroomEntity = classroomEntity;
        users.forEach(user -> {
          List<ClassroomEntity> prevClasses = user.getClassroom();
          prevClasses.add(finalClassroomEntity);
          user.setClassroom(prevClasses);
          userRepository.save(user);
        });
      }
      return ResponseEntity.status(HttpStatus.OK)
              .body(Map.of("message", addDeleteUserModel.getType() + " have been added successfully"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error adding users"));
    }
  }

  public ResponseEntity<Object> joinByCode(JoinByCodeModel joinByCodeModel) {
    try {
      Optional<UserEntity> userEntityOptional = userRepository.findByEmail(joinByCodeModel.email);
      UserEntity userEntity = new UserEntity();
      if (userEntityOptional.isPresent()) {
        userEntity = userEntityOptional.get();
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
      }
      Optional<ClassroomEntity> classroomEntityOptional = classroomRepository
              .findClassroomEntityByCode(joinByCodeModel.code);
      ClassroomEntity classroomEntity = new ClassroomEntity();
      if (classroomEntityOptional.isPresent()) {
        classroomEntity = classroomEntityOptional.get();
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
      }
      if (userEntity.getClassroom().contains(classroomEntity)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Already exists in this classroom"));
      }
      List<ClassroomEntity> classroomEntities = userEntity.getClassroom();
      classroomEntities.add(classroomEntity);
      userEntity.setClassroom(classroomEntities);
      userRepository.save(userEntity);
      List<UserEntity> students = classroomEntity.getStudents();
      students.add(userEntity);
      classroomEntity.setStudents(students);
      classroomRepository.save(classroomEntity);
      return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Successfully joined the classroom"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "Couldn't join the classroom"));
    }
  }

  public ResponseEntity<Object> getTeachers(String classroomId) {
    Optional<ClassroomEntity> classroomEntityOptional = classroomRepository.findById(classroomId);
    if (classroomEntityOptional.isPresent()) {
      ClassroomEntity classroomEntity = classroomEntityOptional.get();
      return ResponseEntity.status(HttpStatus.OK).body(classroomEntity.getTeachers());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
    }
  }

  public ResponseEntity<Object> getStudents(String classroomId) {
    Optional<ClassroomEntity> classroomEntityOptional = classroomRepository.findById(classroomId);
    if (classroomEntityOptional.isPresent()) {
      ClassroomEntity classroomEntity = classroomEntityOptional.get();
      return ResponseEntity.status(HttpStatus.OK).body(classroomEntity.getStudents());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
    }
  }

  public ResponseEntity<Object> leaveClassroom(LeaveClassroomModel leaveClassroomModel) {
    try {
      Optional<ClassroomEntity> classroomEntityOptional = classroomRepository
              .findById(leaveClassroomModel.getClassroomId());
      if (classroomEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
      }
      ClassroomEntity classroomEntity = classroomEntityOptional.get();
      Optional<UserEntity> userEntityOptional = userRepository.findByEmail(leaveClassroomModel.getEmail());
      if (userEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
      }
      UserEntity userEntity = userEntityOptional.get();
      if (classroomEntity.getCreator() == userEntity) {
        return deleteClassroom(classroomEntity);
      }
      List<ClassroomEntity> tempClasses = userEntity.getClassroom();
      tempClasses.remove(classroomEntity);
      userEntity.setClassroom(tempClasses);
      userRepository.save(userEntity);
      List<UserEntity> tempUsers;
      if (classroomEntity.getStudents().contains(userEntity)) {
        tempUsers = classroomEntity.getStudents();
        tempUsers.remove(userEntity);
        classroomEntity.setStudents(tempUsers);
      } else {
        tempUsers = classroomEntity.getTeachers();
        tempUsers.remove(userEntity);
        classroomEntity.setTeachers(tempUsers);
      }
      classroomRepository.save(classroomEntity);
      return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Left the classroom successfully"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("message", "Couldn't leave the classroom"));
    }
  }

  public ResponseEntity<Object> removeClassroom(LeaveClassroomModel leaveClassroomModel) {
    Optional<ClassroomEntity> classroomEntityOptional = classroomRepository
            .findById(leaveClassroomModel.getClassroomId());
    if (classroomEntityOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
    }
    ClassroomEntity classroomEntity = classroomEntityOptional.get();
    Optional<UserEntity> userEntityOptional = userRepository.findByEmail(leaveClassroomModel.getEmail());
    if (userEntityOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
    }
    UserEntity userEntity = userEntityOptional.get();
    if (classroomEntity.getCreator() == userEntity) {
      return deleteClassroom(classroomEntity);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(Map.of("message", "Only creator can remove a classroom"));
    }
  }

  private ResponseEntity<Object> deleteClassroom(ClassroomEntity classroomEntity) {
    List<UserEntity> tempUsers = userRepository.findUserEntitiesByClassroomContains(classroomEntity);
    tempUsers.forEach(tempUser -> {
      tempUser.getClassroom().remove(classroomEntity);
      userRepository.save(tempUser);
    });
    classroomEntity.getAssignments().forEach(assignment -> {
      submissionRepository.deleteAll(assignment.getSubmissions());
      assignmentRepository.delete(assignment);
    });
    classroomRepository.delete(classroomEntity);
    return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Classroom has been deleted successfully"));
  }

  public ResponseEntity<Object> createPost(CreatePostModel createPostModel, MultipartFile file) {
    try {
      Optional<UserEntity> userEntityOptional = userRepository.findByEmail(createPostModel.getEmail());
      if (userEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
      }
      UserEntity userEntity = userEntityOptional.get();
      Optional<ClassroomEntity> classroomEntityOptional = classroomRepository.findById(createPostModel.getClassroomId());
      if (classroomEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
      }
      ClassroomEntity classroomEntity = classroomEntityOptional.get();
      if (!classroomEntity.getStudents().contains(userEntity) && !classroomEntity.getTeachers().contains(userEntity)) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "The user does not exist in the given classroom"));
      }
      PostEntity postEntity = PostEntity.builder()
              .post(createPostModel.getPost())
              .classroom(classroomEntity)
              .creator(userEntity)
//              .file(file.isEmpty() ? null : new Binary(BsonBinarySubType.BINARY, file.getBytes()))
              .file(file.isEmpty() ? null : gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType()).toString())
              .build();
      postRepository.save(postEntity);
      return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Post has been created successfully"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Couldn't create post"));
    }
  }

  public ResponseEntity<Object> updatePost(UpdatePostModel updatePostModel, MultipartFile file) {
    try {
      Optional<PostEntity> postEntityOptional = postRepository.findById(updatePostModel.getPostId());
      if (postEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Post not found"));
      }
      PostEntity postEntity = postEntityOptional.get();
      Optional<UserEntity> userEntityOptional = userRepository.findByEmail(updatePostModel.getEmail());
      if (userEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
      }
      UserEntity userEntity = userEntityOptional.get();
      if (!postEntity.getCreator().equals(userEntity)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Only creator can update his/her post"));
      }
      if (!updatePostModel.getDescription().isEmpty()) {
        postEntity.setPost(updatePostModel.getDescription());
        postRepository.save(postEntity);
      }
      if (!file.isEmpty()) {
//        postEntity.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        postEntity.setFile(gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType()).toString());
        postRepository.save(postEntity);
      }
      return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Post has been updated successfully"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Couldn't update post"));
    }
  }

  public ResponseEntity<Object> deletePost(UpdatePostModel updatePostModel) {
    try {
      Optional<PostEntity> postEntityOptional = postRepository.findById(updatePostModel.getPostId());
      if (postEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Post not found"));
      }
      PostEntity postEntity = postEntityOptional.get();
      Optional<UserEntity> userEntityOptional = userRepository.findByEmail(updatePostModel.getEmail());
      if (userEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
      }
      UserEntity userEntity = userEntityOptional.get();
      ClassroomEntity classroomEntity = postEntity.getClassroom();
      if (postEntity.getCreator().equals(userEntity) || classroomEntity.getCreator().equals(userEntity)) {
        postRepository.delete(postEntity);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Post has been deleted successfully"));
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Only classroom or post creator can remove a post"));
      }
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Couldn't delete post"));
    }
  }

  public ResponseEntity<Object> getPosts(String classroomId) {
    try {
      Optional<ClassroomEntity> classroomEntityOptional = classroomRepository.findById(classroomId);
      if (classroomEntityOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Classroom not found"));
      }
      ClassroomEntity classroomEntity = classroomEntityOptional.get();
      List<PostEntity> postEntities = new ArrayList<>();
      postEntities = postRepository.getPostEntitiesByClassroom(classroomEntity);
      List<GetPostModel> postModels = new ArrayList<>();
      postModels = postEntities.stream()
              .map(postEntity -> {
                GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(postEntity.getFile())));
                GetPostModel getPostModel = new GetPostModel();
                if (gridFSFile == null) {
                  getPostModel = GetPostModel.builder()
                          .post(postEntity.getPost())
                          .creator(postEntity.getCreator().getName())
                          .build();
                } else {
                  try {
                    InputStream inputStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
                    byte[] fileData = inputStream.readAllBytes();
                    getPostModel = GetPostModel.builder()
                            .post(postEntity.getPost())
                            .creator(postEntity.getCreator().getName())
                            .file(fileData)
                            .build();
                  } catch (Exception e) {
                    e.printStackTrace();
                    getPostModel = GetPostModel.builder()
                            .post(postEntity.getPost())
                            .creator(postEntity.getCreator().getName())
                            .build();
                  }
                }
                return getPostModel;
              }).toList();
      return ResponseEntity.status(HttpStatus.OK).body(postModels);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Couldn't get posts"));
    }
  }
}
