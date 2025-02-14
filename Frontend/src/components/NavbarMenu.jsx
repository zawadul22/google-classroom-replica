import { Popover, PopoverContent, PopoverTrigger } from './ui/popover'
import { Command, CommandItem, CommandList } from './ui/command'
import { Avatar, AvatarImage } from './ui/avatar'

import avatar from "../assets/avatar-96.png"
import add from "../assets/add-96.png"
import ClassroomDialog from './ClassroomDialog'
import { useState } from 'react'

const NavbarMenu = () => {
  const [dialogOpen, setDialogOpen] = useState(false);
  const [join, setJoin] = useState(true);
  const handleDialog = (type) => {
    if (type === "join") {
      setJoin(true);
    }
    else {
      setJoin(false);
    }
    setDialogOpen(true);
  }

  return (
    <div className='flex gap-4 align-middle justify-center content-center'>
      <ClassroomDialog
        dialogOpen={dialogOpen}
        setDialogOpen={setDialogOpen}
        join={join}
      />
      <Popover>
        <PopoverTrigger>
          <div className="rounded-[50%] hover:shadow-md hover:shadow-slate-500 hover:transition-shadow duration-500 cursor-pointer">
            <img src={add} alt="" className='w-[40px]' />
          </div>
        </PopoverTrigger>
        <PopoverContent className="w-38 p-2">
          <Command>
            <CommandList>
              <CommandItem className="cursor-pointer" >
                <div className='w-full' onClick={(e) => { e.preventDefault(); handleDialog("join") }}>
                  Join Class
                </div>
              </CommandItem>
              <CommandItem className="cursor-pointer">
                <div className='w-full' onClick={(e) => { e.preventDefault(); handleDialog("create") }}>
                  Create Class
                </div>
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
