import { NavigationMenu, NavigationMenuItem, NavigationMenuList } from './ui/navigation-menu'
import { NavLink } from 'react-router'

import menu from "../assets/menu.svg"
import NavbarMenu from './NavbarMenu';

const Navigation = (props) => {

  const { sidebarOpen, setSidebarOpen, dialogOpen, setDialogOpen } = props;

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
      <section>
        <NavbarMenu />
      </section>
    </nav>
  )
}

export default Navigation
