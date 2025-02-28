import { useParams } from "react-router"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "./ui/tabs";

import Posts from "./Posts";
import Assignments from "./Assignments";
import People from "./People";
import { Suspense } from "react";

const TabSection = () => {
  const param = useParams();

  return (
    <>
      <div className="w-full flex justify-center">
        <Tabs defaultValue="stream" className="flex flex-col justify-center items-center">
          <TabsList className="grid w-[360px] sm:w-[600px] mt-3 sm:mt-0 grid-cols-3">
            <TabsTrigger value="stream">Posts</TabsTrigger>
            <TabsTrigger value="assignments">Assignments</TabsTrigger>
            <TabsTrigger value="people">People</TabsTrigger>
          </TabsList>
          <TabsContent value="stream">
            <Suspense fallback={<div>Loading...</div>}>
              <Posts type={param.type} />
            </Suspense>
          </TabsContent>
          <TabsContent value="assignments"><Assignments /></TabsContent>
          <TabsContent value="people"><People /></TabsContent>
        </Tabs>
      </div>
    </>
  )
}

export default TabSection
