import { NavLink, useLocation } from 'react-router'
import { SidebarMenu, SidebarMenuButton, SidebarMenuItem } from '../ui/sidebar'

const SidebarData = ({ classroomList, type, setOpenMobile }) => {
  const location = useLocation();
  return (
    <SidebarMenu>
      {classroomList?.map((classroom, index) => (
        <SidebarMenuItem key={index}>
          <SidebarMenuButton asChild isActive={location.pathname === `/${type}/${classroom?.id}`} onClick={() => setOpenMobile(false)}>
            {/* <NavLink to={`/${type}/${classroom?.id}`}> */}
            <a href={`/${type}/${classroom?.id}`}>
              <span className='truncate'>
                {classroom?.classroomName}
              </span>
            </a>
            {/* </NavLink> */}
          </SidebarMenuButton>
        </SidebarMenuItem>
      ))}
    </SidebarMenu>
  )
}

export default SidebarData
