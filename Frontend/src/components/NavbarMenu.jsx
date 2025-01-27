import { Popover, PopoverContent, PopoverTrigger } from './ui/popover'
import { Command, CommandItem, CommandList } from './ui/command'
import { Avatar, AvatarImage } from './ui/avatar'

import avatar from "../assets/avatar-96.png"
import add from "../assets/add-96.png"

const NavbarMenu = () => {
  return (
    <div className='flex gap-4 align-middle justify-center content-center'>
      <Popover>
        <PopoverTrigger>
          <div className="rounded-[50%] hover:shadow-md hover:shadow-slate-500 hover:transition-shadow duration-500 cursor-pointer">
            <img src={add} alt="" className='w-[40px]' />
          </div>
        </PopoverTrigger>
        <PopoverContent className="w-38 p-2">
          <Command>
            <CommandList>
              <CommandItem className="cursor-pointer">
                Join Class
              </CommandItem>
              <CommandItem className="cursor-pointer">
                Create Class
              </CommandItem>
            </CommandList>
          </Command>
        </PopoverContent>
      </Popover>
      <Popover>
        <PopoverTrigger>
          <Avatar className="hover:shadow-md hover:shadow-slate-500 hover:transition-shadow duration-500 cursor-pointer">
            <AvatarImage src={avatar} />
          </Avatar>
        </PopoverTrigger>
        <PopoverContent className="w-38 p-2 mr-7">
          <Command>
            <CommandList>
              <CommandItem className="cursor-pointer">
                Logout
              </CommandItem>
            </CommandList>
          </Command>
        </PopoverContent>
      </Popover>
    </div>
  )
}

export default NavbarMenu
