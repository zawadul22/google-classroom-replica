import { useEffect } from 'react';
import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  useSidebar
} from '../ui/sidebar'
import { Collapsible, CollapsibleContent, CollapsibleTrigger } from '../ui/collapsible';
import { ChevronDown } from 'lucide-react';
import { useQuery } from '@tanstack/react-query';

import teacher from "../../assets/school.png"
import student from "../../assets/student.png"
import home from "../../assets/home.png"
import { Separator } from '../ui/separator';
import SidebarData from './SidebarData';
import SidebarSkeleton from './SidebarSkeleton';
import { NavLink, useLocation } from 'react-router';

const AppSidebar = (prop) => {

  const { toggleSidebar, setOpenMobile } = useSidebar();
  const { sidebarOpen } = prop;
  const location = useLocation();
  const apiUrl = import.meta.env.VITE_BACKEND_URL;

  useEffect(() => {
    toggleSidebar();
  }, [sidebarOpen])

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

  return (
    <Sidebar className="pl-3 z-100" >
      <SidebarContent>
        <SidebarGroup>
          <SidebarMenu>
            <SidebarMenuItem>
              <SidebarMenuButton asChild isActive={location.pathname === '/'} onClick={() => setOpenMobile(false)}>
                {/* <NavLink to="/"> */}
                <a href='/'>
                  <img src={home} alt="home" className="w-5 mr-3" />
                  Home
                </a>
                {/* </NavLink> */}
              </SidebarMenuButton>
            </SidebarMenuItem>
          </SidebarMenu>
        </SidebarGroup>
        <Separator />
        <Collapsible defaultOpen className="group/collapsible">
          <SidebarGroup>
            <SidebarGroupLabel asChild className="mb-2">
              <CollapsibleTrigger>
                <img src={teacher} alt="teacher" className="w-[23px] mr-3" />
                As Teacher
                <ChevronDown className="ml-auto transition-transform group-data-[state=open]/collapsible:rotate-180" />
              </CollapsibleTrigger>
            </SidebarGroupLabel>
            <CollapsibleContent>
              <SidebarGroupContent>
                {isLoading ?
                  <SidebarSkeleton />
                  :
                  <SidebarData classroomList={userClassrooms?.asTeacher} type="teacher" setOpenMobile={setOpenMobile} />
                }
              </SidebarGroupContent>
            </CollapsibleContent>
          </SidebarGroup>
        </Collapsible>
        <Separator />
        <Collapsible defaultOpen className="group/collapsible">
          <SidebarGroup>
            <SidebarGroupLabel asChild className="mb-2">
              <CollapsibleTrigger>
                <img src={student} alt="student" className="w-[20px] mr-3" />
                As Student
                <ChevronDown className="ml-auto transition-transform group-data-[state=open]/collapsible:rotate-180" />
              </CollapsibleTrigger>
            </SidebarGroupLabel>
            <CollapsibleContent>
              <SidebarGroupContent>
                {isLoading ?
                  <SidebarSkeleton />
                  :
                  <SidebarData classroomList={userClassrooms?.asStudent} type="student" setOpenMobile={setOpenMobile} />
                }
              </SidebarGroupContent>
            </CollapsibleContent>
          </SidebarGroup>
        </Collapsible>
      </SidebarContent>
    </Sidebar>
  )
}

export default AppSidebar
