import { useState } from "react"
import { Button } from "../ui/button"
import { DialogFooter } from "../ui/dialog"
import { Input } from "../ui/input"
import { toast } from "react-toastify"
import { Loader2 } from "lucide-react"

const ClassroomCreate = ({ setDialogOpen }) => {
  const [error1, setError1] = useState(false);
  const [error2, setError2] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const apiUrl = import.meta.env.VITE_BACKEND_URL;

  const [classroom, setClassroom] = useState({
    className: '',
    section: ''
  });

  const handleInput = (e) => {
    const { name, value } = e.target;
    setClassroom({
      ...classroom,
      [name]: value
    })
  }

  const handleClick = () => {
    setError1(false);
    setError2(false);
    if (classroom.className.length === 0) {
      setError1(true);
    }
    else if (classroom.section.length === 0) {
      setError2(true);
    }
    else {
      handleJoin();
    }
  }

  const handleJoin = async () => {
    try {
      setIsLoading(true);
      const response = await fetch(`${apiUrl}/classroom/create`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          classroomName: classroom.className,
          section: classroom.section,
          teacherName: "zawad4@gmail.com"
        })
      })
      if (response.ok) {
        const data = await response.json();
        toast.success(data.message);
      }
      else {
        const data = await response.json();
        toast.error(data.message);
      }
      setIsLoading(false);
      setDialogOpen(false);
    }
    catch (error) {
      setIsLoading(false);
      setDialogOpen(false);
      toast.error("An error occurred. Please try again later.");
      console.error(error);
    }
  }

  return (
    <div>
      <Input
        className={`h-12 ${error1 && "border-red-500 border-spacing-1 focus:outline-none"}`}
        placeholder="Class name"
        type="text"
        name="className"
        value={classroom.className}
        onChange={handleInput}
      />
      <Input
        className={`h-12 mt-4 ${error2 && "border-red-500 border-spacing-1 focus:outline-none"}`}
        placeholder="Section"
        type="text"
        name="section"
        value={classroom.section}
        onChange={handleInput}
      />
      {(error1 || error2) && <p className="text-red-500 text-xs mt-2">Fields cannot be empty</p>}
      <DialogFooter className="mt-6">
        <Button disabled={isLoading} onClick={handleClick}>
          {isLoading ? <Loader2 className="animate-spin" /> : "Create"}
        </Button>
      </DialogFooter>
    </div>
  )
}

export default ClassroomCreate
