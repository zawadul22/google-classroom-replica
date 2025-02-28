import teacherPoster from "/teacher_poster.jpg";
import studentPoster from "/student_poster.jpg";
import { useLocation, useParams } from "react-router";
import { useQuery } from "@tanstack/react-query";
import { useEffect } from "react";
import { Skeleton } from "./ui/skeleton";

const Posts = ({ type }) => {
  const apiUrl = import.meta.env.VITE_BACKEND_URL;
  const param = useParams();
  const location = useLocation();

  const fetchClassroomName = async () => {
    const response = await fetch(`${apiUrl}/classroom/getClassroomName?classroomId=${param.index}`);
    if (response.ok) {
      const data = await response.json();
      return data;
    }
    else {
      console.log("Could not fetch data")
    }
  }
  const fetchPosts = async () => {
    const response = await fetch(`${apiUrl}/classroom/post/get/${param.index}`);
    if (response.ok) {
      const data = await response.json();
      return data;
    }
    else {
      console.log("Could not fetch data")
    }
  }

  const { data: classroomName, isLoading: gettingName, refetch: refetchName } = useQuery({
    queryKey: ['classroomName'],
    queryFn: fetchClassroomName,
    refetchOnMount: true,
    refetchOnWindowFocus: false,
    refetchOnReconnect: true,
  })

  const { data: posts, isLoading: gettingPosts, refetch: refetchPosts } = useQuery({
    queryKey: ['posts'],
    queryFn: fetchPosts,
    refetchOnMount: true,
    refetchOnWindowFocus: false,
    refetchOnReconnect: true
  })

  // useEffect(() => {
  //   refetchName();
  //   refetchPosts();
  // }, [param.index])


  return (
    <div className="mt-2">
      <div className="relative">
        <img
          src={type === "teacher" ? teacherPoster : studentPoster}
          alt="poster"
          className="w-full rounded-lg"
        />
        <p className="w-48 absolute bottom-3 ml-3 sm:ml-5 text-white text-lg font-semibold sm:text-3xl">
          {gettingName ?
            <Skeleton className="w-32" /> :
            classroomName?.name}
          {/* {classroomName?.name} */}
        </p>
      </div>
    </div>
  )
}

export default Posts
