package com.glcl.backend.controller;

import com.glcl.backend.model.classroomModel.ClassroomCreateModel;
import com.glcl.backend.model.classroomModel.JoinByCodeModel;
import com.glcl.backend.model.classroomModel.LeaveClassroomModel;
import com.glcl.backend.model.postModel.CreatePostModel;
import com.glcl.backend.model.postModel.UpdatePostModel;
import com.glcl.backend.model.userModel.AddDeleteUserModel;
import com.glcl.backend.service.ClassroomService;
import com.glcl.backend.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(value = "/classroom")
public class ClassroomController {
  private final ClassroomService classroomService;
  private final FileService fileService;

  @PostMapping(value = "/create")
  public ResponseEntity<Object> createClassController(@RequestBody ClassroomCreateModel classroomCreateModel) {
    return classroomService.createClass(classroomCreateModel);
  }

  @PutMapping(value = "/addUser")
  public ResponseEntity<Object> addUserToClassroomController(@RequestBody AddDeleteUserModel addDeleteUserModel) {
    return classroomService.addUser(addDeleteUserModel);
  }

  @GetMapping(value = "/getClassroomName")
  public ResponseEntity<Object> getClassroomNameController(@RequestParam String classroomId) {
    return classroomService.getClassroomName(classroomId);
  }

  @GetMapping(value = "/getTeachers")
  public ResponseEntity<Object> getTeachersController(@RequestParam String classroomId) {
    return classroomService.getTeachers(classroomId);
  }

  @GetMapping(value = "/getStudents")
  public ResponseEntity<Object> getStudentsController(@RequestParam String classroomId) {
    return classroomService.getStudents(classroomId);
  }

  @DeleteMapping(value = "/deleteUser")
  public ResponseEntity<Object> deleteUserController(@RequestBody AddDeleteUserModel addDeleteUserModel) {
    return null;
  }

  @PatchMapping(value = "/joinByCode")
  public ResponseEntity<Object> joinByCodeController(@RequestBody JoinByCodeModel joinByCodeModel) {
    return classroomService.joinByCode(joinByCodeModel);
  }

  @PatchMapping(value = "/leaveClassroom")
  public ResponseEntity<Object> leaveClassroomController(@RequestBody LeaveClassroomModel leaveClassroomModel) {
    return classroomService.leaveClassroom(leaveClassroomModel);
  }

  @DeleteMapping(value = "/removeClassroom")
  public ResponseEntity<Object> removeClassroomController(@RequestBody LeaveClassroomModel leaveClassroomModel) {
    return classroomService.removeClassroom(leaveClassroomModel);
  }

  @PostMapping(value = "/post/create")
  public ResponseEntity<Object> postCreateController(@RequestPart("body") CreatePostModel createPostModel, @RequestPart("file") MultipartFile file) {
    return classroomService.createPost(createPostModel, file);
  }

  @PatchMapping(value = "/post/update")
  public ResponseEntity<Object> postUpdateController(@RequestPart("body") UpdatePostModel updatePostModel, @RequestPart("file") MultipartFile file) {
    return classroomService.updatePost(updatePostModel, file);
  }

  @DeleteMapping(value = "/post/delete")
  public ResponseEntity<Object> postDeleteController(@RequestBody UpdatePostModel updatePostModel) {
    return classroomService.deletePost(updatePostModel);
  }

  @GetMapping(value = "/post/get/{classroomId}")
  public ResponseEntity<Object> getPostController(@PathVariable("classroomId") String classroomId) {
    return classroomService.getPosts(classroomId);
  }

  @GetMapping(value = "/post/getFile")
  public ResponseEntity<InputStreamResource> getPostFileController(@RequestParam String fileName) throws Exception {
    File fileToDownload = fileService.getFile(fileName);
    return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=\"" + fileToDownload.getName() + "\"")
            .contentLength(fileToDownload.length())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(new InputStreamResource(Files.newInputStream(fileToDownload.toPath())));
  }
}
