import { useParams } from "react-router"

const Taecher = () => {
  const param = useParams();
  console.log(param)
  return (
    <div>
      Teacher {param.index}
    </div>
  )
}

export default Taecher
