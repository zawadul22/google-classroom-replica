import { NavigationMenu, NavigationMenuItem, NavigationMenuList } from './ui/navigation-menu'
import { Avatar, AvatarImage } from './ui/avatar'

import menu from "../assets/menu.svg"
import avatar from "../assets/avatar-96.png"
import add from "../assets/add-96.png"

import './Navigation.css'

const Navigation = () => {

  return (
    <nav className='px-6 pt-4 pb-2 flex justify-between border-b border-gray-200 z-20 fixed w-full'>
      <section>
        <NavigationMenu>
          <NavigationMenuList>
            <NavigationMenuItem className='cursor-pointer'>
              <img src={menu} alt="menu" />
            </NavigationMenuItem>
            <NavigationMenuItem className='px-4 text-xl text-gray-600 font-semibold 
            cursor-pointer hover:underline hover:underline-offset-2'>
              Classroom
            </NavigationMenuItem>
          </NavigationMenuList>
        </NavigationMenu>
      </section>
      <section className='flex gap-4 align-middle justify-center content-center'>
        <div className="rounded-[50%] hover:shadow-md hover:shadow-slate-500 hover:transition-shadow duration-500">
          <img src={add} alt="" className='w-[40px]' />
        </div>
        <Avatar className="hover:shadow-md hover:shadow-slate-500 hover:transition-shadow duration-500">
          <AvatarImage src={avatar} />
        </Avatar>
      </section>
    </nav>
    // <NavigationMenu>
    //       <NavigationMenuList>
    //         <NavigationMenuItem className='cursor-pointer'>
    //           <img src={menu} alt="menu" />
    //         </NavigationMenuItem>
    //         <NavigationMenuItem className='px-4 text-xl text-gray-600 font-semibold 
    //         cursor-pointer hover:underline hover:underline-offset-2'>
    //           Classroom
    //         </NavigationMenuItem>
    //       </NavigationMenuList>
    //     </NavigationMenu>
  )
}

export default Navigation
