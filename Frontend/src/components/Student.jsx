import { useParams } from "react-router"

const Student = () => {
    const param = useParams();
    
    return (
        <div>
            Student {param.index2}
        </div>
    )
}

export default Student
