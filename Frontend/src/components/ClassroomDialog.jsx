import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle } from "./ui/dialog"
import { Input } from "./ui/input"
import { Button } from "./ui/button"

import ClassroomJoin from "./ClassroomJoin"
import ClassroomCreate from "./ClassroomCreate";

const ClassroomDialog = ({ dialogOpen, setDialogOpen, join }) => {

  return (
    <Dialog open={dialogOpen} onOpenChange={setDialogOpen} className="w-10">
      <DialogContent>
        <DialogHeader>
          <DialogTitle>
            {join ? "Join Class" : "Create Class"}
          </DialogTitle>
          <DialogDescription>
          </DialogDescription>
        </DialogHeader>
        {join ? <ClassroomJoin/> : <ClassroomCreate/>}
        <DialogFooter>
          <Button>{join ? "Join" : "Create"}</Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  )
}

export default ClassroomDialog
