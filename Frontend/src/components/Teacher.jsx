import { useParams } from "react-router"

const Taecher = () => {
  const param = useParams();
  
  return (
    <div>
      Teacher {param.index}
    </div>
  )
}

export default Taecher
