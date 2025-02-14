import { Input } from './ui/input'

const ClassroomJoin = () => {
  return (
    <div>
      <div className="border-2 border-gray-200 rounded-md p-4">
        <p className="font-semibold text-gray-700">Class Code</p>
        <p className="text-sm text-gray-700">Ask your teacher for the class code, then enter it here.</p>
        <Input className="mt-5 mb-6 h-12" type="text" placeholder="Class Code" />
      </div>
      <div>
        <p className="text-sm font-semibold text-gray-700 mt-5">To sign in with a class code</p>
        <ul className="list-disc text-sm pl-4 mt-3">
          <li className="mb-2">Use an authorized account</li>
          <li>Use a class code with 5 letters or numbers, and no spaces or symbols</li>
        </ul>
      </div>
    </div>
  )
}

export default ClassroomJoin
