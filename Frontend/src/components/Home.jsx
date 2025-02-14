import { useQuery } from "@tanstack/react-query";

import { H4 } from "@/Tags";
import ClassroomCards from "./ClassroomCards";

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

  const { data: userClassrooms } = useQuery({
    queryKey: ['classrooms'],
    queryFn: getUserClassrooms,
    refetchOnMount: false,
    refetchOnWindowFocus: false,
    refetchOnReconnect: false
  })

  console.log(userClassrooms)
  return (
    <>
      <div className="w-full pb-10">
        <div className="mb-10 w-full">
          {!userClassrooms && userClassrooms?.asTeacher?.length === 0 && userClassrooms?.asStudent?.length === 0 &&
            <div className="w-full flex flex-col justify-center items-center h-96">
              <p className="text-2xl font-semibold">No classrooms found</p>
              <p className="text-center mt-6">Click on the + button to create or join a classroom</p>
            </div>
          }
          {userClassrooms?.asTeacher &&
            <div className="w-full">
              <H4>As Teacher</H4>
              <br />
              <div className="classroom-grid">
                {userClassrooms?.asTeacher.map((classroom, index) => (
                  <ClassroomCards
                    key={index}
                    classroom={classroom}
                    type="teacher"
                  />
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
              <div className="classroom-grid">
                {userClassrooms?.asStudent.map((classroom, index) => (
                  <ClassroomCards
                    key={index}
                    classroom={classroom}
                    type="student"
                  />
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
