import { useRef, useState } from "react";
import { useLocation, useParams } from "react-router";
import { useQuery } from "@tanstack/react-query";

import teacherPoster from "/teacher_poster.jpg";
import studentPoster from "/student_poster.jpg";
import { Skeleton } from "../ui/skeleton";
import { Textarea } from "../ui/textarea";
import { Button } from "../ui/button";
import { Upload } from "lucide-react";
import avatar from "../../assets/man.png";
import { Avatar, AvatarImage } from "../ui/avatar";
import Posts from "./Posts";

const PostSection = ({ type }) => {
  const apiUrl = import.meta.env.VITE_BACKEND_URL;
  const param = useParams();
  // const location = useLocation();

  const [post, setPost] = useState(false);
  const [file, setFile] = useState(null);
  const [postText, setPostText] = useState("");

  console.log("File ", file);
  // console.log("Post ", postText);

  const fileInputRef = useRef(null);

  const handleFileUploader = () => {
    if (fileInputRef.current) {
      fileInputRef.current.click();
    }
  };

  const fetchClassroomName = async () => {
    const response = await fetch(
      `${apiUrl}/classroom/getClassroomName?classroomId=${param.index}`
    );
    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      console.log("Could not fetch data");
    }
  };

  const submitPost = async () => {
    const body = {
      email: "zawad@gmail.com",
      postText: postText,
      classroomId: param.index,
    };
    const formData = new FormData();
    formData.append("body", body);
    if (file) {
      formData.append("file", file);
    }
    else{
      formData.append("file", null);
    }
    // formData.append("email", "zawad@gmail.com");
    // formData.append("post", post);
    // formData.append("classroomId", param.index);

    try {
      const response = await fetch(`${apiUrl}/classroom/post/create`, {
        method: "POST",
        body: formData,
        redirect: "follow",
        headers:{
          "Content-Type": "multipart/form-data",
        }
      });
      if (response.ok) {
        const data = await response.json();
        console.log("Post created successfully", data);
        setPost(false);
        setFile(null);
      } else {
        console.error("Failed to create post");
      }
    } catch (error) {
      console.error("Error creating post:", error);
    }
  };

  const {
    data: classroomName,
    isLoading: gettingName,
    refetch: refetchName,
  } = useQuery({
    queryKey: ["classroomName"],
    queryFn: fetchClassroomName,
    refetchOnMount: true,
    refetchOnWindowFocus: false,
    refetchOnReconnect: true,
  });

  return (
    <div className="mt-2">
      <div className="relative">
        <img
          src={type === "teacher" ? teacherPoster : studentPoster}
          alt="poster"
          className="w-full rounded-lg"
        />
        <div className="w-48 absolute bottom-3 ml-3 sm:ml-5 text-white">
          {gettingName ? (
            <Skeleton className="w-32" />
          ) : (
            <p className="text-lg font-semibold sm:text-3xl">
              {classroomName?.name}
            </p>
          )}
          <p className="mt-1 ml-0.5 font-semibold opacity-80">
            {classroomName?.section}
          </p>
        </div>
      </div>
      <div className="mt-4 border-[1px] rounded-lg">
        {post ? (
          <div className="p-6">
            <Textarea
              placeholder="Announce something to your class"
              onChange={(e) => {
                setPostText(e.target.value);
              }}
            />
            <div className="mt-3 flex w-full justify-between">
              <section className="flex items-center gap-2">
                <Button variant="outline" onClick={handleFileUploader}>
                  <Upload /> Upload
                </Button>
                <input
                  className="hidden"
                  type="file"
                  ref={fileInputRef}
                  // multiple
                  // accept
                  onChange={(e) => setFile(e.target.files[0])}
                />
                {file && <div className="">{file?.name}</div>}
              </section>
              <div>
                <Button
                  variant="ghost"
                  className="text-blue-600 hover:text-blue-600 hover:bg-blue-50"
                  onClick={() => setPost(false)}
                >
                  Cancel
                </Button>
                <Button
                  disabled={file || postText ? false : true}
                  onClick={submitPost}
                  className="ml-2 bg-blue-600 hover:bg-blue-700"
                >
                  Post
                </Button>
              </div>
            </div>
          </div>
        ) : (
          <div
            className="px-6 py-3 cursor-pointer flex items-center gap-4 shadow-md"
            onClick={() => setPost(true)}
          >
            <Avatar className="w-12 h-12">
              <AvatarImage src={avatar} />
            </Avatar>
            <p className="text-gray-500 text-sm hover:text-black">
              Announce something to your class
            </p>
          </div>
        )}
      </div>
      <Posts />
    </div>
  );
};

export default PostSection;
