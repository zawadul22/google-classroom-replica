import { useQuery } from "@tanstack/react-query";
import { useParams } from "react-router";
import avatar from "../../assets/man.png"
import { Avatar, AvatarImage } from "../ui/avatar";
import { Skeleton } from "../ui/skeleton";
import FileViewer from "./FileViewer";

const Posts = () => {
  const apiUrl = import.meta.env.VITE_BACKEND_URL;
  const param = useParams();

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

  const { data: posts, isLoading: gettingPosts, refetch: refetchPosts } = useQuery({
    queryKey: ['posts'],
    queryFn: fetchPosts,
    refetchOnMount: true,
    refetchOnWindowFocus: false,
    refetchOnReconnect: true
  })

  // console.log(posts?.[0]?.fileName)

  return (
    <div>
      {gettingPosts ? (
        <div className="p-6">
          <Skeleton className="h-10 w-full" />
          <Skeleton className="h-10 w-full mt-2" />
          <Skeleton className="h-10 w-full mt-2" />
        </div>
      ) : (
        posts?.length === 0 ? (
          <div className="mt-8 flex items-center justify-center">
            <p className="text-2xl font-semibold text-gray-500">No posts yet</p>
          </div>
        ) :
          posts?.map((post, index) => (
            <div key={index}>
              <div className="p-6 border-[1px] mt-4 rounded-t-lg roun">
                <div className="flex items-center gap-4">
                  <Avatar className="w-12 h-12">
                    <AvatarImage src={avatar} />
                  </Avatar>
                  <div>
                    <p className="font-semibold">
                      {post?.creator}
                    </p>
                    <p className="text-xs text-gray-500">
                      22 April 2024
                    </p>
                  </div>
                </div>
                <div className="mt-4">
                  {post?.post}
                </div>
                <div>
                  {post?.file && (
                    <div>
                      <img src={post?.file} />
                    </div>
                  )}
                </div>
              </div>
              <div className="mt-4">{post?.post}</div>
              <div>
                {/* <img
                  src={`${apiUrl}/classroom/post/getFile?fileName=${post?.fileName}`}
                  alt=""
                  className="w-[200px]"
                /> */}
                <FileViewer file={post?.fileName} />
              </div>
            </div>
          ))
      )}
    </div>
  )
}

export default Posts
