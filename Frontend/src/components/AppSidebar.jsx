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
} from './ui/sidebar'
import { Collapsible, CollapsibleContent, CollapsibleTrigger } from './ui/collapsible';
import { ChevronDown } from 'lucide-react';

import teacher from "../assets/school.png"
import student from "../assets/student.png"
import home from "../assets/home.png"
import { Separator } from './ui/separator';
import { Link, NavLink } from 'react-router';

const AppSidebar = (prop) => {

  const { toggleSidebar } = useSidebar();
  const { sidebarOpen } = prop;

  const classrooms = ["Classroom 1Classroom 1Classroom 1", "Classroom 2", "Classroom 3", "Classroom 4", "Classroom 5"];

  useEffect(() => {
    toggleSidebar();
  }, [sidebarOpen])

  useEffect(() => { }, [location.pathname])

  return (
    <Sidebar className="pl-3 z-100" >
      <SidebarContent>
        <SidebarGroup>
          <SidebarMenu>
            <SidebarMenuItem>
              <a href="/">
                <SidebarMenuButton isActive={location.pathname === '/'}>
                  <img src={home} alt="home" className="w-5 mr-3" />
                  Home
                </SidebarMenuButton>
              </a>
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
                <SidebarMenu>
                  {classrooms.map((classroom, index) => (
                    <SidebarMenuItem key={index}>
                      <a href={`/teacher/${index}`}>
                        <SidebarMenuButton isActive={location.pathname === `/teacher/${index}`}>
                          <span className='truncate'>
                            {classroom}
                          </span>
                        </SidebarMenuButton>
                      </a>
                    </SidebarMenuItem>
                  ))}
                </SidebarMenu>
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
                <SidebarMenu >
                  {classrooms.map((classroom, index) => (
                    <SidebarMenuItem key={index}>
                      <a href={`/student/${index}`}>
                        <SidebarMenuButton isActive={location.pathname === `/student/${index}`}>
                          <span className='truncate'>
                            {classroom}
                          </span>
                        </SidebarMenuButton>
                      </a>
                    </SidebarMenuItem>
                  ))}
                </SidebarMenu>
              </SidebarGroupContent>
            </CollapsibleContent>
          </SidebarGroup>
        </Collapsible>
      </SidebarContent>
    </Sidebar>
  )
}

export default AppSidebar
