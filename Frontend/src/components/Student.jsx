import { useParams } from "react-router"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "./ui/tabs";

const Student = () => {
  const param = useParams();

  return (
    <>
      <div className="w-full flex justify-center">
        <Tabs defaultValue="stream" className="w-[600px]">
          <TabsList className="grid w-full grid-cols-3">
            <TabsTrigger value="stream">Stream</TabsTrigger>
            <TabsTrigger value="assignments">Assignments</TabsTrigger>
            <TabsTrigger value="people">People</TabsTrigger>
          </TabsList>
          <TabsContent value="stream">Posts</TabsContent>
          <TabsContent value="assignments">Assignments</TabsContent>
          <TabsContent value="people">People</TabsContent>
        </Tabs>
      </div>
    </>
  )
}

export default Student
