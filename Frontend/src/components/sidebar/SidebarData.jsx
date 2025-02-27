import { NavLink, useLocation } from 'react-router'
import { SidebarMenu, SidebarMenuButton, SidebarMenuItem } from '../ui/sidebar'

const SidebarData = ({ classroomList, type, setOpenMobile }) => {
  const location = useLocation();
  return (
    <SidebarMenu>
      {classroomList?.map((classroom, index) => (
        <SidebarMenuItem key={index}>
          <SidebarMenuButton asChild isActive={location.pathname === `/${type}/${index}`} onClick={() => setOpenMobile(false)}>
            <NavLink to={`/${type}/${index}`}>
              <span className='truncate'>
                {classroom?.classroomName}
              </span>
            </NavLink>
          </SidebarMenuButton>
        </SidebarMenuItem>
      ))}
    </SidebarMenu>
  )
}

export default SidebarData
