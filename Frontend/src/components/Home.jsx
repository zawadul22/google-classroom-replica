import { useQuery } from "@tanstack/react-query";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "./ui/card";

import { H4 } from "@/Tags";
import { Avatar, AvatarImage } from "./ui/avatar";
import avatarImage from "../assets/man.png";

const Home = () => {
  const apiUrl = import.meta.env.VITE_BACKEND_URL;
  const getUserClassrooms = async () => {
    try {
      const response = await fetch(`${apiUrl}/user/classroom/zawad@gmail.com`);
      if (response.ok) {
        const data = await response.json();
        return data;
      }
      else {
        console.log("Could not fetch data")
      }
    }
    catch (error) {
      console.error(error);
    }
  }

  const { data: userClassrooms, isLoading } = useQuery({
    queryKey: ['classrooms'],
    queryFn: getUserClassrooms,
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false
  })

  console.log(userClassrooms)
  return (
    <>
      <div className="w-full">
        <div className="mb-10 w-full">
          {userClassrooms?.asTeacher &&
            <div className="w-full">
              <H4>As Teacher</H4>
              <br />
              <div className="classroom-grid">
                {/* {userClassrooms?.asTeacher.map((classroom, index) => (
                  <Card key={index}>
                    <CardHeader>
                      <CardTitle>{classroom?.classroomName}</CardTitle>
                    </CardHeader>
                  </Card>
                ))} */}
                {Array.from({ length: 8 }).map((_, index) => (
                  <Card key={index} className="custom-card">
                    <CardHeader>
                      <CardTitle className="text-white">
                        Classroom {index + 1}
                      </CardTitle>
                      <CardDescription className="text-white">
                        Section 3
                      </CardDescription>
                    </CardHeader>
                    <CardContent>
                      <div className="relative">
                        <p className="absolute top-5 font-semibold truncate w-48">Zawadul Islam Nibir</p>
                        <Avatar className="absolute right-0 -bottom-12 w-20 h-20">
                          <AvatarImage src={avatarImage} />
                        </Avatar>
                        <p className="absolute top-11">10 participants</p>
                      </div>
                    </CardContent>
                  </Card>
                ))}
              </div>
            </div>
          }
        </div>
        <div>
          {userClassrooms?.asStudent &&
            <div>
              <H4>As Student</H4>
              <br />
              <div className="grid grid-col-2 gap-4">
                {userClassrooms?.asStudent.map((classroom, index) => (
                  <Card key={index}>
                    <CardHeader>
                      <CardTitle>{classroom?.classroomName}</CardTitle>
                    </CardHeader>
                  </Card>
                ))}
              </div>
            </div>
          }
        </div>
      </div>
    </>
  )
}

export default Home
