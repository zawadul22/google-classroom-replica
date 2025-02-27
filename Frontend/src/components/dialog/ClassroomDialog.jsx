import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle } from "../ui/dialog"

import ClassroomJoin from "./ClassroomJoin"
import ClassroomCreate from "./ClassroomCreate";

const ClassroomDialog = ({ dialogOpen, setDialogOpen, join }) => {

  return (
    <>
      <Dialog open={dialogOpen} onOpenChange={setDialogOpen} className="w-10">
        <DialogContent>
          <DialogHeader>
            <DialogTitle>
              {join ? "Join Class" : "Create Class"}
            </DialogTitle>
            <DialogDescription>
            </DialogDescription>
          </DialogHeader>
          {join ?
            <ClassroomJoin setDialogOpen={setDialogOpen} /> :
            <ClassroomCreate setDialogOpen={setDialogOpen} />}
        </DialogContent>
      </Dialog>

    </>
  )
}

export default ClassroomDialog
