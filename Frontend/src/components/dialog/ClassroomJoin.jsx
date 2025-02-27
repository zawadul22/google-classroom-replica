import { useState } from 'react';
import { Input } from '../ui/input'
import { DialogFooter } from '../ui/dialog';
import { Button } from '../ui/button';
import { toast } from 'react-toastify';
import { Loader2 } from 'lucide-react';

const ClassroomJoin = ({ setDialogOpen }) => {
  const [code, setCode] = useState('');
  const [error, setError] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const apiUrl = import.meta.env.VITE_BACKEND_URL;

  const handleCode = (e) => {
    setCode(e.target.value);
  }

  const handleClick = () => {
    setError(false);
    if (code.length === 0) {
      setError(true);
    }
    else {
      joinByCode();
    }
  }

  const joinByCode = async () => {
    try {
      setIsLoading(true);
      const response = await fetch(`${apiUrl}/classroom/joinByCode`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          code: code,
          email: "zawad3@gmail.com"
        })
      });
      if (response.ok) {
        const data = await response.json();
        console.log(data);
        toast.success(data.message);
      }
      else {
        const data = await response.json();
        console.log(data);
        toast.error(data.message);
      }
      setIsLoading(false);
      setDialogOpen(false);
    } catch (error) {
      toast.error("An error occurred. Please try again later.");
      setIsLoading(false);
      setDialogOpen(false);
      console.error(error);
    }
  }

  return (
    <div>
      <div className="border-2 border-gray-200 rounded-md p-4">
        <p className="font-semibold text-gray-700">Class Code</p>
        <p className="text-sm text-gray-700">Ask your teacher for the class code, then enter it here.</p>
        <Input
          className={`mt-5 mb-2 h-12 
            ${error && "border-red-500 border-spacing-1 focus:outline-0"}`}
          type="text"
          placeholder="Class Code"
          onChange={handleCode}

        />
        {error && <p className="text-red-500 text-xs">This field cannot be empty</p>}
      </div>
      <div>
        <p className="text-sm font-semibold text-gray-700 mt-5">To sign in with a class code</p>
        <ul className="list-disc text-sm pl-4 mt-3">
          <li className="mb-2">Use an authorized account</li>
          <li>Use a class code with 5 letters or numbers, and no spaces or symbols</li>
        </ul>
      </div>
      <DialogFooter className="mt-6">
        <Button disabled={isLoading} onClick={handleClick} className="">
          {isLoading ? <Loader2 className='animate-spin' /> : "Join"}
        </Button>
      </DialogFooter>
    </div>
  )
}

export default ClassroomJoin
