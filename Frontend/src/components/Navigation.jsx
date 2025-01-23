import { NavigationMenu, NavigationMenuItem, NavigationMenuList } from './ui/navigation-menu'
import { Avatar, AvatarImage } from './ui/avatar'

import menu from "../assets/menu.svg"
import avatar from "../assets/avatar-96.png"
import add from "../assets/add-96.png"
import { NavLink } from 'react-router'

const Navigation = (props) => {

  const { sidebarOpen, setSidebarOpen } = props;
  // const { setOpenMobile } = useSidebar();

  return (
    <nav className='pr-6 pl-4 pt-4 pb-2 flex justify-between border-b border-gray-200 z-20 fixed w-full bg-white'>
      <section>
        <NavigationMenu>
          <NavigationMenuList>
            <NavigationMenuItem className='cursor-pointer hover:bg-gray-200 w-10 h-10 flex justify-center rounded-3xl'
              onClick={() => { setSidebarOpen(!sidebarOpen) }}
              onTouchStart={() => setSidebarOpen(!sidebarOpen)}
            >
              <img src={menu} className='w-4' alt="menu" />
            </NavigationMenuItem>
            <NavigationMenuItem className='px-2 text-xl text-gray-600 font-semibold 
            cursor-pointer hover:underline hover:underline-offset-2'>
              <NavLink to="/">
                Classroom
              </NavLink>
            </NavigationMenuItem>
          </NavigationMenuList>
        </NavigationMenu>
      </section>
      <section className='flex gap-4 align-middle justify-center content-center'>
        <div className="rounded-[50%] hover:shadow-md hover:shadow-slate-500 hover:transition-shadow duration-500 cursor-pointer">
          <img src={add} alt="" className='w-[40px]' />
        </div>
        <Avatar className="hover:shadow-md hover:shadow-slate-500 hover:transition-shadow duration-500 cursor-pointer">
          <AvatarImage src={avatar} />
        </Avatar>
      </section>
    </nav>
  )
}

export default Navigation
